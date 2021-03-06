package io.roost.quiz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.common.exception.CommonException;
import org.springframework.boot.common.filter.SearchFilter;
import org.springframework.boot.common.request.tracker.AbstractRequestTracker;
import org.springframework.boot.common.response.dto.PagedList;
import org.springframework.boot.common.response.dto.Pagination;
import org.springframework.boot.common.response.dto.Paging;
import org.springframework.boot.common.sort.dto.SortRules;
import org.springframework.boot.common.utils.CommonUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.roost.quiz.dao.QuizDAO;
import io.roost.quiz.dto.QuizDTO;
import io.roost.quiz.filter.QuizFilter;
import io.roost.quiz.model.Quiz;

/**
 * This class is responsible for implementing business logic on data.
 * 
 * @version 1.0.0
 */
@Service
@Transactional
public class QuizServiceImpl implements QuizService {
	private QuizDAO quizDAO;

	private EmailSenderService emailSenderService;

	@Autowired
	public QuizServiceImpl(QuizDAO quizDAO) {
		this.quizDAO = quizDAO;
		this.emailSenderService = new EmailSenderServiceImpl();
	}

	@Override
	public QuizDTO save(AbstractRequestTracker requestTracker, QuizDTO dto) throws CommonException {
		dto.setId(CommonUtils.getUUID());
		dto.setCreatedOn(new Date().getTime());
		dto.setModifiedOn(dto.getCreatedOn());
		Quiz quiz = quizDAO.save(getEntityForDTO(dto));
		try {
			if (quiz != null) {
				emailSenderService.sendQuizCreationEmail(dto.getEmail(), dto.getName(), dto.getQuizPassword(),
						dto.getQuizzerPassword(),
						new StringBuilder("http://roost-controlplane:30057/#/quiz/quizzers/").append(dto.getId()).toString());
			}
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return getDTOForEntity(quiz);
	}

	@Override
	public QuizDTO update(AbstractRequestTracker requestTracker, QuizDTO dto, SearchFilter filter)
			throws CommonException {
		return null;
	}

	@Override
	public PagedList<QuizDTO> list(AbstractRequestTracker requestTracker, QuizFilter filter, Paging paging,
			SortRules sortRules) throws CommonException {
		PagedList<QuizDTO> pagedList = new PagedList<>();
		Pagination pagination = new Pagination();
		pagination.setLimit(paging.getLimit());
		pagination.setOffset(paging.getOffset());
		pagination.setTotal(getTotal(filter));
		pagedList.setPagination(pagination);
		return getDTOsForEntities(getQuizes(filter, paging, sortRules), pagedList);
	}

	@Override
	public QuizDTO findById(AbstractRequestTracker requestTracker, String id) throws CommonException {
		return getDTOForEntity(quizDAO.findById(id).get());
	}

	@Override
	public Integer deleteById(AbstractRequestTracker requestTracker, String id) throws CommonException {
		return null;
	}

	private PagedList<QuizDTO> getDTOsForEntities(List<Quiz> models, PagedList<QuizDTO> pagedList) {
		List<QuizDTO> dtos = new ArrayList<>();
		for (Quiz model : models) {
			dtos.add(getDTOForEntity(model));
		}
		pagedList.setObjects(dtos);
		pagedList.getPagination().setCount(dtos.size());
		return pagedList;
	}

	private QuizDTO getDTOForEntity(Quiz model) {
		QuizDTO dto = new QuizDTO();
		dto.setId(model.getId());
		dto.setCreatedOn(model.getCreatedOn());
		dto.setModifiedOn(model.getModifiedOn());
		dto.setName(model.getName());
		dto.setEmail(model.getEmail());
		dto.setCategory(model.getCategory());
		return dto;
	}

	private static String encrypt(String value) {
		return BCrypt.hashpw(value, BCrypt.gensalt());
	}

	private static boolean validate(String plaintext, String hashed) {
		return BCrypt.checkpw(plaintext, hashed);
	}

	private Quiz getEntityForDTO(QuizDTO dto) {
		Quiz model = new Quiz();
		model.setId(dto.getId());
		model.setCreatedOn(dto.getCreatedOn());
		model.setModifiedOn(dto.getModifiedOn());
		model.setName(dto.getName());
		model.setEmail(dto.getEmail());
		model.setCategory(dto.getCategory());
		model.setQuizPassword(encrypt(dto.getQuizPassword()));
		model.setQuizzerPassword(encrypt(dto.getQuizzerPassword()));
		return model;
	}

	private List<Quiz> getQuizes(QuizFilter filter, Paging paging, SortRules sortRules) {
		if (StringUtils.isNotBlank(filter.getCategory())) {
			if (StringUtils.isNotBlank(filter.getName())) {
				return quizDAO.findByNameAndCategory(filter.getName(), filter.getCategory());
			} else if (StringUtils.isNotBlank(filter.getEmail())) {
				return quizDAO.findByEmailAndCategory(filter.getEmail(), filter.getCategory());
			}
		} else {
			if (StringUtils.isNotBlank(filter.getName())) {
				return quizDAO.findByName(filter.getName());
			} else if (StringUtils.isNotBlank(filter.getEmail())) {
				return quizDAO.findByEmail(filter.getEmail());
			}
		}
		return quizDAO.findAll();
	}

	private Integer getTotal(QuizFilter filter) {
		if (StringUtils.isNotBlank(filter.getCategory())) {
			if (StringUtils.isNotBlank(filter.getName())) {
				return quizDAO.countByNameAndCategory(filter.getName(), filter.getCategory()).intValue();
			} else if (StringUtils.isNotBlank(filter.getEmail())) {

				return quizDAO.countByEmailAndCategory(filter.getEmail(), filter.getCategory()).intValue();
			}
		} else {
			if (StringUtils.isNotBlank(filter.getName())) {
				return quizDAO.countByName(filter.getName()).intValue();
			} else if (StringUtils.isNotBlank(filter.getEmail())) {
				return quizDAO.countByEmail(filter.getEmail()).intValue();
			}
		}
		return (int) quizDAO.count();
	}

	@Override
	public boolean authenticateQuizzer(String id, QuizDTO dto) throws CommonException {
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(dto.getQuizzerPassword())) {
			return validate(dto.getQuizzerPassword(), quizDAO.findById(id).get().getQuizzerPassword());
		}
		return false;
	}

	@Override
	public boolean authenticateQuizMaster(String id, QuizDTO dto) throws CommonException {
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(dto.getQuizPassword())) {
			return validate(dto.getQuizPassword(), quizDAO.findById(id).get().getQuizPassword());
		}
		return false;
	}

}
