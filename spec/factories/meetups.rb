# Read about factories at https://github.com/thoughtbot/factory_girl

FactoryGirl.define do
  factory :meetup do
    title "MyString"
    description "MyText"
    location_id "MyString"
    scheduled "2013-04-03"
    start_hour "2013-04-03 13:07:01"
    end_hour "2013-04-03 13:07:01"
    meetup_url "MyString"
  end
end
