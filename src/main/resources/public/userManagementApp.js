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

var UserCtrl = function(UserService) {
	var vm = this;
	vm.addUser = function() {
		UserService.add(vm.newUser).success(function(){
		});
	};
	
	vm.removeUser=function(){
		UserService.remove(vm.user).success(function(){
			
		});
	};
	
	vm.editUser =function(){
		
	};
}

app.controller('UserCtrl', UserCtrl);
