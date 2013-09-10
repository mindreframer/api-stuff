json.array!(@talks) do |talk|
  json.extract! talk, :title, :description, :proposed_on, :meetup_id, :user_id, :level, :duration
  json.url talk_url(talk, format: :json)
end