json.array!(@meetups) do |meetup|
  json.extract! meetup, :title, :description, :location_id, :scheduled, :start_hour, :end_hour, :meetup_url
  json.url meetup_url(meetup, format: :json)
end