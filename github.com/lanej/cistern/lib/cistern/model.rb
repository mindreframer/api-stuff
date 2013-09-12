class Cistern::Model
  extend Cistern::Attributes::ClassMethods
  include Cistern::Attributes::InstanceMethods

  attr_accessor :collection, :connection

  def inspect
    Cistern.formatter.call(self)
  end

  def initialize(attributes={})
    merge_attributes(attributes)
  end

  def save
    raise NotImplementedError
  end

  def reload
    requires :identity

    if data = collection.get(identity)
      new_attributes = data.attributes
      merge_attributes(new_attributes)
      self
    end
  end

  def ==(comparison_object)
    comparison_object.equal?(self) ||
      (comparison_object.is_a?(self.class) && 
       comparison_object.identity == self.identity && 
       !comparison_object.new_record?)
  end

  def service
    self.connection ? self.connection.class : Cistern
  end

  def wait_for(timeout = self.service.timeout, interval = self.service.poll_interval, &block)
    service.wait_for(timeout, interval) { reload && block.call(self) }
  end

  def wait_for!(timeout = self.service.timeout, interval = self.service.poll_interval, &block)
    service.wait_for!(timeout, interval) { reload && block.call(self) }
  end
end
