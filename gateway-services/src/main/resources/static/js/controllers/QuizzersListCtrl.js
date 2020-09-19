app.controller("QuizzersListCtrl", [ '$scope', '$rootScope', '$state', '$stateParams', 'QuestionService', 'QuizService', 'AnswerService', 'GlobalMethodService', function($scope, $rootScope, $state, $stateParams, QuestionService, QuizService, AnswerService, GlobalMethodService) {
	$scope.isSaveClicked = false;

	$scope.quiz = {};
	$scope.answers = [];
	$scope.login = {
			password: ""
	};
	$scope.isshowQuizzersListClicked = false;
	$scope.isShowQuizzersList = false;
	
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
	
	$scope.showQuizzersList = function() {
		$scope.isshowQuizzersListClicked = true;
		if (!GlobalMethodService.isBlank($scope.login.password)) {
			QuizService.authenticateQuizMaster({id: $stateParams.quizId, quizPassword: $scope.login.password}, function(response) {
				if (response.data != undefined && response.data != null) {
					if (response.data) {
						$scope.isShowQuizzersList = true;
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