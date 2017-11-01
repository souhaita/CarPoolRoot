//var main_url = "http://localhost:8081/api/";
var main_url = "http://localhost/api/";
var request = "request/";
var account = "account/";

var urlGetTotalTripsCreatedToday = main_url + request + "getTotalTripsCreatedToday";
var urlGetTotalTripsForToday = main_url + request + "getTotalTripsForToday";
var urlGetTotalTrips = main_url + request + "getTotalTrips";

var urlGetTotalCarSeekerCreatedToday = main_url + account + "getTotalCarSeekerCreatedToday";
var urlGetTotalCarPoolerCreatedToday = main_url + account + "getTotalCarPoolerCreatedToday";
var urlGetTotalUser = main_url + account + "getTotalUser";


var urlSetUserStatus = main_url + account + "setUserStatus";


var app = angular.module("ngApp", []);
app.controller("ngCtrl", function($scope,$http) {
	
	$http.post(urlGetTotalTripsCreatedToday).success(function (response) {
    	$scope.totalTripsCreatedToday = response;    	
	});
	
	$http.post(urlGetTotalTripsForToday).success(function (response) {
    	$scope.totalTripsForToday = response;    	
	});
	
	$http.post(urlGetTotalTrips).success(function (response) {
    	$scope.totalTrips = response;    	
	});
	
	$http.post(urlGetTotalCarSeekerCreatedToday).success(function (response) {
    	$scope.totalCarSeekerCreatedToday = response;    	
	});
	
	$http.post(urlGetTotalCarPoolerCreatedToday).success(function (response) {
    	$scope.totalCarPoolerCreatedToday = response;    	
	});
	
	$http.post(urlGetTotalUser).success(function (response) {
    	$scope.totalUser = response;    	
	});
	
	
	$scope.popUpSetUserStatus= function (userStatus) {

		var userId = prompt("Please enter user id");
		
	    if (userId == null || userId == "") {
   	
	    }
	    else{
	    	
    		var data = {'userStatus': userStatus, 'userId': userId};
	    		    	
	    	$http.post(urlSetUserStatus, angular.toJson(data)).success(function (response) {
	        	 	
	    	});
	    	
	    }
	}

});