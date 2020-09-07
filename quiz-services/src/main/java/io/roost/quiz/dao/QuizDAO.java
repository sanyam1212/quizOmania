package io.roost.quiz.dao;

import java.util.List;

import org.springframework.boot.common.dao.AbstractDAO;
import org.springframework.data.repository.query.Param;

import io.roost.quiz.model.Quiz;

public interface QuizDAO extends AbstractDAO<Quiz> {
	Long countByName(@Param("name") String name);

	Long countByEmail(@Param("email") String email);

	Long countByNameAndCategory(@Param("name") String name, @Param("category") String category);

	Long countByEmailAndCategory(@Param("email") String email, @Param("category") String category);

	List<Quiz> findByName(@Param("name") String name);

	List<Quiz> findByEmail(@Param("email") String email);

	List<Quiz> findByNameAndCategory(@Param("name") String name, @Param("category") String category);

	List<Quiz> findByEmailAndCategory(@Param("email") String email, @Param("category") String category);

}
