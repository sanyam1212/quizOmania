app.controller("QuizzerAnswerCtrl", [ '$scope', '$rootScope', '$state', '$stateParams', 'QuestionService', 'QuizService', 'AnswerService', 'GlobalMethodService', function($scope, $rootScope, $state, $stateParams, QuestionService, QuizService, AnswerService, GlobalMethodService) {
	$scope.isSaveClicked = false;

	$scope.quiz = {};
	$scope.questions = [];
	$scope.email = "";
	
	$scope.goBackToList = function() {
		$state.go("quiz.quizzers", {quizId: $stateParams.quizId})
	}
	
	if ($stateParams.quizId) {
		GlobalMethodService.showLoadingDiv('loadingdiv');
		QuizService.findById({id : $stateParams.quizId}, function(response) {
			if (response.data != undefined &&  response.data != null && response.data != "") {
				$scope.quiz = response.data;
				AnswerService.findById({id: $stateParams.answerId}, function(resp) {
					if (resp.data != undefined &&  resp.data != null && resp.data != "") {
						$scope.questions = resp.data.questions;
						$scope.email = resp.data.email;
						GlobalMethodService.hideLoadingDiv('loadingdiv');
					}
				});
			}
		});
	}

}]);