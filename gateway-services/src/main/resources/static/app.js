var app = angular.module("quizomania", [ 'ngResource', 'LocalStorageModule',
		'pascalprecht.translate', 'ngRoute', 'ui.router', 'infinite-scroll',
		 'ui.bootstrap', 'ngSanitize', 'ui.grid',
		'ngCookies' ]);

app.config([
	'$routeProvider',
	'$stateProvider',
	'$urlRouterProvider',
	'$locationProvider',
	'$httpProvider',
	'$translateProvider',
	function($routeProvider, $stateProvider, $urlRouterProvider,
			$locationProvider, $httpProvider, $translateProvider) {
		$locationProvider.hashPrefix('');
		$stateProvider.state('quiz', {
			url : "/quiz",
			controller : 'QuizCtrl',
			templateUrl : "views/quiz.html",
			pageTitle : 'Quiz'
		}).state('quiz.list', {
			url : "/list",
			controller : 'QuizListCtrl',
			templateUrl : "views/quizList.html",
			pageTitle : 'Quiz List'
		}).state('quiz.home', {
			url : "/home",
			controller : 'QuizHomeCtrl',
			templateUrl : "views/quizHome.html",
			pageTitle : 'Home'
		}).state('quiz.new', {
			url : "/new",
			controller : 'AddQuizCtrl',
			templateUrl : "views/addQuiz.html",
			pageTitle : 'Add New Quiz'
		}).state('quiz.quizzers', {
			url : "/quizzers/:quizId",
			controller : 'QuizzersListCtrl',
			templateUrl : "views/quizzersList.html",
			pageTitle : 'Quizzers'
		}).state('quiz.attempt', {
			url : "/attempt/:quizId",
			controller : 'QuizAttemptCtrl',
			templateUrl : "views/quizAttempt.html",
			pageTitle : 'Quiz Attempt'
		}).state('quiz.quizzer', {
			url : "/quizzer/:quizId/:answerId",
			controller : 'QuizzerAnswerCtrl',
			templateUrl : "views/quizzerAnswer.html",
			pageTitle : 'Quizzer Answer'
		});

		$urlRouterProvider.otherwise('/quiz/list');
		$httpProvider.interceptors.push('requestInterceptor');
		
	}
]);

app.config['$translateProvider', function($translateProvider) {
    $translateProvider
    .useStaticFilesLoader({
        prefix: 'i18n/',
        suffix: '.json'
    });
    $translateProvider.preferredLanguage('en');
    $translateProvider.useLocalStorage();
    $translateProvider.useSanitizeValueStrategy('sanitizeParameters');
    $translateProvider.useMissingTranslationHandlerLog();
}];

app.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}]);

app.run(function($rootScope, $state, localStorageService, LocalStorageAccessService ) {
	$rootScope.closeMessage = function() {
		$rootScope.message = {};
	}
	$rootScope.$on('$stateChangeStart', function(event, current, currentParam,
			from, fromParams) {
		var user = LocalStorageAccessService.getUser();
		$rootScope.showGLobalSearch = false;
		$rootScope.pageTitle = current.pageTitle;
		// if (current.url != '/sessionlogin' && current.url != '/login'
		// 		&& current.url.indexOf('/sessionlogin') != 0
		// 		&& current.url.indexOf('/signup') != 0
		// 		&& current.url != '/createPassword'
		// 		&& current.url != '/forgotPassword'
		// 		&& localStorageService.get('user') == null) {
		// 	event.preventDefault();
		// 	$state.go('login');
		// }
	});
});

app.run(['$anchorScroll', function($anchorScroll) {
	$anchorScroll.yOffset = 116;   // always scroll by 50 extra pixels
}])


app.directive('numbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
});

app.directive('decimalNumbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9\.]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
});

//Set the Page Title
app.directive('updateTitle', ['$rootScope', '$timeout',
  function($rootScope, $timeout) {
    return {
      link: function(scope, element) {

        var listener = function(event, toState) {

          var title = 'Quizomania';
          if (toState.pageTitle) title = toState.pageTitle +" | Quizomania";

          $timeout(function() {
            element.text(title);
          }, 0, false);
        };

        $rootScope.$on('$stateChangeSuccess', listener);
      }
    };
  }
]);


app.filter('unsafe', ['$sce', function ($sce) {
    return function (val) {
        return $sce.trustAsHtml(val);
    };
}]);

app.service('ConfirmService', function($uibModal) {
	var service = {};
	service.open = function (obj, onOk) {

		var modalInstance = $uibModal.open({
			templateUrl: 'myModalContent.html',
			controller: 'ModalConfirmCtrl',
			windowClass: 'center-modal',
			resolve: {
				obj: function () {
					return obj;
				}
			}
		});

		modalInstance.result.then(function (selectedItem) {
			if (onOk) {
				onOk();
			};
		}, function () {
		});
	};
	return service;
});

app.controller('ModalConfirmCtrl', function ($scope, $uibModalInstance, obj, $filter, $state) {
	$scope.message = obj.message;
	$scope.cancelText = obj.cancelText != null ? obj.cancelText : 'CANCEL';
	$scope.okText =  obj.okText != null ? obj.okText : 'OK';
	$scope.showCancel = obj.showCancel;

	$scope.ok = function () {
		$uibModalInstance.close(true);
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
});
