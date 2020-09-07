package io.roost.quiz.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.common.dto.AbstractAuditDTO;

public class AnswerDTO extends AbstractAuditDTO {
	private String email;
	private String quizId;
	private Map<String, List<String>> answers;
	private String score;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public Map<String, List<String>> getAnswers() {
		return answers;
	}

	public void setAnswers(Map<String, List<String>> answers) {
		this.answers = answers;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
