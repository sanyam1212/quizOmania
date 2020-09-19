package io.roost.quiz.dto;

import org.springframework.boot.common.object.AbstractObject;

public class QuizResponseDTO extends AbstractObject {
	private String question;
	private String answer;
	private Boolean isCorrect;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

}
