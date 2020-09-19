package io.roost.quiz.service;

import org.springframework.boot.common.exception.CommonException;

public interface EmailSenderService {
	/**
	 * This method is used to send quiz creation success email.
	 * 
	 * @param sendTo
	 * @param quizName
	 * @param password
	 * @param quizzerPassword
	 * @param URL
	 * @throws CommonException
	 */
	void sendQuizCreationEmail(String sendTo, String quizName, String password, String quizzerPassword, String URL)
			throws CommonException;

	/**
	 * This method is used to send quiz attempt success email.
	 * 
	 * @param sendTo
	 * @param quizName
	 * @param score
	 * @param URL
	 * @throws CommonException
	 */
	void sendQuizAttemptEmail(String sendTo, String quizName, String score, String URL) throws CommonException;

}
