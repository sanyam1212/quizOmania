app.controller("QuizAttemptCtrl", [ '$scope', '$rootScope', '$state', '$stateParams', 'QuestionService', 'QuizService', 'AnswerService', 'GlobalMethodService', function($scope, $rootScope, $state, $stateParams, QuestionService, QuizService, AnswerService, GlobalMethodService) {
	$scope.isSaveClicked = false;

	$scope.quiz = {};
	$scope.questions = [];
	$scope.answer = {
		answers: {}
	};
	
	$scope.goBackToList = function() {
		$state.go('quiz.list');
	}
	
	if ($stateParams.quizId) {
		GlobalMethodService.showLoadingDiv('loadingdiv');
		QuizService.findById({id : $stateParams.quizId}, function(response) {
			if (response.data != undefined &&  response.data != null && response.data != "") {
				$scope.quiz = response.data;
				QuestionService.list({quizId: $stateParams.quizId}, function(response) {
					if (response.data != undefined &&  response.data != null && response.data != "") {
						$scope.questions = response.data;
						for(var i=0; i<$scope.questions.length; i++) {
							$scope.answer.answers[$scope.questions[i].id] = [];
						}
						$scope.answer.quizId = $stateParams.quizId;
						GlobalMethodService.hideLoadingDiv('loadingdiv');
					}
				});
			}
		});
	}
	
	$scope.submitQuiz = function() {
		$scope.isSaveClicked = true;
		$rootScope.showLoadingDiv=true;
		$rootScope.forceLoadDivToContinueDisplay=true;
		if (GlobalMethodService.isBlank($scope.answer.email)) {
			$rootScope.showLoadingDiv=false;
			$rootScope.forceLoadDivToContinueDisplay=false;
			return;
		}
		for(var i = 0; i < $scope.questions.length; i++) {
			if ($scope.questions[i].question == "" || $scope.questions[i].options.length < 2) {
				return;
			}
			for(var j = 0; j < $scope.questions[i].options.length; j++) {
				console.log($scope.questions[i].options);
				var radioValue = $("input[name='question-"+$scope.questions[i].showOrder+"']:checked").val();
				console.log("radioValue QAC ===>>> " + radioValue);
	            if(radioValue){
	            	$scope.answer.answers[$scope.questions[i].id].push(radioValue);
	                console.log("radioValue" + radioValue);
	                break;
	            }
			}
		}
		console.log("Final Answer ===>>> ", $scope.answer)
		if (!confirm('Are you sure you want to submit the quiz? Once submitted, quiz cannot be edited.')) {
			return;
		}
		AnswerService.saveAnswer($scope.answer, function(response) {
			if (response.data != undefined && response.data != null && response.data != '' ) {
				var messages = [{
					"type" : 'success',
					"string" : 'Quiz Submitted Successfully. Your score is: ' + response.data.score
				}];
				GlobalMethodService.showMessageForCustomTime("template1", messages, 100000);
				$state.go('quiz.list');
			} else {
				var messages =  [{
					"type" : 'error',
					"string" : 'There was some error in submitting the quiz. Please try again.'
				}];
				GlobalMethodService.showMessage("template1", messages);
			}
			$rootScope.showLoadingDiv=false;
			$rootScope.forceLoadDivToContinueDisplay=false;
		});
	}

}]);