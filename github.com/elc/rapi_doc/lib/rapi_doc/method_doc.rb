module RapiDoc
  # This class holds methods about a doc.
  class MethodDoc
    attr_accessor :scope, :method_order, :content, :request_header, :request, :response, :outputs, :params,
                  :name, :url, :method
    
    def initialize(resource_name, type, order)
      @resource_name = resource_name
      @scope = type
      @method_order = order
      @content = ""
      @request_header = ""
      @request = ""
      @response = ""
      @outputs = {}
      @params = []
      @name = ""
      @url = ""
      @method = ""
    end

    def process_line(line, current_scope)
      line.strip!
      #puts "In scope #{current_scope} processing: #{line}"
      new_scope = current_scope
      case current_scope
      when :response
        if line =~ /::response-end::/
          new_scope = :function
        else
          @response << line
        end
      when :request_header
        if line =~ /::request_header-end::/
          new_scope = :function
        else
          @request_header << line
        end
      when :request
        if line =~ /::request-end::/
          new_scope = :function
        else
          @request << line
        end
      when :output # append output
        if line =~ /::output-end::/
          new_scope = :function
        else
          last_output_key = @outputs.keys.last
          @outputs[last_output_key] << ERB::Util.html_escape(line)
        end
      when :class, :function
        result = line.scan(/(\w+)\:\:\s*(.*)/)
        if not result.empty?
          key, value = result[0]
          value.strip!
          case key
          when "response", "request", "request_header"            
            new_scope = key.to_sym
          when "output"
            new_scope = key.to_sym
            @outputs[value] = '' # add the new output format as a key
          when "param"
            @params << value
          when "url", "name", "method"
            self.send("#{key}=", value)
          else # user wants this new shiny variable whose name is the key with value = value
            instance_variable_set("@#{key}".to_sym, value)
            define_singleton_method(key.to_sym) { value } # define accessor for the templates to read it
          end
        else
          # add line to block
          @content << line
        end
      else
        raise ParsingException, "logic error: unknown current scope #{current_scope}"
      end
      new_scope
    end

    def get_binding
      binding
    end

    # Return url if the @name is not set.
    def name
      return @url if @name.empty?
      @name
    end
  end
end
