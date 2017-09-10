var PacketBuilder = require('./public/js/PacketBuilder');
var express = require("express");
var app = express();
var bodyParser = require('body-parser')
var request = require("request")
var fs = require("fs");
var $ = require("jquery");
var jsdom = require("jsdom");
var ByteBuffer = require("bytebuffer");
var net = require("net");


var s = new net.Socket();
s.connect(144, "10.172.12.123");

s.on('error', function (exc) {
    console.log("ignoring exception: " + exc);
});
app.use( bodyParser.json() );
app.use(bodyParser.urlencoded({
  extended: true
}));

app.use( express.static("public"));
app.set("view engine", "ejs");

app.get("/", function(req, res) {
    res.render("home");
});
app.get("/register", function(req, res) {
    res.render("register", {invalid: "", username:"",invalid2: ""});
});
app.get("/login", function(req, res) {
    res.render("login", {invalid: "", username:""});
});

app.post("/login", function(req, res) {
    if(s.destroyed){
        s.connect(144, "10.172.12.123");
    }
    var suc = false;
    var gemacht = true;
    if (req.body !== {}) {
        var us = req.body.username;
        var pw = req.body.password;
        s.on('data', (data) => {
            if(typeof data === "string" && gemacht){
                 var bb = new ByteBuffer.fromUTF8(data)
                 var res1 = bb.readString()              
                 suc = Number(res1) ? true : false;
                if(suc){
                    res.render("map", {reserved: ""})
                    gemacht = false;
                }
                 else {
                    res.render("login", {invalid: "invalid", username:us}); 
                    gemacht = false;
                 }
            } else {
                if(gemacht){
                var res1 = data.toString('utf8');
                 suc = Number(res1) ? true : false; 
                 if(suc){
                    res.render("map", {reserved: ""});
                    gemacht = false;
                 
                 } else {
                    res.render("login", {invalid: "invalid", username:us}); 
                    gemacht = false;
                 }
                }
            }
        }
    );
        PacketBuilder.writeLogin(s,us,pw);
    }else{

    } 
});

app.post("/reserve", function(req, res) {
    if(s.destroyed){
        s.connect(144, "10.172.12.123");
    }
    var suc = false;
    var gemacht = true;
        s.on('data', (data) => {
            if(typeof data === "string" && gemacht){
                 var bb = new ByteBuffer.fromUTF8(data)
                 var res1 = bb.readString();
                if(res1 ==  "0"){
                    res.render("map", {reserved: "Schon reserviert!"});
                    gemacht = false;
                 } else{
                    res.render("map", {reserved: "Box wurde für 15 Minuten reserviert! Dein Code: <b>" + res1 + "</b>\n Achte gut auf ihn!"});
                    gemacht = false;
                }         
                } else {
                    if(gemacht){
                var res1 = data.toString('utf8');
                if(res1 ==  "0"){
                    res.render("map", {reserved: "Schon reserviert!"});
                    gemacht = false;
                 } else{
                    res.render("map", {reserved: "Box wurde für 15 Minuten reserviert! Dein Code: <b>" + res1 + "</b>\n Achte gut auf ihn!"});
                    gemacht = false;
                }       
            }
            }  
            });
           PacketBuilder.writeReserver(s);
});
app.get("/ggg", function(req,res){
    res.render("map", {reserved: "Box wurde für 15 Minuten reserviert! Dein Code: <b>" +"1234"+ "</b>\n Achte gut auf ihn!"});
})
app.post("/register", function(req, res) {
    if(s.destroyed){
        s.connect(144, "10.172.12.123");
    }
    var suc = false;
    var gemacht = true;
    if (req.body !== {}) {
        var us = req.body.username;
        var pw = req.body.password;
        var pw1 = req.body.password1;
        if(pw != pw1){
            res.render("register", {invalid: "", username:us, invalid2: "invalid"});            
        } else {
        s.on('data', (data) => {
            if(typeof data === "string" && gemacht){
                 var bb = new ByteBuffer.fromUTF8(data)
                 var res1 = bb.readString()            
                 console.log(res1);  
                 suc = Number(res1) ? true : false;
                 if(suc){
                    res.render("map", {reserved: ""});
                    gemacht = false;
                  } else{
                    res.render("register", {invalid: "invalid", username:us, invalid2: ""});
                    gemacht = false;
                  }
            } else {
                if(gemacht){
                var res1 = data.toString('utf8');
                 suc = Number(res1) ? true : false;
                 console.log(res1);  
                 if(suc){
                    res.render("map", {reserved: ""});
                    gemacht = false;
                  } else{
                    res.render("register", {invalid: "invalid", username:us,invalid2: ""});
                    gemacht = false;
                  }
            }
        }
         });
        PacketBuilder.writeSignUp(s,us,pw);            
        }
    }else{

    } 
});

app.listen(3000, '0.0.0.0');
