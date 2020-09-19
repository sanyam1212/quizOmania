app.controller("AddQuizCtrl", [ '$scope', '$rootScope', '$state', '$stateParams', 'QuestionService', 'QuizService', 'GlobalMethodService', function($scope, $rootScope, $state, $stateParams, QuestionService, QuizService, GlobalMethodService) {
	$scope.isSaveClicked = false;
	$scope.quizCategories = [ 'History', 'Mathematics',
			'Science', 'Hindi', 'English',
			'Geography', 'Civics', 'Sanskrit',
			'Engineering', 'Computer', 'Railways', 'Roadways',
			'Airways', 'Solar System'];
	$scope.questionCategories = ['Single Select', 'Multiple Select']
	$scope.questions = [];
	$scope.question = {
		question: '',
		options: [],
		answers: [],
		category: 'Single Select',
		option: '',
		isAnswerSelected: false
	}
	
	$scope.goBackToList = function() {
		$state.go('quiz.list');
	}
	
	$scope.addQuiz = function() {
		$scope.isSaveClicked = true;
		$rootScope.showLoadingDiv=true;
		$rootScope.forceLoadDivToContinueDisplay=true;
		if (GlobalMethodService.isBlank($scope.quiz.name)
			|| GlobalMethodService.isBlank($scope.quiz.email)
			|| GlobalMethodService.isBlank($scope.quiz.category)
			|| GlobalMethodService.isBlank($scope.quiz.quizPassword)
			|| GlobalMethodService.isBlank($scope.quiz.quizzerPassword)
			|| $scope.questions.length == 0) {
			$rootScope.showLoadingDiv=false;
			$rootScope.forceLoadDivToContinueDisplay=false;
			return;
		}
		var isAllAnswersSelected = true;
		for(var i = 0; i < $scope.questions.length; i++) {
			if ($scope.questions[i].question == "" || $scope.questions[i].options.length < 2) {
				return;
			}
			for(var j = 0; j < $scope.questions[i].options.length; j++) {
				var radioValue = $("input[name='question-"+$scope.questions[i].showOrder+"']:checked").val();
	            if(radioValue){
	            	$scope.questions[i].isAnswerSelected = true;
	            	$scope.questions[i].answers.push(radioValue);
	            	break;
	            }
			}
			if(!$scope.questions[i].isAnswerSelected) {
				isAllAnswersSelected = false;
			}
		}

		if(!isAllAnswersSelected) {
			return;
		}
		QuizService.addQuiz($scope.quiz, function(response) {
			if (response.data != undefined && response.data != null && response.data != '' ) {
				for (var i = $scope.questions.length - 1; i >= 0; i--) {
					$scope.questions[i].quizId = response.data.id;
				}
				QuestionService.addQuestions($scope.questions, function(resp) {
					if (resp.data != undefined && resp.data != null && resp.data != '' ) {
						var messages = [{
							"type" : 'success',
							"string" : 'Quiz Added Successfully.'
						}];
						GlobalMethodService.showMessage("template1", messages);
						$state.go('quiz.list');
					}
				});
			} else {
				var messages =  [{
					"type" : 'error',
					"string" : $filter('translate')('error.errorInAddingQuiz')
				}];
				GlobalMethodService.showMessage("template1", messages);
			}
			$rootScope.showLoadingDiv=false;
			$rootScope.forceLoadDivToContinueDisplay=false;
		});
	}

	$scope.addQuestion = function() {
		$scope.isSaveClicked = false;
		$scope.question.showOrder = $scope.questions.length;
		$scope.questions.push(angular.copy($scope.question))
	}

	$scope.addOption = function(index) {
		$scope.isSaveClicked = false;
		if (!GlobalMethodService.isBlank($scope.questions[index].option)) {
			$scope.questions[index].options.push(angular.copy($scope.questions[index].option));
			$scope.questions[index].option = '';
		}
	}
	
	$scope.deleteQuestion = function(index) {
		$scope.questions.splice(index, 1);
		for(var i = 2; i < $scope.questions.length; i++) {
			$scope.questions[i].showOrder = $scope.questions[i].showOrder - 1;
		}
	}
	
	$scope.deleteOption = function(questionOrder, optionIndex) {
		$scope.isSaveClicked = false;
		$scope.questions[questionOrder].options.splice(optionIndex, 1);
	}

}]);