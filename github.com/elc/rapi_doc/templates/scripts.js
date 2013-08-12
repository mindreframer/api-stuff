/**
 * created by: salilwadnerkar
 * Date: 11/4/12
 */

 helper = {
    pretty_print: function(source_selector){
      if(!source_selector || $(source_selector).length <= 0)
        return false;
      
      $(source_selector).each(function(idx){
        var source = $(this);
        var opts = {
          "indent_size":"4",
          "indent_char":" ",
          "preserve_newlines":true,
          "brace_style":"collapse",
          "keep_array_indentation":false,
          "space_after_anon_function":true,
          "space_before_conditional":true,
          "indent_scripts":"normal"
        };
        var v = js_beautify($(source).html(), opts);
        $(source).html(v);
      });
      prettyPrint();
    },

    scroll_to: function(element, delay_time, padding_top){
      if (!delay_time)
        delay_time = 300;

      if(!padding_top)
        padding_top = 60;

      $('html, body').animate({ scrollTop: $(element).offset().top - padding_top}, delay_time);
    }
 };

$(document).ready(function(){
    var api_endpoints = $("#resource_index ul li");
    var search = function(query){
      var search_regex = new RegExp(query, 'gi');
      api_endpoints.each ( function() {
        var elem = $(this);
        var h_elem = elem.find('a')[0];
        var link_txt = $.trim($(h_elem).text());
        var match = link_txt.match(search_regex);
        if(match){
          // Show the parent container first
          elem.parents("li.resource-container").show();
          elem.show();
        }
        else
          elem.hide();
      });
    };

    
    $('#resource_index input').keyup(function(event) {
      if ( event.keyCode == 13 ){
        event.preventDefault();
      }
      search($(this).val());
    });

    // Pretty print code.
    helper.pretty_print(".prettyprint, code");

    // Auto scroll to API endpoint.
    var id = window.location.hash.replace('#', '');
    var elem = $("a[id='" + id + "']" );
    if(elem && elem.length > 0) {
      helper.scroll_to(elem);
    }

    $("#api-navigation a").click(function(e){
      e.preventDefault();
      var id = $(this).attr('href');
      if(id){
        id = id.replace('#', '');
        var elem = $("a[id='" + id + "']" );
        helper.scroll_to(elem);
      }

      // Highlight the current entry
      $("#api-navigation .active").removeClass("active");
      $(this).parent().addClass("active");
    });
});
