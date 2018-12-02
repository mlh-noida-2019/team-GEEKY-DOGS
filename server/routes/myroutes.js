const express=require('express');
const router=express.Router();
const _=require('lodash');
const {ObjectID}=require('mongodb');
var formidable = require('formidable');
var fs = require('fs');
var path=require('path');

var {mongoose}=require('./../db/mongoose.js');
var {User}=require('./../User-Data/user.js');
var {Event}=require('./../User-Data/event.js');
var {authenticate}=require('./../middleware/authenticate.js');

router.get('/', (req, res) => res.send('running'));


router.post('/usersignup',(req,res)=>{
  console.log(req.body);
  var body=_.pick(req.body,['email','name','organization','phone','password']);
  var user=new User(body);
  user.save().then(()=>{
    return user.generateAuthToken();
  }).then((token)=>{
    res.header('x-auth',token).send(user);
  }).catch((e)=>{
    res.status(400).send(e);
  });
});

router.post('/userlogin',(req,res)=>{
  var body=_.pick(req.body,['email','password']);
  User.findByCredentials(body.email,body.password).then((user)=>{
    return user.generateAuthToken().then((token)=>{
      res.header('x-auth',token).send(user);
    });
  }).catch((e)=>{
   res.status(400).send();
  });
});

router.post('/add',authenticate,(req,res)=>{
  var eventname=req.body.eventname;
  var emaildata=[];
  emaildata=req.body.emaildata;
  var event=new Event({
    eventname:eventname,
    emaildata:emaildata
  });
  event.save().then((event)=>{
    res.send(event);
  },(e)=>{
    res.status(400).send(e);
  });
});

router.post('verify',authenticate,(req,res)=>{
  var eventname=req.body.eventname;
  var email=req.body.email;
  var id=req.body.eventId;
  User.findById(id).then((event)=>{
  //if email exist user verified else not// cannot write code time ended
    });
  });
  



router.post('/verify',authenticate,(req,res)=>{
  var body=_.pick(req.body,['email']);

})

module.exports=router;
