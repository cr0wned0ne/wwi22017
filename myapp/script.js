var lovecat = new Object();

lovecat.load = function() {
  console.log("logged in: " + lovecat.loggedIn)
  if (!lovecat.loggedIn) {
    lovecat.hideContent();
  }

  rawButton = document.getElementById('rawButton');
  console.log(rawButton);
  rawButton.addEventListener('click', function() {
    console.log('raaaaw');
  });

  rawButton.addEventListener('click', function() {
    lovecat.next();
  })

  var nopeButton = document.getElementById('nopeButton');
  nopeButton.addEventListener('click', function() {
    console.log('nope!');
  });
  nopeButton.addEventListener('click', function() {
    lovecat.next();
  })
}


lovecat.login = function() {
  lovecat.username = document.getElementById('username').value;
  lovecat.password = document.getElementById('password').value;
  if (lovecat.username && lovecat.password) {
	$.post('/lovecat-server/login', {
		username : lovecat.username,
		password : lovecat.password
	}, function(data) {
		  lovecat.loggedIn = true;
		  lovecat.hideLogin();
		  lovecat.showContent();
	}).fail(function() {
		alert("Wrong username or password");
	})
	  
	  
 
  }
}

lovecat.logout = function() {
  lovecat.hideContent();
  lovecat.loggedIn = false;
  lovecat.username = undefined;
  lovecat.password = undefined;
  lovecat.showLogin();
}

lovecat.showLogin = function() {
  if(!lovecat.loggedIn) {
    var headerControls = document.getElementsByClassName('login');
    for (var i = 0; i < headerControls.length; i++) {
      ctrl = headerControls[i];
      ctrl.style.display = 'block';
    }
  }
}

lovecat.hideLogin = function() {
  if(lovecat.loggedIn) {
    var headerControls = document.getElementsByClassName('login');
    for (var i = 0; i < headerControls.length; i++) {
      ctrl = headerControls[i];
      ctrl.style.display = 'none';
    }
    this.showContent();
  }
}

lovecat.hideContent = function() {
  var content = document.getElementById('content');
  content.style.display = 'none';

  var button = document.getElementById('logoutButton');
  button.style.display = 'none';

  var landing = document.getElementById('landing');
  landing.style.display = "block";

}

lovecat.showContent = function() {
  var content = document.getElementById('content');
  content.style.display = 'block';

  var content = document.getElementById('logoutButton');
  content.style.display = 'block';

  var landing = document.getElementById('landing');
  landing.style.display = "none";
}

lovecat.next = function() {
  var profileImg = document.getElementById('profileImg');
  var src = profileImg.src;
  var path = src.substring(0, src.lastIndexOf("/") + 1);
  var file = src.substring( src.lastIndexOf("/") + 1, src.length);
  var number = file.substring(0, file.lastIndexOf("."));
  if (number < 1) {
    number++;
  } else {
    number = 0;
  }
  var newPath = path + number + ".jpg";
  profileImg.src = newPath;
}


lovecat.getLocation = function() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(lovecat.showPosition);
  } else {
    geoDemo.innerHTML = "Geolocation is not supported by this browser.";
  }
}

lovecat.showPosition = function(position) {
  document.getElementById('geoDemo').innerHTML = "Latitude: " + position.coords.latitude +
  "<br>Longitude: " + position.coords.longitude;
}

lovecat.load();
