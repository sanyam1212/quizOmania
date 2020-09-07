package io.roost.quiz.service;

import org.springframework.boot.common.exception.CommonException;
import org.springframework.boot.common.request.tracker.AbstractRequestTracker;
import org.springframework.boot.common.response.dto.PagedList;
import org.springframework.boot.common.response.dto.Paging;
import org.springframework.boot.common.sort.dto.SortRules;

import io.roost.quiz.dto.AnswerDTO;
import io.roost.quiz.filter.AnswerFilter;

public interface AnswerService {

	AnswerDTO save(AbstractRequestTracker requestTracker, AnswerDTO dto) throws CommonException;

	PagedList<AnswerDTO> list(AbstractRequestTracker requestTracker, AnswerFilter filter, Paging paging,
			SortRules sortRules) throws CommonException;

	AnswerDTO findById(String id) throws CommonException;

}
