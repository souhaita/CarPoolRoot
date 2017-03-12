var url = "http://localhost/api/account/createAdmin"

var app = angular.module("ngApp", []);
app.controller("ngCtrl", function($scope,$http) {
    $scope.submit = function () {
        console.log("You clicked submit!");
        
        $http.post(url, $scope.account).success(function (response) {
			if (response != "") {
				console.log(response);
	 		}
			else{
				console.log("Else "+response);
			}
		});
        
        
    }
});