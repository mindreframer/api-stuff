class Talk
  include Mongoid::Document
  field :title, type: String
  field :description, type: String
  field :proposed_on, type: Date
  field :meetup_id, type: String
  field :user_id, type: String
  field :level, type: String
  field :duration, type: Integer
  belongs_to :meetup
  belongs_to :user
end
