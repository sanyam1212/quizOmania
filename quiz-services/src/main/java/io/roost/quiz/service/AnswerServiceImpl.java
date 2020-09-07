package io.roost.quiz.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.common.exception.CommonException;
import org.springframework.boot.common.request.tracker.AbstractRequestTracker;
import org.springframework.boot.common.response.dto.PagedList;
import org.springframework.boot.common.response.dto.Pagination;
import org.springframework.boot.common.response.dto.Paging;
import org.springframework.boot.common.sort.dto.SortRules;
import org.springframework.boot.common.utils.CommonUtils;
import org.springframework.boot.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.roost.quiz.dao.AnswerDAO;
import io.roost.quiz.dto.AnswerDTO;
import io.roost.quiz.dto.QuestionDTO;
import io.roost.quiz.filter.AnswerFilter;
import io.roost.quiz.filter.QuestionFilter;
import io.roost.quiz.model.Answer;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

	AnswerDAO answerDAO;
	QuestionService questionService;

	@Autowired
	public AnswerServiceImpl(AnswerDAO answerDAO, QuestionService questionService) {
		this.answerDAO = answerDAO;
		this.questionService = questionService;
	}

	@Override
	public AnswerDTO save(AbstractRequestTracker requestTracker, AnswerDTO dto) throws CommonException {
		dto.setId(CommonUtils.getUUID());
		dto.setCreatedOn(new Date().getTime());
		dto.setModifiedOn(dto.getCreatedOn());
		dto.setScore(calculateScore(dto));
		return getDTOForEntity(answerDAO.save(getEntityForDTO(dto)));
	}

	private String calculateScore(AnswerDTO dto) throws CommonException {
		QuestionFilter filter = new QuestionFilter();
		filter.setQuizId(dto.getQuizId());
		List<QuestionDTO> questions = questionService.list(null, filter, new Paging(-1, -1), null, true).getObjects();
		Map<String, QuestionDTO> map = new HashMap<>();
		for (QuestionDTO questionDTO : questions) {
			map.put(questionDTO.getId(), questionDTO);
		}
		int correctAnswers = 0;
		for (Map.Entry<String, List<String>> entry : dto.getAnswers().entrySet()) {
			if (!entry.getValue().isEmpty()
					&& entry.getValue().get(0).equalsIgnoreCase(map.get(entry.getKey()).getAnswers().get(0))) {
				correctAnswers++;
			}
		}
		return correctAnswers + "/" + questions.size();
	}

	@Override
	public PagedList<AnswerDTO> list(AbstractRequestTracker requestTracker, AnswerFilter filter, Paging paging,
			SortRules sortRules) throws CommonException {
		PagedList<AnswerDTO> pagedList = new PagedList<>();
		Pagination pagination = new Pagination();
		pagination.setLimit(paging.getLimit());
		pagination.setOffset(paging.getOffset());
		pagination.setTotal(getTotal(filter));
		pagedList.setPagination(pagination);
		return getDTOsForEntities(getAnswers(filter, paging, sortRules), pagedList);
	}

	private PagedList<AnswerDTO> getDTOsForEntities(List<Answer> models, PagedList<AnswerDTO> pagedList) {
		List<AnswerDTO> dtos = models.parallelStream().map(new Function<Answer, AnswerDTO>() {
			@Override
			public AnswerDTO apply(Answer model) {
				return getDTOForEntity(model);
			}
		}).collect(Collectors.toList());

		pagedList.setObjects(dtos);
		pagedList.getPagination().setCount(dtos.size());
		return pagedList;
	}

	private AnswerDTO getDTOForEntity(Answer model) {
		AnswerDTO dto = new AnswerDTO();
		dto.setId(model.getId());
		dto.setCreatedOn(model.getCreatedOn());
		dto.setModifiedOn(model.getModifiedOn());
		dto.setQuizId(model.getQuizId());
		dto.setEmail(model.getEmail());
		try {
			ObjectMapper mapper = new ObjectMapper();
			dto.setAnswers(mapper.readValue(model.getAnswers(), new TypeReference<Map<String, List<String>>>() {
			}));
		} catch (Exception e) {
			e.printStackTrace();
		}

		dto.setScore(model.getScore());
		return dto;
	}

	private Answer getEntityForDTO(AnswerDTO dto) {
		Answer model = new Answer();
		model.setId(dto.getId());
		model.setCreatedOn(dto.getCreatedOn());
		model.setModifiedOn(dto.getModifiedOn());
		model.setQuizId(dto.getQuizId());
		model.setEmail(dto.getEmail());
		model.setAnswers(JsonUtils.getJsonString(dto.getAnswers()));
		model.setScore(dto.getScore());
		return model;
	}

	private Integer getTotal(AnswerFilter filter) {
		if (StringUtils.isNotBlank(filter.getQuizId())) {
			return answerDAO.countByQuizId(filter.getQuizId()).intValue();
		} else {
			return answerDAO.countByEmail(filter.getEmail()).intValue();
		}
	}

	private List<Answer> getAnswers(AnswerFilter filter, Paging paging, SortRules sortRules) {
		if (StringUtils.isNotBlank(filter.getQuizId())) {
			return answerDAO.findByQuizId(filter.getQuizId());
		} else {
			return answerDAO.findByEmail(filter.getEmail());
		}
	}

	@Override
	public AnswerDTO findById(String id) throws CommonException {
		return getDTOForEntity(answerDAO.findById(id).get());
	}

}
