package io.roost.quiz.dto;

import org.springframework.boot.common.dto.AbstractAuditDTO;

public class QuizDTO extends AbstractAuditDTO {
	private String name;
	private String email;
	private String category;
	private String quizPassword;
	private String quizzerPassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getQuizPassword() {
		return quizPassword;
	}

	public void setQuizPassword(String quizPassword) {
		this.quizPassword = quizPassword;
	}

	public String getQuizzerPassword() {
		return quizzerPassword;
	}

	public void setQuizzerPassword(String quizzerPassword) {
		this.quizzerPassword = quizzerPassword;
	}

}
