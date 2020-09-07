package io.roost.quiz.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.common.controller.AbstractBulkController;
import org.springframework.boot.common.exception.CommonException;
import org.springframework.boot.common.request.tracker.AbstractRequestTracker;
import org.springframework.boot.common.response.dto.Paging;
import org.springframework.boot.common.sort.dto.SortRules;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.roost.quiz.dto.QuestionDTO;
import io.roost.quiz.filter.QuestionFilter;
import io.roost.quiz.service.QuestionService;

/**
 * This controller is responsible to handle Question APIs.
 * 
 * @version 1.0.0
 */
@RestController
@RequestMapping("/quiz/question")
public class QuestionController extends AbstractBulkController<QuestionDTO, QuestionFilter> {

	@Autowired
	QuestionService questionService;

	public QuestionController() {
		super(QuestionDTO.class, QuestionFilter.class);
	}

	@Override
	protected Response bulkSave(AbstractRequestTracker requestTracker, List<QuestionDTO> dtos) throws CommonException {
		return onSuccess(questionService.bulkSave(dtos));
	}

	@Override
	protected Response bulkUpdate(AbstractRequestTracker requestTracker, List<QuestionDTO> dtos, QuestionFilter filter)
			throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

	@Override
	protected Response bulkDelete(AbstractRequestTracker requestTracker, List<String> ids) throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

	@Override
	protected Response save(AbstractRequestTracker requestTracker, QuestionDTO dto) throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

	@Override
	protected Response update(AbstractRequestTracker requestTracker, QuestionDTO dto, QuestionFilter filter)
			throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

	@Override
	protected Response list(AbstractRequestTracker requestTracker, QuestionFilter filter, Paging paging,
			SortRules sortRules) throws CommonException {
		return onSuccess(questionService.list(requestTracker, filter, paging, sortRules, false));
	}

	@Override
	protected Response findById(AbstractRequestTracker requestTracker, String id) throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

	@Override
	protected Response deleteById(AbstractRequestTracker requestTracker, String id) throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

}
