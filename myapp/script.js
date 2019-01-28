var lamiau = new Object();

lamiau.load = function() {
  console.log("logged in: " + lamiau.loggedIn)
  if (!lamiau.loggedIn) {
    lamiau.hideContent();
  }

  var rawButton = document.getElementById('rawButton');
  rawButton.addEventListener('click', function() {
    console.log('raaaaw');
  });

  rawButton.addEventListener('click', function() {
    lamiau.next();
  })

  var nopeButton = document.getElementById('nopeButton');
  nopeButton.addEventListener('click', function() {
    console.log('nope!');
  });
  nopeButton.addEventListener('click', function() {
    lamiau.next();
  })
}


lamiau.login = function() {
  lamiau.username = document.getElementById('username').value;
  lamiau.password = document.getElementById('password').value;
  if (lamiau.username && lamiau.password) {
    lamiau.loggedIn = true;
    this.hideLogin();
    this.showContent();
  }

}

lamiau.hideLogin = function() {
  if(lamiau.loggedIn) {
    var headerControls = document.getElementsByClassName('headerControl');
    for (var i = 0; i < headerControls.length; i++) {
      ctrl = headerControls[i];
      ctrl.style.display = 'none';
    }
    this.showContent();
  }
}

lamiau.hideContent = function() {
  var content = document.getElementById('content');
  content.style.display = 'none';
}
lamiau.showContent = function() {
  var content = document.getElementById('content');
  content.style.display = 'block';
}

lamiau.next = function() {
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


lamiau.getLocation = function() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(lamiau.showPosition);
  } else {
    geoDemo.innerHTML = "Geolocation is not supported by this browser.";
  }
}

lamiau.showPosition = function(position) {
  document.getElementById('geoDemo').innerHTML = "Latitude: " + position.coords.latitude +
  "<br>Longitude: " + position.coords.longitude;
}

lamiau.load();
