"use strict";
var app = angular.module('userManagementApp', []);

var UserService = function($http) {
	return {
		getAll : function() {
			return $http.get("/user");
		},
		add : function(user) {
			return $http.put("/user",user);
		},
		remove : function(user) {

		},
		edit : function(user) {

		},
		search:function(searchParam){
			return $http.post("/user",searchParam);
		}
	};
}
app.service('UserService', UserService);

var UserCtrl = function(UserService) {
	var vm = this;
	
	 angular.element(document).ready(function () {
		 UserService.getAll().success(function(result){
			 vm.users=result;
		 });
	    });
	
	vm.addUser = function() {
		UserService.add(vm.newUser).success(function(){
			vm.newUser=null;
		});
	};
	
	vm.removeUser=function(){
		UserService.remove(vm.user).success(function(){
			
		});
	};
	
	vm.editUser =function(){
		
	};
	
	vm.search=function(){
		UserService.search(vm.searchParam).success(function(result){
		vm.users=result;
		vm.searchParam=null;
		});
	};
}

app.controller('UserCtrl', UserCtrl);
