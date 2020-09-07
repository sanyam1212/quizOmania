package io.roost.quiz.dao;

import java.util.List;

import org.springframework.boot.common.dao.AbstractDAO;
import org.springframework.data.repository.query.Param;

import io.roost.quiz.model.Answer;

public interface AnswerDAO extends AbstractDAO<Answer> {

	Long countByQuizId(@Param("quizId") String quizId);

	Long countByEmail(@Param("email") String email);

	List<Answer> findByQuizId(@Param("quizId") String quizId);

	List<Answer> findByEmail(@Param("email") String email);

}
