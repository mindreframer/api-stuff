require 'spec_helper'

describe Acme::API do
  include Rack::Test::Methods

  def app
    Acme::API
  end

  it "rescues all exceptions" do
    get "/api/raise"
    last_response.status.should == 500
    last_response.body.should == "Unexpected error."
  end

end

