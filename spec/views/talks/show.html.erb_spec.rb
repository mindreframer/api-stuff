require 'spec_helper'

describe "talks/show" do
  before(:each) do
    @talk = assign(:talk, stub_model(Talk,
      :title => "Title",
      :description => "MyText",
      :meetup_id => "Meetup",
      :user_id => "User",
      :level => "Level",
      :duration => 1
    ))
  end

  it "renders attributes in <p>" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Title/)
    rendered.should match(/MyText/)
    rendered.should match(/Meetup/)
    rendered.should match(/User/)
    rendered.should match(/Level/)
    rendered.should match(/1/)
  end
end
