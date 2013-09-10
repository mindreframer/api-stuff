require 'spec_helper'

describe "meetups/show" do
  before(:each) do
    @meetup = assign(:meetup, stub_model(Meetup,
      :title => "Title",
      :description => "MyText",
      :location_id => "Location",
      :meetup_url => "Meetup Url"
    ))
  end

  it "renders attributes in <p>" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Title/)
    rendered.should match(/MyText/)
    rendered.should match(/Location/)
    rendered.should match(/Meetup Url/)
  end
end
