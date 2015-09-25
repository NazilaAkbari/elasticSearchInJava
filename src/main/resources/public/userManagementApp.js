"use strict";
var app = angular.module('userManagementApp', []);

var UserService = function($http) {
	return {
		getAll : function() {

		},
		add : function(user) {
			return $http.put("/user", user);
		},
		remove : function(user) {

		},
		edit : function(user) {

		}
	};
}
app.service('UserService', UserService);

var UserCtrl = function(UserSerivce) {
	var vm = this;
	vm.addUser = function() {
		UserService.add(vm.newUser).success(function(){
			
		});
	};
}

app.controller('UuserCtrl', UserCtrl);
