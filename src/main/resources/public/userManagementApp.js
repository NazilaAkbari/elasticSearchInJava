"use strict";
var app = angular.module('userManagementApp', []);

var UserService = function($http) {
	return {
		getAll : function() {

		},
		add : function(user) {
			console.log(user);
			return $http.put("/user",user);
		},
		remove : function(user) {

		},
		edit : function(user) {

		},
		search:function(searchParam){
			return $http({url:"/user", method:"GET",params:{searchParam}});
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
	
	vm.search=function(){
		UserService.search(vm.searchParam).success(function(){
		});
	};
}

app.controller('UserCtrl', UserCtrl);
