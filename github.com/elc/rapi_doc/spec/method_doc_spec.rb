require 'spec_helper'

describe RapiDoc::MethodDoc do
  before do
    @method_doc = RapiDoc::MethodDoc.new('a_type')
  end

  describe "#methods" do
    describe 'add_variable' do
      before do
        @name = 'any_name'
        @value = 'a_value'
      end

      it "should add a new attribute to the object with the same name" do
        @method_doc.add_variable(@name, @value)
        @method_doc.send(@name).should eq(@value)
      end

      it "the value should not be added to 'variables' attribute when adding with named other than 'param' " do
        expect {
          @method_doc.add_variable(@name, @value)
        }.to change {
          @method_doc.variables.count
        }.by(0)
      end

      describe "adding with named 'param'" do
        before do
          @name = 'param'
        end

        it "the value should be added to 'variables' attribute" do
          expect {
            @method_doc.add_variable(@name, @value)
          }.to change {
            @method_doc.variables.count
          }.by(1)
        end

        it "the added value should be at the last entry of 'variables' attribute" do
          @method_doc.add_variable(@name, @value)
          @method_doc.variables.last.should eq(@value)
        end
      end
    end

    describe 'add_output' do
      before do
        @name = 'any_name'
        @value = 'a_value'
      end

      it "should not be added to the 'outputs' attribute when adding with named other than 'output'" do
        expect {
          @method_doc.add_output(@name, @value)
        }.to change {
          @method_doc.outputs.count
        }.by(0)
      end

      describe "adding with named 'output'" do
        before do
          @name = 'output'
        end

        it "should add an output item to the 'outputs' attribute" do
          expect {
            @method_doc.add_output(@name, @value)
          }.to change {
            @method_doc.outputs.count
          }.by(1)
        end

        it "the added item should be at the last entry of 'outputs' attribute" do
          @method_doc.add_output(@name, @value)
          new_hash = eval("{#{@value}: ''}")
          @method_doc.outputs.last.should eq(new_hash)
        end

        it "the new output item should be a hash with key named the 'value' parameter" do
          @method_doc.add_output(@name, @value)
          @method_doc.outputs.last.keys.include?(@value.to_sym).should be_true
        end
      end
    end

    describe 'append_output' do
      before do
        @current_format_content = "any string"
        @last_hash = {format: @current_format_content}
        @method_doc.outputs << @last_hash
      end

      it 'should append the existing format content on the outputs' do
        value = "some string"
        @method_doc.append_output(value)
        @method_doc.outputs.last[:format].should eq(@current_format_content + value)
      end

      it 'the appended value should be html-escaped' do
        value = "<a>tag</a>"
        @method_doc.append_output(value)
        @method_doc.outputs.last[:format].should eq(@current_format_content + ERB::Util.html_escape(value))
      end
    end
  end
end