package io.roost.quiz.service;

import org.springframework.boot.common.exception.CommonException;
import org.springframework.boot.common.filter.SearchFilter;
import org.springframework.boot.common.request.tracker.AbstractRequestTracker;
import org.springframework.boot.common.response.dto.PagedList;
import org.springframework.boot.common.response.dto.Paging;
import org.springframework.boot.common.sort.dto.SortRules;

import io.roost.quiz.dto.QuizDTO;
import io.roost.quiz.filter.QuizFilter;

/**
 * This class is responsible for implementing business logic on data.
 * 
 * @version 1.0.0
 */
public interface QuizService {
	/**
	 * This method is used for saving a new user.
	 * 
	 * @param requestTracker
	 * @param dto
	 * @return the saved user information.
	 * @throws CommonException
	 */
	QuizDTO save(AbstractRequestTracker requestTracker, QuizDTO dto) throws CommonException;

	/**
	 * This method is used for updating an already existing user.
	 * 
	 * @param requestTracker
	 * @param dto
	 * @param filter
	 * @return the updated user information.
	 * @throws CommonException
	 */
	QuizDTO update(AbstractRequestTracker requestTracker, QuizDTO dto, SearchFilter filter) throws CommonException;

	/**
	 * This method is used to fetch the list of users based on the search criteria
	 * and paging.
	 * 
	 * @param requestTracker
	 * @param filter
	 * @param paging
	 * @param sortRules
	 * @return the paged list of of users found for the search criteria.
	 * @throws CommonException
	 */
	PagedList<QuizDTO> list(AbstractRequestTracker requestTracker, QuizFilter filter, Paging paging, SortRules sortRules)
			throws CommonException;

	/**
	 * This method is used to find a single user based on its id.
	 * 
	 * @param requestTracker
	 * @param id
	 * @return the user found for the corresponding id.
	 * @throws CommonException
	 */
	QuizDTO findById(AbstractRequestTracker requestTracker, String id) throws CommonException;

	/**
	 * This method is used to soft delete a single user based on its id.
	 * 
	 * @param requestTracker
	 * @param id
	 * @return the deleted user count.
	 * @throws CommonException
	 */
	Integer deleteById(AbstractRequestTracker requestTracker, String id) throws CommonException;

	/**
	 * This method is used to authenticate the quizzer.
	 * 
	 * @param id
	 * @param dto
	 * @return
	 * @throws CommonException
	 */
	boolean authenticateQuizzer(String id, QuizDTO dto) throws CommonException;
	
	/**
	 * This method is used to authenticate the quiz master.
	 * 
	 * @param id
	 * @param dto
	 * @return
	 * @throws CommonException
	 */
	boolean authenticateQuizMaster(String id, QuizDTO dto) throws CommonException;

}
