require "spec_helper"

describe MeetupsController do
  describe "routing" do

    it "routes to #index" do
      get("/meetups").should route_to("meetups#index")
    end

    it "routes to #new" do
      get("/meetups/new").should route_to("meetups#new")
    end

    it "routes to #show" do
      get("/meetups/1").should route_to("meetups#show", :id => "1")
    end

    it "routes to #edit" do
      get("/meetups/1/edit").should route_to("meetups#edit", :id => "1")
    end

    it "routes to #create" do
      post("/meetups").should route_to("meetups#create")
    end

    it "routes to #update" do
      put("/meetups/1").should route_to("meetups#update", :id => "1")
    end

    it "routes to #destroy" do
      delete("/meetups/1").should route_to("meetups#destroy", :id => "1")
    end

  end
end
