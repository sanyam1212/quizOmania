package io.roost.quiz.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

import io.roost.quiz.dao.QuestionDAO;
import io.roost.quiz.dto.QuestionDTO;
import io.roost.quiz.filter.QuestionFilter;
import io.roost.quiz.model.Question;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	private QuestionDAO questionDAO;

	@Autowired
	public QuestionServiceImpl(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	@Override
	public PagedList<QuestionDTO> bulkSave(List<QuestionDTO> dtos) throws CommonException {
		PagedList<QuestionDTO> pagedList = new PagedList<>();
		Pagination pagination = new Pagination();
		pagedList.setPagination(pagination);
		List<Question> questions = dtos.parallelStream().map(new Function<QuestionDTO, Question>() {
			@Override
			public Question apply(QuestionDTO dto) {
				dto.setId(CommonUtils.getUUID());
				dto.setCreatedOn(new Date().getTime());
				dto.setModifiedOn(dto.getCreatedOn());
				return getEntityForDTO(dto);
			}
		}).collect(Collectors.toList());

		return getDTOsForEntities(questionDAO.saveAll(questions), pagedList, false);
	}

	@Override
	public PagedList<QuestionDTO> list(AbstractRequestTracker requestTracker, QuestionFilter filter, Paging paging,
			SortRules sortRules, boolean isSendAnswers) throws CommonException {
		PagedList<QuestionDTO> pagedList = new PagedList<>();
		Pagination pagination = new Pagination();
		pagination.setLimit(paging.getLimit());
		pagination.setOffset(paging.getOffset());
		pagination.setTotal(getTotal(filter));
		pagedList.setPagination(pagination);
		return getDTOsForEntities(getQuestions(filter, paging, sortRules), pagedList, isSendAnswers);
	}

	private PagedList<QuestionDTO> getDTOsForEntities(List<Question> models, PagedList<QuestionDTO> pagedList,
			boolean isSendAnswers) {
		List<QuestionDTO> dtos = models.parallelStream().map(new Function<Question, QuestionDTO>() {
			@Override
			public QuestionDTO apply(Question model) {
				QuestionDTO questionDTO = getDTOForEntity(model);
				if (!isSendAnswers) {
					questionDTO.setAnswers(Collections.emptyList());
				}
				return questionDTO;
			}
		}).collect(Collectors.toList());

		pagedList.setObjects(dtos);
		pagedList.getPagination().setCount(dtos.size());
		return pagedList;
	}

	private QuestionDTO getDTOForEntity(Question model) {
		QuestionDTO dto = new QuestionDTO();
		dto.setId(model.getId());
		dto.setCreatedOn(model.getCreatedOn());
		dto.setModifiedOn(model.getModifiedOn());
		dto.setQuizId(model.getId());
		dto.setQuestion(model.getQuestion());
		try {
			ObjectMapper mapper = new ObjectMapper();
			dto.setAnswers(mapper.readValue(model.getAnswers(), new TypeReference<List<String>>() {
			}));
			dto.setOptions(mapper.readValue(model.getOptions(), new TypeReference<List<String>>() {
			}));
		} catch (Exception e) {
			e.printStackTrace();
		}
		dto.setShowOrder(model.getShowOrder());
		dto.setCategory(model.getCategory());
		return dto;
	}

	private Question getEntityForDTO(QuestionDTO dto) {
		Question model = new Question();
		model.setId(dto.getId());
		model.setCreatedOn(dto.getCreatedOn());
		model.setModifiedOn(dto.getModifiedOn());
		model.setQuizId(dto.getQuizId());
		model.setQuestion(dto.getQuestion());
		model.setAnswers(JsonUtils.getJsonString(dto.getAnswers()));
		model.setOptions(JsonUtils.getJsonString(dto.getOptions()));
		model.setShowOrder(dto.getShowOrder());
		model.setCategory(dto.getCategory());
		return model;
	}

	private Integer getTotal(QuestionFilter filter) {
		return questionDAO.countByQuizId(filter.getQuizId()).intValue();
	}

	private List<Question> getQuestions(QuestionFilter filter, Paging paging, SortRules sortRules) {
		return questionDAO.findByQuizIdOrderByShowOrderAsc(filter.getQuizId());
	}

}
