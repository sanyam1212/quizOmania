package io.roost.quiz.filter;

import org.springframework.boot.common.filter.SearchFilter;

public class QuestionFilter extends SearchFilter {
	private String quizId;

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

}
