# Read about factories at https://github.com/thoughtbot/factory_girl

FactoryGirl.define do
  factory :talk do
    title "MyString"
    description "MyText"
    proposed_on "2013-04-03"
    meetup_id "MyString"
    user_id "MyString"
    level "MyString"
    duration 1
  end
end
