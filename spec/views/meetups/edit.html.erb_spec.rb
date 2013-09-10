require 'spec_helper'

describe "meetups/edit" do
  before(:each) do
    @meetup = assign(:meetup, stub_model(Meetup,
      :title => "MyString",
      :description => "MyText",
      :location_id => "MyString",
      :meetup_url => "MyString"
    ))
  end

  it "renders the edit meetup form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form[action=?][method=?]", meetup_path(@meetup), "post" do
      assert_select "input#meetup_title[name=?]", "meetup[title]"
      assert_select "textarea#meetup_description[name=?]", "meetup[description]"
      assert_select "input#meetup_location_id[name=?]", "meetup[location_id]"
      assert_select "input#meetup_meetup_url[name=?]", "meetup[meetup_url]"
    end
  end
end
