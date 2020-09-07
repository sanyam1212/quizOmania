app.controller("QuizzersListCtrl", [ '$scope', '$rootScope', '$state', '$stateParams', 'QuestionService', 'QuizService', 'AnswerService', 'GlobalMethodService', function($scope, $rootScope, $state, $stateParams, QuestionService, QuizService, AnswerService, GlobalMethodService) {
	$scope.isSaveClicked = false;

	$scope.quiz = {};
	$scope.answers = [];
	
	$scope.goBackToList = function() {
		$state.go('quiz.list');
	}
	
	if ($stateParams.quizId) {
		GlobalMethodService.showLoadingDiv('loadingdiv');
		QuizService.findById({id : $stateParams.quizId}, function(response) {
			if (response.data != undefined &&  response.data != null && response.data != "") {
				$scope.quiz = response.data;
				AnswerService.list({quizId: $stateParams.quizId}, function(resp) {
					if (resp.data != undefined &&  resp.data != null && resp.data != "") {
						$scope.answers = resp.data;
						GlobalMethodService.hideLoadingDiv('loadingdiv');
					}
				});
			}
		});
	}
	
	$scope.showQuizzerAnswer = function(quizId, answerId) {
		$state.go("quiz.quizzer", {quizId: quizId, answerId: answerId});
	}

}]);