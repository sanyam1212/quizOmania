app.controller("QuizzerAnswerCtrl", [ '$scope', '$rootScope', '$state', '$stateParams', 'QuestionService', 'QuizService', 'AnswerService', 'GlobalMethodService', function($scope, $rootScope, $state, $stateParams, QuestionService, QuizService, AnswerService, GlobalMethodService) {
	$scope.isSaveClicked = false;

	$scope.quiz = {};
	$scope.questions = [];
	$scope.answer = {
		answers: {}
	};
	
	$scope.goBackToList = function() {
		$state.go("quiz.quizzers", {quizId: $stateParams.quizId})
	}
	
	if ($stateParams.quizId) {
		GlobalMethodService.showLoadingDiv('loadingdiv');
		QuizService.findById({id : $stateParams.quizId}, function(response) {
			if (response.data != undefined &&  response.data != null && response.data != "") {
				$scope.quiz = response.data;
				QuestionService.list({quizId: $stateParams.quizId}, function(response) {
					if (response.data != undefined &&  response.data != null && response.data != "") {
						$scope.questions = response.data;
						AnswerService.findById({id: $stateParams.answerId}, function(resp) {
							if (resp.data != undefined &&  resp.data != null && resp.data != "") {
								$scope.answers = resp.data;
								for(var i=0; i<$scope.questions.length; i++) {
									if ($scope.answers.answers[$scope.questions[i].id] != null && $scope.answers.answers[$scope.questions[i].id].length != 0) {										
										$scope.questions[i].quizzerAnswer = $scope.answers.answers[$scope.questions[i].id][0];
									}
								}
								GlobalMethodService.hideLoadingDiv('loadingdiv');
							}
						});
					}
				});
			}
		});
	}

}]);