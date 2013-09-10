class Location
  include Mongoid::Document
  field :title, type: String
  has_many :meetups

end
