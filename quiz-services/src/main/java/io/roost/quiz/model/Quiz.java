package io.roost.quiz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.common.model.AbstractAuditModel;

@Entity
@Table(name = "quiz")
public class Quiz extends AbstractAuditModel {
	private static final long serialVersionUID = 8732517952260640149L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	private String name;
	private String email;
	private String category;
	private String quizPassword;
	private String quizzerPassword;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", columnDefinition = "NVARCHAR(MAX)")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "quiz_password")
	public String getQuizPassword() {
		return quizPassword;
	}

	public void setQuizPassword(String quizPassword) {
		this.quizPassword = quizPassword;
	}

	@Column(name = "quizzer_password")
	public String getQuizzerPassword() {
		return quizzerPassword;
	}

	public void setQuizzerPassword(String quizzerPassword) {
		this.quizzerPassword = quizzerPassword;
	}
	
}
