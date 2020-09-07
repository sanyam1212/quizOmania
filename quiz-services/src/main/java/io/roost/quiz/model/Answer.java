package io.roost.quiz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.common.model.AbstractAuditModel;

@Entity
@Table(name = "answer")
public class Answer extends AbstractAuditModel {
	private static final long serialVersionUID = -4314527892947634271L;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	private String email;
	private String quizId;
	private String answers;
	private String score;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "quiz_id")
	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	@Column(name = "answers", columnDefinition = "NVARCHAR(MAX)")
	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}
	
	@Column(name = "score", columnDefinition = "VARCHAR(10)")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
