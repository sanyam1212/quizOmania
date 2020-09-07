app.factory('AnswerService', function($resource) {
	return $resource('/service/quiz/answer', {
		id : "@id",
		limit : "@limit",
		offset : "@offset",
		sort : "@sort"
	}, {
		list : {
			url : '/service/quiz/answer/list?limit=:limit&offset=:offset',
			method : 'GET',
			params : {},
			isArray : false
		},
		saveAnswer : {
			url : '/service/quiz/answer',
			method : 'POST',
			params : {},
			isArray : false
		},
		findById : {
			url : '/service/quiz/answer/:id',
			method : 'GET',
			params : {},
			isArray : false
		}
	});
});