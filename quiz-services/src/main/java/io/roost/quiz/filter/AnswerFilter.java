package io.roost.quiz.filter;

import org.springframework.boot.common.filter.SearchFilter;

public class AnswerFilter extends SearchFilter {
	private String quizId;
	private String email;

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
