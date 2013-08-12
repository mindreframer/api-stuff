include RapiDoc::RAPIDoc

namespace :rapi_doc do

  desc "Generate the config files"
  task :setup => :environment do
    create_structure!
    #puts "Now specify controllers in config/rapi_doc/config.yml for which api documentation is to be generated and then run rapi_doc::generate"
  end

  desc "Generate the api documentation"
  task :generate => :environment do
    if ENV['confirmation'] # Need confirmation?
      resources = get_resources! do |controller, controller_url, controller_location|
        print "Generate documentation for resource \"#{controller}\" mapped at \"#{controller_url}\" (\"#{File.basename(controller_location)}\")? (Y/n):"
        response = STDIN.gets.chomp
        ['y', 'Y'].include? response
      end
    else # Silent mode
      resources = get_resources!
    end

    if resources.empty?
      puts "Nothing to generate"
      #puts "Please specify controllers in config/rapi_doc/config.yml for which api documentation is to be generated and then run rapi_doc::generate again"
    else
      # generate the apidoc
      puts "Generating API documentation..."
      generate_templates!(resources)
      move_structure!
      puts "Finished."
    end
  end

  desc "Clean up generated documentation"
  task :clean do
    remove_structure!
  end

  desc "Clean up everything - generated documentation and all the config"
  task :distclean do
    remove_all!
  end

end
