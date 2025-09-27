function checkdata(){
      //Create references to the input elements we wish to validate
      var username = document.getElementById("name");
      var email_address = document.getElementById("email");

      //Check if username field is empty
      if(username.value == ""){
            alert("Please enter the name");
            username.focus();
            return false;
      }
     //Check if email field is empty
      if(email_address.value == ""){
            alert("Please enter the email");
            email_address.focus();
            return false;
      }
     //If all is well return true.
      return true;
}
