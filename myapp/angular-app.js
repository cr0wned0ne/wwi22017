var myAngularApp = angular.module('myAngularApp', []);

myAngularApp.controller('mainController', function($scope) {

  $scope.yourName = "Tom";

 $scope.users = ['Tim', 'tom', 'tam']

  $scope.logIt = function() {
    console.log($scope.yourName);
  };


 $scope.validate = function() {
   return $scope.yourName.length > 3 && $scope.yourName.length < 6; 
 }


})
