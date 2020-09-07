app.factory('QuizService', function($resource) {
	return $resource('/service/quiz', {
		id : "@id",
		limit : "@limit",
		offset : "@offset",
		sort : "@sort"
	}, {
		list : {
			url : '/service/quiz/list?limit=:limit&offset=:offset',
			method : 'GET',
			params : {},
			isArray : false
		}, 
		addQuiz : {
			url : '/service/quiz',
			method : 'POST',
			params : {},
			isArray : false
		},
		findById : {
			url : '/service/quiz/:id',
			method : 'GET',
			params : {},
			isArray : false
		}
	});
});