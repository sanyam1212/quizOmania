app.controller("QuizAttemptCtrl", [ '$scope', '$rootScope', '$state', '$stateParams', 'QuestionService', 'QuizService', 'AnswerService', 'GlobalMethodService', function($scope, $rootScope, $state, $stateParams, QuestionService, QuizService, AnswerService, GlobalMethodService) {
	$scope.isSaveClicked = false;

	$scope.quiz = {};
	$scope.questions = [];
	$scope.answer = {
		answers: {}
	};
	$scope.isShowQuiz = false;
	$scope.login = {
			password: ""
	}
	
	$scope.goBackToList = function() {
		$state.go('quiz.list');
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
				var radioValue = $("input[name='question-"+$scope.questions[i].showOrder+"']:checked").val();
	            if(radioValue){
	            	$scope.answer.answers[$scope.questions[i].id].push(radioValue);
	                break;
	            }
			}
		}
		if (!confirm('Are you sure you want to submit the quiz? Once submitted, quiz cannot be edited.')) {
			return;
		}
		$scope.answer.quizName = $scope.quiz.name;
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
	
	$scope.startQuiz = function() {
		$scope.isStartQuizClicked = true;
		if (!GlobalMethodService.isBlank($scope.login.password)) {
			QuizService.authenticateQuizzer({id: $stateParams.quizId, quizzerPassword: $scope.login.password}, function(response) {
				if (response.data != undefined && response.data != null) {
					if (response.data) {
						$scope.isShowQuiz = true;
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
					} else {
						var messages = [{
							"type" : 'error',
							"string" : 'Wrong Password. Please try again.'
						}];
						GlobalMethodService.showMessage("template1", messages);
					}
				} else {
					var messages = [{
						"type" : 'error',
						"string" : 'There was some error in authentication. Please try again.'
					}];
					GlobalMethodService.showMessage("template1", messages);
				}
			});
		} else {
			var messages = [{
				"type" : 'error',
				"string" : 'Password cannot be empty.'
			}];
			GlobalMethodService.showMessage("template1", messages);
		}
	}

}]);