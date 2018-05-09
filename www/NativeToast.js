var exec = require('cordova/exec');

exports.show = function (message, time, success, error) {
    //check if typeof message is string
    if(typeof message !== "string"){
      //throw an error to user
      throw new Error("Native toast need a string message");
    } else if (typeof time !== "number") {
      //throw an error to user
      throw new Error("Please define the time for toast in number formate 0 or 1 for short or long respectively");
    } else{
      //call native side to show toast
      exec(success, error, 'NativeToast', 'show', [message, time]);
    }
};
