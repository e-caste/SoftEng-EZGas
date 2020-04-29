function logout() {
	sessionStorage.removeItem("userid");
	sessionStorage.removeItem("username");
	sessionStorage.removeItem("token");
	sessionStorage.removeItem("email");
	sessionStorage.removeItem("reputation");
	sessionStorage.removeItem("admin");
	location.reload();
}

function saveSession(userData) {
	sessionStorage.setItem("userid",userData.userId);
  	sessionStorage.setItem("username", userData.userName);
  	sessionStorage.setItem("token", userData.token);
  	sessionStorage.setItem("email", userData.email);
  	sessionStorage.setItem("reputation", userData.reputation);
  	sessionStorage.setItem("admin", userData.admin);
}

function buttonVisibility(id, toShow) {
	var x = document.getElementById(id);
   	if (x) {
		if (toShow) {
  			x.style.display = "block";
  		} else {
    		x.style.display = "none";
		}
	}	
}

function checkSession() {
	if (sessionStorage.getItem('username')) {
    	buttonVisibility('login',false);
    	buttonVisibility('logout',true);
    	if (sessionStorage.getItem('admin') == 'true') {
    		buttonVisibility('addpoi',true);
    	} else {
    		buttonVisibility('addpoi',false);
    	}
    	buttonVisibility('updateprofile',true);
    } else {
    	buttonVisibility('addpoi',false);
    	buttonVisibility('updateprofile',false);
    	buttonVisibility('logout', false);
    }
}

function updateData(data) {
	buttonVisibility('login', false);
	buttonVisibility('update', true);
}

function addData(id, data) {
  var event = new Event('input', {
    bubbles: true,
    cancelable: true,
  });
  var element = document.getElementById(id);
  element.dispatchEvent(event);
  element.value = data;
}

function showName(id) {
	if (sessionStorage.getItem('username')) {
    var path = window.location.pathname;
    window.currentPage = path.split("/").pop();

    window.reputation = sessionStorage.getItem('reputation');
    window.userid = sessionStorage.getItem('userid');
		window.elementID = id;
    message = "Hello " + sessionStorage.getItem('username') + " your reputation is " + window.reputation;
	} else {
		message = "";
  	}
  	
	document.getElementById(id).innerText = message;
}

function updateReputation() {
  
  var message = "Hello " + sessionStorage.getItem('username') + " your reputation is " + window.reputation;
  sessionStorage.setItem("reputation", window.reputation);
    
  document.getElementById(window.elementID).innerText = message;
}

function writeLoginResult(userData) {
  	
  	var message;
  	
  	if (userData.userName) {
  		message = "Hello " + userData.userName + " your reputation is " + userData.reputation;
  		saveSession(userData);
  		//setTimeout(function(){
   			window.location='/';
		//}, 2000);
  		
  	} else {
  		message = "Login failed";
  	}
  	
  	document.getElementById('loginResult').innerText = message;
  	checkSession();
  	
  }

function checkLogin() {
	if (!sessionStorage.getItem('username')) {
		buttonVisibility('form',false);
		document.getElementById('loginResult').innerText = 'You need to login';
		setTimeout(function(){
			window.location='/login';
		}, 1000);
	}
}

function getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function setCurrentLocation(lat,lon,acc) {
	var now = new Date();
	var time = now.getTime();
	time += 3600 * 1000;
	now.setTime(time);
	document.cookie = 
	'mylat=' + lat + 
	'; expires=' + now.toUTCString() + 
	'; path=/';
	document.cookie = 
	'mylon=' + lon + 
	'; expires=' + now.toUTCString() + 
	'; path=/';
	document.cookie = 
	'myacc=' + acc + 
	'; expires=' + now.toUTCString() + 
	'; path=/';

}
