#Short presentation on Grape for your Rails bases API
##Paris.rb April 2nd

I don't  really have the time to write a proper blog post, I hope this will
be at least somewhat useful to those who saw my talk. We examined in this talk
the interest of adding grape as an API DSL compared to using vanilla Rails.

To do so we are in a very quick and dirty manner recreating the website for talk
submissions for paris.rb .... giving it a nice API.

We are using Rails 4.0 beta1. And to speed things up we are using an application template. We will be using Alex Klepa's example Rails 4.0 app with Devise, CanCan, OmniAuth and Twitter Bootstrap, minimally hacked for our needs. 

Let's first take care of our dependencies. I ran this on ruby 2.0 with Rails 4.0. You should also install and have running an instance of Mongo db (http://docs.mongodb.org/manual/installation/).

As a quick reminder:

    $  gem install rails --version 4.0.0.beta1 --no-ri --no-rdoc


First lets create the fullblown rails application. Boom.

    $ rails new parisrb -m https://raw.github.com/OriPekelman/rails4-bootstrap-devise-cancan-omniauth/master/rails4-bootstrap.rb --skip-bundle

    $ cd parisrb

To create a first admnistrative user(admin@example.com, password:administrator) we can:

    $ rake db:seed

Let's test this is working

    $ rails s

Note that lines starting with $EDITOR mean you want to replace this with your
own (or configure your shell)

     $ $EDITOR app/views/layouts/_navigation.html.erb

Real geeks don't like facebook, but they do love branding.

Now let's generate our domain objects. Talks are given by a person, at a meetup that has a data, a starting time and a location.

    $ rails g scaffold location title:string
    $ rails g scaffold meetup title:string description:text location_id:string scheduled:date start_hour:time end_hour:time meetup_url:string
    $ rails g scaffold talk  title:string description:text proposed_on:date meetup_id:string user_id:string level:string duration:integer

Now let's tell mongoing what is what and establish some relations between our models. Locations will have many meetups, meetups will have a single location but many talks. Talks belong to a meetup and a user. A user can have many talks.

     $ $EDITOR app/models/location.rb

     has_many :meetups

     $ $EDITOR   app/models/meetup.rb

     has_many :talks
     belongs_to :location
  
     $ $EDITOR  app/models/talk.rb

     belongs_to :meetup
     belongs_to :user
  
     $ $EDITOR  app/models/user.rb

     has_many :talks
  
At this point you might want to visit your site again and see everything is cool.

Ok, now for some better looking entry forms. Please remember all this is very
q&r, and might very well be half broken

     $ $EDITOR  app/views/meetups/_form.html.erb

    <%= f.input :title %>
    <%= f.input :description, as: :text %>
    <%= f.input :location_id, collection: Location.all, as: :select%>
    <%= f.date_select :scheduled, default: Date.today + 30, label: 'Date' %>
    <%= f.time_select :start_hour, default: Date.today + 1 , label: 'Heure de début' %>
    <%= f.time_select :end_hour, default: Date.today + 4, label: 'Heure de fin'  %>

     $ $EDITOR  app/views/talks/_form.html.erb

     <%= f.input :title %>
     <%= f.input :description, as: :text %>
     <%= f.input :proposed_on, as: :date, disabled: :true, default: Date.today  %>
     <%= f.input :meetup_id, collection: Meetup.all, as: :select%>
     <fieldset><legend>Niveau</legend>
     <%= f.collection_radio_buttons :level, [[1, 'Débutant'] ,[5, 'Intermédiaire'],[10, 'Avancé']], :first, :last %>
     </fieldset>
     <fieldset><legend>Durée</legend>
     <%= f.collection_radio_buttons :duration, [[5, '4 min super court'] ,[20, '20 mins un peu de temps'],[60, 'Une heure!?']], :first, :last %>
     </fieldset>

Now time to see our beautiful application again. This is nice. But this is an admin party, only authenticated users should edit all this stuff

     $ $EDITOR  config/routes.rb

     devise_for :users, :controllers => {
       registrations: "users/registrations", 
       passwords: "users/passwords", 
       omniauth_callbacks: "users/omniauth_callbacks"
     }
      
     authenticated :user do
       root :to => "talks#index"
       resources :talks
       resources :meetups
       resources :locations
       resources :users
     end
     root :to => "home#index", :as => :anon_home
  
Before we forget, we said that a talk belongs to the user that proposes it, so here goes
  
     $ $EDITOR   app/controllers/talks_controller.rb
    @talk = Talk.new(talk_params.merge({:user_id => current_user.id}))

Please log-in (or you will get  a bunch of 404s). We can now visit http://localhost:3000/locations/new and create a new location  then add our new meetup  http://localhost:3000/meetups/new and even submit a new talk http://localhost:3000/talks/new

We almost also have a working API! we used jbuilder and we need to tweak the generated json views.

Now visiting  http://localhost:3000/talks.json makes you wan't to cry right? 10 minutes later and we have our API!  We can even do "PATCH" against it!

Or do we? Not really. Because we don't have an API documentation. And more importantly we are not exposing something that is rational, useful and designed. This was quick, but this is dirty. APIs want to be designed. Even more importantly the API portion of our application is spaghettied all over. Let's add the very nice https://github.com/intridea/grape from the great folks at Intridea (that also give us Omniauth used in this little demonstration).

     $ $EDITOR Gemfile

     gem 'grape'

We need to ctrl-c the rails we have been running and:

     $ bundle install

and restart it now

    $ rails s

Now we want grape because we wanted to separate our API, decouple it from our main implementation. Let's honour it by letting it have it's own space.

    mkdir app/api

We need rails to load our new creations so:

     $ $EDITOR config/application.rb

     config.paths.add "app/api", :glob => "**/*.rb"
     config.autoload_paths += Dir["#{Rails.root}/app/api/*"]

Time for a small hello-world 
  
     $ $EDITOR  app/api/paris.rb

    module Paris
      class RB < Grape::API
        desc "Root of api"
        get "/" do
          {:link=>"Home sweet home"}
        end
      end
    end
    

We need to mount this as an engine.

     $ $EDITOR   config/routes.rb

     mount Paris::RB => '/api'  
  
Visiting http://localhost:3000/api should fill you with bliss. But we wanted to
have our own specific API not just hello world. 

Let's  first show some grape goodness and add versioning while defaulting to json. Your api should now be at http://localhost:3000/api/v1 In th presenation
I told you how versioning APIs using the url prefix can be a very bad idea. Still it is cool to have it for "free".

Ok, our own custom API logic.
  
     $ $EDITOR  app/api/paris.rb

     module Paris
       class RB < Grape::API
         version 'v1', :using => :path, :vendor => 'parisrb'
         format :json     
         
         resource :talks do  
           desc "Return the collection of current talks"
           get :current do
             Talk.limit(20)
           end
     
           desc "Return a talk."
           params do
             requires :id, :type => String, :desc => "talk id."
           end
           get ':id' do
             Talk.find(params[:id])
           end
         end     
         
       end
     end

Visiting http://localhost:3000/api/v1/talks/current should make you happy

But we haven't finished, have we? Without documentation an API is of no use.. grape comes with a great ecosystem of gems to do Caching, Hypermedia ... and docs.

     $ $EDITOR    Gemfile

     gem 'grape-swagger'

Let's ctrl-c again

     $ bundle install
     $ rails s

Time to add documentation . We add a second module that will mount inside it the API and add swagger style documentation generation. We will now mount this second module as out API.

     $ $EDITOR  app/api/paris.rb

     module Paris      
       class Root < Grape::API   
         mount Paris::RB
         add_swagger_documentation :base_path=>"http://localhost:3000/api", :mount_path =>"v1/doc", :markdown => false, :api_version => Paris::RB.version, :hide_documentation_path =>false
       end
     end
 
     $ $EDITOR  config/routes.rb

     mount Paris::Root => '/api'

Finished. Look at http://localhost:3000/api/v1/doc.json and.. go to swagger-ui demo http://petstore.swagger.wordnik.com and put this url in!

You got a free API console! This is great. But probably the major benefit here is that everything that is related to the API is in a single location. It is 
mostly self contained and understandable. 
