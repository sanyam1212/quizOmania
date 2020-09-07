'use strict';

app.service('LocalStorageAccessService', ['localStorageService', function(localStorageService) { 
	this.userStorageName = 'user';
	
	this.getUser = function() {
		return localStorageService.get(this.userStorageName);
	}
	this.setUser = function(token) {
		localStorageService.set(this.userStorageName, token);
	}

	this.removeUser = function() {
		localStorageService.remove(this.userStorageName);
	}
	
	this.getValue = function(key) {
		return localStorageService.get(key);
	}
	this.setValue = function(key, value) {
		localStorageService.set(key, value);
	}

	this.removeValue = function(key) {
		localStorageService.remove(key);
	}

}]);