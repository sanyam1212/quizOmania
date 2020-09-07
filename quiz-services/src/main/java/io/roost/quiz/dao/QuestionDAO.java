package io.roost.quiz.dao;

import java.util.List;

import org.springframework.boot.common.dao.AbstractDAO;
import org.springframework.data.repository.query.Param;

import io.roost.quiz.model.Question;

public interface QuestionDAO extends AbstractDAO<Question> {

	Long countByQuizId(@Param("quizId") String quizId);

	List<Question> findByQuizIdOrderByShowOrderAsc(@Param("quizId") String quizId);

}
