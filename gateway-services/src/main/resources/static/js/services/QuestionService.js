app.factory('QuestionService', function($resource) {
	return $resource('/service/quiz/question', {
		id : "@id",
		limit : "@limit",
		offset : "@offset",
		sort : "@sort"
	}, {
		list : {
			url : '/service/quiz/question/list?limit=:limit&offset=:offset',
			method : 'GET',
			params : {},
			isArray : false
		}, 
		addQuestions : {
			url : '/service/quiz/question/bulk',
			method : 'POST',
			params : {},
			isArray : false
		}
	});
});