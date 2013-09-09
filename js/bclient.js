
function BarristerUI(rootElem) {
    var me = this;
    me.rootElem = rootElem;

    var html = "<div class='grid_3'><h3>Functions:</h3></div>";
    html += "<div class='grid_9'><div id='barrister-msg'></div></div>";
    html += "<div class='clear'></div>";
    html += "<div class='grid_3'><div id='barrister-ifaces'></div></div>";
    html += "<div class='grid_9'><div id='barrister-main'></div></div>";
    html += "<div class='clear'></div>";
    jQuery(rootElem).html(html);

    jQuery("#barrister-ui").delegate("a.barrister-func", "click", function() {
        var pos = this.href.lastIndexOf("#");
        var re = /iface-(\S+)/;
        var match = re.exec(this.href.substr(pos+1));
        var ifaceAndFunc = me.getFunctionByName(match[1], match[2]);
        me.showFunctionForm(ifaceAndFunc);
        return false;
    });
    jQuery("#barrister-ui").delegate("a.add-row", "click", function() {
        var pos = this.href.lastIndexOf("#");
        var name = this.href.substr(pos+1);
        var p = me.nameToType[name];
        var pClone = { name: p.name, type: p.type, is_array: false };
        var row = me.renderParamHtml(name + "-" + p.rows, pClone) + "<br />";
        p.rows++;
        jQuery(this).before(row);
        return false;
    });
    jQuery("#barrister-main").delegate("#btn-call-func", "click", function() {
        var params = me.parseParams(jQuery("#form-call-func"));  // this == submitted form
        var method = me.currentIface.name + "." + me.currentFunc.name;
        console.log("params: " + params);
        me.showMessage("info", "Executing " + method);
        me.client.request(method, params, function(err, result) {
            me.hideMessage();
            if (err) {
                me.onErr(err.message);
            }
            else {
                var html = "<h3>Result:</h3><pre>" + JSON.stringify(result) + "</pre>";
                jQuery("#barrister-result").html(html);
                me.showMessage("success", "Call Successful");
            }
        });
        return false;
    });
}

BarristerUI.prototype.escapeHTML = function(s) {
   s = s.replace('&', '&amp;');
   s = s.replace('<', '&lt;');
   s = s.replace('>', '&gt;');
   s = s.replace('"', '&#34;');
   s = s.replace("'", '&#39;');
   return s;
};

BarristerUI.prototype.parseParams = function(form) {
    var me     = this;
    var params = me.currentFunc.params;
    var parsed = [ ];
    var i;
    
    var formValArr = jQuery(form).serializeArray();
    var formValMap = { };
    for (i = 0; i < formValArr.length; i++) {
        var v = formValArr[i];
        formValMap[v.name] = v.value;
    }
    
    for (i = 0; i < params.length; i++) {
        var key = "param-" + i;
        parsed.push(me.parseParam(key, formValMap, params[i]));
    }
    
    return parsed;
};

BarristerUI.prototype.parseParam = function(key, formValMap, field) {
    if (field.is_array) {
        var arr = [ ];
        var t = this.nameToType[key];
        var fieldClone = { name: field.name, type: field.type, is_array: false };
        var i;
        for (i = 0; i < t.rows; i++) {
            arr.push(this.parseParam(key+"-"+i, formValMap, fieldClone));
        }
        return arr;
    }
    else if (field.type === "string") {
        return formValMap[key];
    }
    else if (field.type === "int") {
        return parseInt(formValMap[key], 10);
    }
    else if (field.type === "float") {
        return parseFloat(formValMap[key]);
    }
    else if (field.type === "bool") {
        return formValMap[key] === "true" || formValMap[key] === "1";
    }
    else {
        var s = this.client.contract.structs[field.type];
        if (s) {
            var m = { };
            this.addStructParams(key, formValMap, s, m);
            return m;
        }
        else {
            s = this.client.contract.enums[field.type];
            if (s) {
                return formValMap[key];
            }
        }
    }
};

BarristerUI.prototype.addStructParams = function(key, formValMap, s, m) {
    var x;
    if (s['extends'] && s['extends'] !== "") {
        var parent = this.client.structs[s['extends']];
        if (parent) {
            this.addStructParams(key, formValMap, parent, m);
        }
    }
    for (x = 0; x < s.fields.length; x++) {
        m[s.fields[x].name] = this.parseParam(key + "-" + x, formValMap, s.fields[x]);
    }
};

BarristerUI.prototype.loadContract = function(url) {
    var me = this;
    me.client = Barrister.httpClient(url);
    me.client.enableTrace();
    me.client.loadContract(function(err) {
        var i, x, name;
        var html = "<ul>";
        var ifaces = me.client.contract.interfaces;
        for (name in ifaces) {
            if (ifaces.hasOwnProperty(name)) {
                html += "<li>" + name + "<ul>";
                var iface = me.client.contract.interfaces[name];
                var funcs = iface.functions;
                
                for (x = 0; x < funcs.length; x++) {
                    var f = funcs[x];
                    html += "<li><a class='barrister-func' href='#iface-" + 
                            name + "." + f.name + "'>" + f.name + "</a></li>";
                }
                html += "</ul></li>";
            }
        }
        html += "</ul>";
        
        jQuery("#barrister-ifaces").html(html);
        me.showMessage("info", "Contract loaded");
    });
};

BarristerUI.prototype.getFunctionByName = function(method) {
    var ifaceName, funcName, pos;
    pos = method.indexOf(".");
    ifaceName = method.substr(0, pos);
    funcName  = method.substr(pos+1);

    var o   = { };
    o.iface = this.client.contract.interfaces[ifaceName];
    o.func  = this.client.contract.functions[method];
    return o;
};

BarristerUI.prototype.showFunctionForm = function(ifaceAndFunc) {
    this.currentFunc  = ifaceAndFunc.func;
    this.currentIface = ifaceAndFunc.iface;
    this.nameToType   = { };
    var f = this.currentFunc;
    var i;
    
    var html = "";
    if (f.comment) {
        
        html += "<p class='comment'>" + this.escapeHTML(f.comment) + "</p>";
    }
    html += "<form class='form-horizontal well' id='form-call-func'><fieldset>";
    html += "<legend>" + this.escapeHTML(this.currentIface.name + "." + this.currentFunc.name) + "</legend>";
    
    for (i = 0; i < f.params.length; i++) {
        html += this.renderParamRow("param-" + i, f.params[i]);
    }
    html += "</fieldset>";
    html += "<a href='#' class='btn btn-primary' id='btn-call-func'>Call Function</a>";
    html += "</form>";
    html += "<div id='barrister-result'></div>";
    
    this.hideMessage();
    jQuery("#barrister-main").html(html);
};

BarristerUI.prototype.renderParamRow = function(name, p) {
    this.nameToType[name] = p;
    var inputHtml = this.renderParamHtml(name, p);
    var comment = p.type;
    if (p.is_array) {
        comment = "array of " + p.type;
    }

    var html = "<div class='control-group'>" +
        "<label class='control-label' for='"+name+"'>"+p.name+" (" + comment + ")</label>" +
        "<div class='controls'>" + inputHtml;
    if (p.comment) {
        comment += " - " + p.comment;
    }
    
    html += "</div></div>";
    return html;
};

BarristerUI.prototype.renderParamHtml = function(name, p) {
    var html = null;
    var i;
    if (p.is_array) {
        p.rows = 0;
        html = "<a class='add-row' href='#" + name + "'>add array element</a>";
    }
    else if (p.type === 'string' || p.type === 'int' || p.type === 'float') {
         html = "<input type='text' name='" + name + "'></input>";
    }
    else if (p.type === 'bool') {
        html = "<select name='" + name + "'>";
        html += "<option value='true'>true</option>";
        html += "<option value='false'>false</option>";
        html += "</select>";
    }
    else {
        var s = this.client.contract.structs[p.type];
        if (s) {
            html = "<table>";
            for (i = 0; i < s.fields.length; i++) {
                html += this.renderParamRow(name + "-" + i, s.fields[i]);
            }
            html += "</table>";
        }
        else {
            var e = this.client.contract.enums[p.type];
            if (e) {
                html = "<select name='" + name + "'>";
                for (i = 0; i < e.values.length; i++) {
                    html += "<option value='" + e.values[i].value + "'>" + e.values[i].value + "</option>";
                }
                html += "</select>";
            }
        }
    }
    
    if (html === null) {
        alert("Unknown type: " + p.type);
    }
    
    return html;
};

BarristerUI.prototype.onErr = function(o) {
    var s = "Error: " + JSON.stringify(o);
    this.showMessage("error", s.substr(0, 80));
};

BarristerUI.prototype.showMessage = function(type, msg) {
    var html = "<div class=\"alert alert-" + type + "\"><h4>" + this.escapeHTML(msg) + "</h4></div>";
    jQuery("#barrister-msg").html(html);
    jQuery("#barrister-msg").show();
};

BarristerUI.prototype.hideMessage = function() {
    jQuery("#barrister-msg").hide();
};