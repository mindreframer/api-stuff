require 'rails'

module RapiDoc
  class Railtie < Rails::Railtie
    rake_tasks do
      load 'rapi_doc/tasks/rapi_doc_tasks.rake'
    end
  end
end