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
	
	//$("#registerButton").on('click', function(){lovecat.register()});
}

lovecat.register = function() {
	var regUsername = document.querySelector('#register_username').value;
	var regPassword = document.querySelector('#register_password').value;
	var uploadFile = document.querySelector('#register_file');
	
	var files = uploadFile.files;
    if(files.length === 0) {
    	alert("Please select a profile picture!");
    	return;
    }
    var profileImg = files[0];
    
    
    var formData = new FormData();
    formData.append("username", regUsername);
    formData.append("password", regPassword);
    formData.append("file", profileImg);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/users");

    xhr.onload = function() {
        console.log(xhr.responseText);
        if(xhr.status == 200) {
        	lovecat.username = regUsername;
        	lovecat.loggedIn = true;
			lovecat.hideLogin();
			lovecat.showContent();
        } else {
        	alert("Cannot register user!");
        }
    }

    xhr.send(formData);
};

lovecat.login = function() {
	lovecat.username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	if (lovecat.username && lovecat.password) {
		$.post('/lovecat-server/login', {
			username : lovecat.username,
			password : password
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
	if (!lovecat.loggedIn) {
		var headerControls = document.getElementsByClassName('login');
		for (var i = 0; i < headerControls.length; i++) {
			ctrl = headerControls[i];
			ctrl.style.display = 'block';
		}
	}
}

lovecat.hideLogin = function() {
	if (lovecat.loggedIn) {
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
	lovecat.loadFirstMatch();
}

lovecat.next = function() {
/**	var profileImg = document.getElementById('profileImg');
	var src = profileImg.src;
	var path = src.substring(0, src.lastIndexOf("/") + 1);
	var file = src.substring(src.lastIndexOf("/") + 1, src.length);
	var number = file.substring(0, file.lastIndexOf("."));
	if (number < 1) {
		number++;
	} else {
		number = 0;
	}
	var newPath = path + number + ".jpg";
	profileImg.src = newPath;**/
	lovecat.loadNextMatch();
}

lovecat.loadFirstMatch = function() {
	$.get("/users/"+lovecat.username +"/random/", {
		page : 1,
		size : 1
	}, function(data) {
		console.log(data);
		var profileImg = document.getElementById('profileImg');
		profileImg.src = data._embedded.userProfileDtoList[0]._links.img.href;
		lovecat.matches = data.page.totalPages;
		lovecat.currentpage = 0;
	})
}

lovecat.loadNextMatch = function() {
	if (lovecat.currentpage === lovecat.matches) {
		lovecat.currentpage = 0;
	}
	$.get("/users/"+ lovecat.username +"/random/", {
		page : lovecat.currentpage,
		size : 1
	}, function(data) {
		console.log(data);
		var profileImg = document.getElementById('profileImg');
		profileImg.src = data._embedded.userProfileDtoList[0]._links.img.href;
		lovecat.matches = data.page.totalPages;
		lovecat.currentpage++;
	})
}

lovecat.getLocation = function() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(lovecat.showPosition);
	} else {
		geoDemo.innerHTML = "Geolocation is not supported by this browser.";
	}
}

lovecat.showPosition = function(position) {
	document.getElementById('geoDemo').innerHTML = "Latitude: "
			+ position.coords.latitude + "<br>Longitude: "
			+ position.coords.longitude;
}

lovecat.load();
