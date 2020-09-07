package io.roost.quiz.controller;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.common.controller.AbstractController;
import org.springframework.boot.common.exception.CommonException;
import org.springframework.boot.common.request.tracker.AbstractRequestTracker;
import org.springframework.boot.common.response.dto.Paging;
import org.springframework.boot.common.sort.dto.SortRules;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.roost.quiz.dto.AnswerDTO;
import io.roost.quiz.filter.AnswerFilter;
import io.roost.quiz.service.AnswerService;

@RestController
@RequestMapping("/quiz/answer")
public class AnswerController extends AbstractController<AnswerDTO, AnswerFilter> {

	@Autowired
	AnswerService answerService;

	public AnswerController() {
		super(AnswerDTO.class, AnswerFilter.class);
	}

	@Override
	protected Response save(AbstractRequestTracker requestTracker, AnswerDTO dto) throws CommonException {
		return onSuccess(answerService.save(requestTracker, dto));
	}

	@Override
	protected Response update(AbstractRequestTracker requestTracker, AnswerDTO dto, AnswerFilter filter)
			throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

	@Override
	protected Response list(AbstractRequestTracker requestTracker, AnswerFilter filter, Paging paging,
			SortRules sortRules) throws CommonException {
		return onSuccess(answerService.list(requestTracker, filter, paging, sortRules));
	}

	@Override
	protected Response findById(AbstractRequestTracker requestTracker, String id) throws CommonException {
		return onSuccess(answerService.findById(id));
	}

	@Override
	protected Response deleteById(AbstractRequestTracker requestTracker, String id) throws CommonException {
		throw new UnsupportedOperationException("NOT_SUPPORTED_AT_PRESENT");
	}

}
