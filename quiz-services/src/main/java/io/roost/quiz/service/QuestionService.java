package io.roost.quiz.service;

import java.util.List;

import org.springframework.boot.common.exception.CommonException;
import org.springframework.boot.common.request.tracker.AbstractRequestTracker;
import org.springframework.boot.common.response.dto.PagedList;
import org.springframework.boot.common.response.dto.Paging;
import org.springframework.boot.common.sort.dto.SortRules;

import io.roost.quiz.dto.QuestionDTO;
import io.roost.quiz.filter.QuestionFilter;

public interface QuestionService {

	PagedList<QuestionDTO> bulkSave(List<QuestionDTO> dtos) throws CommonException;

	PagedList<QuestionDTO> list(AbstractRequestTracker requestTracker, QuestionFilter filter, Paging paging,
			SortRules sortRules, boolean isSendAnswers) throws CommonException;

}
