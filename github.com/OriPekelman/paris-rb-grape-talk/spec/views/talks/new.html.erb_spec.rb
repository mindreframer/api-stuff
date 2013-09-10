require 'spec_helper'

describe "talks/new" do
  before(:each) do
    assign(:talk, stub_model(Talk,
      :title => "MyString",
      :description => "MyText",
      :meetup_id => "MyString",
      :user_id => "MyString",
      :level => "MyString",
      :duration => 1
    ).as_new_record)
  end

  it "renders new talk form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form[action=?][method=?]", talks_path, "post" do
      assert_select "input#talk_title[name=?]", "talk[title]"
      assert_select "textarea#talk_description[name=?]", "talk[description]"
      assert_select "input#talk_meetup_id[name=?]", "talk[meetup_id]"
      assert_select "input#talk_user_id[name=?]", "talk[user_id]"
      assert_select "input#talk_level[name=?]", "talk[level]"
      assert_select "input#talk_duration[name=?]", "talk[duration]"
    end
  end
end
