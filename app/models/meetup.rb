class Meetup
  include Mongoid::Document
  field :title, type: String
  field :description, type: String
  field :location_id, type: String
  field :scheduled, type: Date
  field :start_hour, type: Time
  field :end_hour, type: Time
  field :meetup_url, type: String
  has_many :talks
  belongs_to :location
end
