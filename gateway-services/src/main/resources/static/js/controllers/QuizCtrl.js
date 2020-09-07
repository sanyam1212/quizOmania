app.controller("QuizCtrl", [ '$scope', '$rootScope', 'LocalStorageAccessService', 'GlobalMethodService', function($scope, $rootScope, LocalStorageAccessService, GlobalMethodService) {
	
	// GlobalMethodService.globalCheckLogin();
	// $scope.user = LocalStorageAccessService.getUser();

	$scope.search = function() {
		$rootScope.$broadcast('searchMade', $scope.searchText);
	}
	
}]);