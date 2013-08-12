require 'spec_helper'

describe RapiDoc::DocParser do
  before do
    @parser = RapiDoc::DocParser.new
  end

  describe "#methods" do
    describe "strip_line" do
      it "should strip '#' correctly" do
        line = '# random string'
        @parser.strip_line(line).should eq(' random string')
      end
      it "should strip only one '#'" do
        line = '## random string'
        @parser.strip_line(line).should_not eq(' random string')
        @parser.strip_line(line).should eq('# random string')
      end
    end

    describe "start" do
      it 'should by default generate MethodDoc with scope :class' do
        method_doc = RapiDoc::MethodDoc.new(:class)
        @parser.start
        @parser.current_scope.should eq(method_doc.scope)
      end

      it 'should generate MethodDoc object with scope :function if in class' do
        method_doc = RapiDoc::MethodDoc.new(:function)
        @parser.in_class = true
        @parser.start
        @parser.current_scope.should eq(method_doc.scope)
      end

      it 'should generate MethodDoc object with scope :class if not in class' do
        method_doc = RapiDoc::MethodDoc.new(:class)
        @parser.in_class = false
        @parser.start
        @parser.current_scope.should eq(method_doc.scope)
      end
    end

    describe "when it gets started" do
      before do
        @parser.start
      end

      it 'reset_current_scope_and_api_block will set it to :none and nil respectively' do
        @parser.reset_current_scope_and_api_block
        @parser.current_api_block.should be(nil)
        @parser.current_scope.should be(:none)
      end

      describe 'parse' do
        before do
          @api_block = @parser.current_api_block
          @line = "#output:: xml"
        end

        describe 'with scope :response' do
          before do
            @parser.current_scope = :response
            @api_block.response = "some responses"
            @current_response = @api_block.response
          end

          it 'should append the existing response on the current_api_block' do
            @parser.parse(@line)
            @api_block.response.should eq(@current_response + @parser.strip_line(@line))
          end
        end

        describe 'with scope :request' do
          before do
            @parser.current_scope = :request
            @api_block.request = "some requests"
            @current_request = @api_block.request
          end

          it 'should append the existing request on the current_api_block' do
            @parser.parse(@line)
            @api_block.request.should eq(@current_request + @parser.strip_line(@line))
          end
        end

        describe 'with scope :output' do
          before do
            @parser.current_scope = :output
            @current_content = 'some output content'
            @api_block.outputs = [{ext: @current_content}]
          end

          it 'should append the existing content of the last output type' do
            @parser.parse(@line)
            @api_block.outputs.last[:ext].should eq(@current_content + @parser.strip_line(@line))
          end
        end

        describe 'with scope :class' do
          describe 'when the line matches /(\w+)\:\:\s*(.+)/ regex' do
            before do
              @previous_scope = @parser.current_scope
              @regex = /(\w+)\:\:\s*(.+)/
            end

            it 'should update the current_scope' do
              @parser.parse(@line)
              @parser.current_scope.should_not eq(@previous_scope)
            end

            it "should update the current_scope if the captured string is one of 'response', 'request' or 'output'" do
              %w(response request output).all? do |type|
                line = "##{type}:: something"
                @parser.parse(line)
                # since we need to reset the scope back to :function, we need to
                # save the spec result and then put it at the end for all? method
                result = @parser.current_scope.should eq(type.to_sym)
                @parser.current_scope = :function
                result
              end
            end

            describe "when the captured string is 'output'" do
              it "should add a new output to the current api block" do
                expect{
                  @parser.parse(@line)
                }.to change {
                  @api_block.outputs.count
                }.by(1)
              end

              it "the new output should be a hash with a key named the output type " do
                @parser.parse(@line)
                result = @regex.match(@line)
                the_output = eval("{#{result[2]}: ''}")
                @api_block.outputs.last.should eq(the_output)
              end
            end

            describe "when the captured string is other than any of 'response', 'request' or 'output'" do
              it "should add a new instance variable to the current api block" do
                @line = "#anystring:: any random content"
                @parser.parse(@line)
                @api_block.send('anystring').should eq('any random content')
              end

              describe "when the captured string is 'param'" do
                before do
                  @line = "#param:: page:int - the page, default is 1"
                end

                it "should add a new variable to the current api block" do
                  expect {
                    @parser.parse(@line)
                  }.to change {
                    @api_block.variables.count
                  }.by(1)
                end

                it "the new variable's value should be included in current api block's variables attribute" do
                  @parser.parse(@line)
                  result = @regex.match(@line)
                  @api_block.variables.include?(result[2]).should be_true
                end
              end
            end
          end
        end
      end # describe 'parse'
    end
  end
end