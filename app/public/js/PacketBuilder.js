var writeLogin = function(s, us, pw) {
  s.write("2001");
  setTimeout(function(){
    s.write(us + "::" + pw + "::" + "000");
  }, 175);
   
  };
  var writeSignUp = function(s, us, pw) {
    s.write("2002");
    setTimeout(function(){
      s.write(us + "::" + pw + "::" + "000");
    }, 175);
     
    };
    var writeReserver = function(s) {
      s.write("2002");
      setTimeout(function(){
        s.write("000");
      }, 175);
       
      };
  module.exports.writeLogin = writeLogin;
  module.exports.writeSignUp = writeSignUp;
  module.exports.writeReserver = writeReserver;