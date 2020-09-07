app.controller("QuizHomeCtrl", [ '$scope', '$state', '$rootScope', 'GlobalMethodService', function($scope, $state, $rootScope, GlobalMethodService) {
	$rootScope.showLoadingDiv=false;
	$rootScope.forceLoadDivToContinueDisplay=false;
	GlobalMethodService.hideLoadingDiv('loadingdiv');
	GlobalMethodService.hideLoadingDiv('loadingdiv');
	
	
	$scope.goBackToList = function() {
		$state.go('quiz.list');
	}	
	
}]);