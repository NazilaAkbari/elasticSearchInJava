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
		remove : function(id) {
			console.log(id);
			return $http.delete("/user",{params: {id: id}});
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
	var getAll = function(){
		UserService.getAll().success(function(result){
			 vm.users=result;
		 });
		};
	
		 getAll();
	
	vm.addUser = function() {
		UserService.add(vm.newUser).success(function(){
			vm.newUser=null;
			setTimeout(getAll,1000);
		});
	};
	
	vm.removeUser=function(id){
		UserService.remove(id).success(function(){
			setTimeout(getAll,1000);
		});
	};
	
	vm.editUser =function(){
		
	};
	
	vm.search=function(){
		UserService.search(vm.searchParam).success(function(result){
		vm.users=result;
		});
	};
}

app.controller('UserCtrl', UserCtrl);
