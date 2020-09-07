package io.roost.quiz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.common.model.AbstractAuditModel;

@Entity
@Table(name = "question")
public class Question extends AbstractAuditModel {
	private static final long serialVersionUID = -648957093372036211L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	private String question;
	private String quizId;
	private Integer showOrder;
	private String category;
	private String options;
	private String answers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "question", columnDefinition = "NVARCHAR(MAX)")
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Column(name = "quiz_id")
	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	@Column(name = "show_order")
	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	@Column(name = "category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "options", columnDefinition = "NVARCHAR(MAX)")
	public String getOptions() {
		return options;
	}
	
	public void setOptions(String options) {
		this.options = options;
	}

	@Column(name = "answers", columnDefinition = "NVARCHAR(MAX)")
	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

}
