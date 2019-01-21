//alert('Hello from script!');
var login = function() {

  var username = document.getElementById('username').value;
  var password = document.getElementById('password').value;
  console.log("Username:  " + username);
  console.log("Password: " + password);
}

var makeBold = function() {
  var myPara = document.getElementById('oldParagraph');
  myPara.style.fontWeight = 'normal';
}

var makeNormal = function() {
  var myPara = document.getElementById('oldParagraph');
  myPara.style.fontWeight = 'bold';
}

var addParagraph = function() {
  var newPar = document.createElement('p');
  newPar.innerHTML = 'I was made by Javascript!';
  document.getElementById('myArticle').appendChild(newPar);
}

document.getElementById('removeParagraph').onclick = function() {
  var oldPara = document.getElementById('oldParagraph');
  document.getElementById('myArticle').removeChild(oldPara);
}

var addButton  = document.getElementById('addParagraph');
console.log(addButton);
addButton.addEventListener('click', function() {alert('click')});
addButton.addEventListener('click', addParagraph);




var myApp = new Object();

myApp.person = {
  firstname : 'Peter'
}

myApp.person.username = 'peter1989';

myApp.course = {id: "wwi22017"};


myApp.popup = function(x) {
  console.log(x);
}

myApp.changeArticles = function() {
  var myArticle = document.getElementsByTagName('article');
  alert(myArticle.length);
  for (i = 0; i < myArticle.length; i++) {

    var article = myArticle[i];
    console.log("article" +article)
    article.innerHTML = "changed by Javascript!";
    article.style.color = "red";
  }


}

//myApp.changeArticles();



myApp.compare = function(x,y) {
  console.log(typeof x);
  console.log( x === y);
}

myApp.compare(myApp.person, 5);

myApp.myFunction = function(a, b, popup) {
  popup(a + b);
  return a + b;
}

myApp.myFunction('10',5, myApp.popup);


var x = document.getElementById("geoDemo");
function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
  } else {
    x.innerHTML = "Geolocation is not supported by this browser.";
  }
}

function showPosition(position) {
  x.innerHTML = "Latitude: " + position.coords.latitude +
  "<br>Longitude: " + position.coords.longitude;
}

getLocation();
