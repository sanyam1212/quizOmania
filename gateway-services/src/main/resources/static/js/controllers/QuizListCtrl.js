app.controller("QuizListCtrl", [ '$scope', '$rootScope', '$state', '$stateParams', 'ConfirmService', 'QuizService', 'GlobalMethodService',
	function($scope, $rootScope, $state, $stateParams, ConfirmService, QuizService, GlobalMethodService) {
	$scope.quizes = [];
	$scope.limit = 10;
	$scope.offset = 0;
	$scope.hoveredCardNumber = -1;
	
	// $scope.userId = $stateParams.userId;
	
	$scope.getQuizList = function() {
		GlobalMethodService.showLoadingDiv('loadingdiv');
		$scope.searchRequest = {
			limit: $scope.limit,
			offset: $scope.offset,
			sortBy: 'name',
			order: 'ASC'
		};
		QuizService.list($scope.searchRequest, function(response) {
			if (!GlobalMethodService.isBlank(response.data)) {
				$scope.quizes = response.data;
			}
			GlobalMethodService.hideLoadingDiv('loadingdiv');
		});
	}
	$scope.getQuizList();
	
	$scope.addQuiz = function() {
		$state.go("quiz.new");
	}

	$scope.quizzersList = function(quizId) {
		$state.go("quiz.quizzers", {quizId: quizId});
	}
	
	$scope.attempt = function(quizId) {
		$state.go("quiz.attempt", {quizId: quizId});
	}
	
	
    $scope.$on('searchMade', function(event, obj) {
        $scope.searchText = obj;
    });
	
}]);