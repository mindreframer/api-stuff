require 'spec_helper'

describe Grape::Jbuilder do
  subject do
    Class.new(Grape::API)
  end

  before do
    subject.format :json
    subject.formatter :json, Grape::Formatter::Jbuilder
  end

  def app
    subject
  end

  it 'should work without jbuilder template' do
    subject.get('/home') { 'Hello World' }
    get '/home'
    last_response.body.should == 'Hello World'
  end

  it 'should raise error about root directory' do
    subject.get('/home', jbuilder: true){}
    get '/home'
    last_response.status.should == 500
    last_response.body.should include "Use Rack::Config to set 'api.tilt.root' in config.ru"
  end


  context 'titl root is setup'  do
    before do
      subject.before { env['api.tilt.root'] = "#{File.dirname(__FILE__)}/views" }
    end

    it 'should respond with proper content-type' do
      subject.get('/home', jbuilder: 'user'){}
      get('/home')
      last_response.headers['Content-Type'].should == 'application/json'
    end

    it 'should not raise error about root directory' do
      subject.get('/home', jbuilder: true){}
      get '/home'
      last_response.status.should == 500
      last_response.body.should_not include "Use Rack::Config to set 'api.tilt.root' in config.ru"
    end

    ['user', 'user.jbuilder'].each do |jbuilder_option|
      it 'should render jbuilder template (#{jbuilder_option})' do
        subject.get('/home', jbuilder: jbuilder_option) do
          @user    = OpenStruct.new(name: 'LTe', email: 'email@example.com')
          @project = OpenStruct.new(name: 'First')
        end

        pattern = {
          user: {
            name: 'LTe',
            email: 'email@example.com',
            project: {
              name: 'First'
            }
          }
        }

        get '/home'
        last_response.body.should match_json_expression(pattern)
      end
    end
  end
end
