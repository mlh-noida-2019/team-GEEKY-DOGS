var mongoose=require('mongoose');

var Event=mongoose.model('Event',{
  eventname:{
    type:String,
    required:true,
  },
  emaildata:[{
    type:String
  }]
});

module.exports={
    Event
}
