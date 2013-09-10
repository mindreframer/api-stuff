require 'spec_helper'

describe "meetups/index" do
  before(:each) do
    assign(:meetups, [
      stub_model(Meetup,
        :title => "Title",
        :description => "MyText",
        :location_id => "Location",
        :meetup_url => "Meetup Url"
      ),
      stub_model(Meetup,
        :title => "Title",
        :description => "MyText",
        :location_id => "Location",
        :meetup_url => "Meetup Url"
      )
    ])
  end

  it "renders a list of meetups" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "Title".to_s, :count => 2
    assert_select "tr>td", :text => "MyText".to_s, :count => 2
    assert_select "tr>td", :text => "Location".to_s, :count => 2
    assert_select "tr>td", :text => "Meetup Url".to_s, :count => 2
  end
end
