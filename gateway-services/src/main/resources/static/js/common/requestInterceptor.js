app.factory('requestInterceptor', function($q, $rootScope, $location, $window, LocalStorageAccessService) {
	return {
		request: function(config) {
			if($rootScope.showLoadingDiv){
			  angular.element('#loadingdiv').show();
			}
			config.headers.timeZone = jstz.determine().name();	
			return config;
		},
		response: function(response) {
			if(!$rootScope.forceLoadDivToContinueDisplay){
				angular.element('#loadingdiv').hide();
				$rootScope.showLoadingDiv=false;
			}
			return response;
		},
		requestError: function(rejectReason) {
			if(!$rootScope.forceLoadDivToContinueDisplay){
				angular.element('#loadingdiv').hide();
				$rootScope.showLoadingDiv=false;
			}
			return $q.reject(rejectReason);
		},
		responseError: function(rejectReason) {
			angular.element('#loadingdiv').hide();
			$rootScope.showLoadingDiv=false;
			$rootScope.forceLoadDivToContinueDisplay=false;
			var currentURL = $location.url();
			if(rejectReason.status === 401 || rejectReason.status === 403) {
				LocalStorageAccessService.removeUser();
				if(currentURL.indexOf("login") !=-1){
					$rootScope.$emit('errorLogin', {message:'error.usernamePasswordWrong'});
		            return $q.reject(rejectReason);	    
				}
				else{
					$window.location.href = '#/login';
				}
	        }
			else if(rejectReason.status === 429){
				$rootScope.$emit('errorLogin', {message:'error.userMaxLoginAttempts'});
				return $q.reject(rejectReason);
			}
			else if(rejectReason.status === 406){
				$rootScope.$emit('errorLogin', {message:'error.accountDeactivated'});
				return $q.reject(rejectReason);
			}
			else if(rejectReason.status === 430){
				$rootScope.$emit('errorLogin', {message:'error.accountNotAuthenticated'});
				return $q.reject(rejectReason);
			}
			else if(rejectReason.status === 412){
				$rootScope.$emit('errorRequest', {message:rejectReason.statusText});
				return $q.reject(rejectReason);
			}
	        else {
	            return $q.reject(rejectReason);
	        }
		}
	};
});