package io.roost.quiz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.common.controller.AbstractController;
import org.springframework.boot.common.exception.CommonException;
import org.springframework.boot.common.request.tracker.AbstractRequestTracker;
import org.springframework.boot.common.response.dto.Paging;
import org.springframework.boot.common.sort.dto.SortRules;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.roost.quiz.dto.QuizDTO;
import io.roost.quiz.filter.QuizFilter;
import io.roost.quiz.service.QuizService;

/**
 * This controller is responsible to handle Quiz APIs.
 * 
 * @version 1.0.0
 */
@RestController
@RequestMapping("/quiz")
public class QuizController extends AbstractController<QuizDTO, QuizFilter> {

	@Autowired
	QuizService QuizService;

	public QuizController() {
		super(QuizDTO.class, QuizFilter.class);
	}

	@Override
	protected Response save(AbstractRequestTracker requestTracker, QuizDTO dto) throws CommonException {
		return onSuccess(QuizService.save(requestTracker, dto));
	}

	@Override
	protected Response update(AbstractRequestTracker requestTracker, QuizDTO dto, QuizFilter filter)
			throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

	@Override
	protected Response list(AbstractRequestTracker requestTracker, QuizFilter filter, Paging paging,
			SortRules sortRules) throws CommonException {
		return onSuccess(QuizService.list(requestTracker, filter, paging, sortRules));
	}

	@Override
	protected Response findById(AbstractRequestTracker requestTracker, String id) throws CommonException {
		return onSuccess(QuizService.findById(requestTracker, id));
	}

	@Override
	protected Response deleteById(AbstractRequestTracker requestTracker, String id) throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

	@PostMapping("/authenticateQuizzer/{id}")
	public Response authenticateQuizzer(@PathVariable("id") String id, @RequestBody String data,
			@Context HttpServletRequest request) throws CommonException, JsonMappingException, JsonProcessingException {
		return onSuccess(QuizService.authenticateQuizzer(id,
				new ObjectMapper().readValue(data.substring(1, data.length() - 1).replace("\\", ""), QuizDTO.class)));
	}

	@PostMapping("/authenticateQuizMaster/{id}")
	public Response authenticateQuizMaster(@PathVariable("id") String id, @RequestBody String data,
			@Context HttpServletRequest request) throws CommonException, JsonMappingException, JsonProcessingException {
		return onSuccess(QuizService.authenticateQuizMaster(id,
				new ObjectMapper().readValue(data.substring(1, data.length() - 1).replace("\\", ""), QuizDTO.class)));
	}

}
