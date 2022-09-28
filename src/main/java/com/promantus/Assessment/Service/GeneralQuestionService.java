package com.promantus.Assessment.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.promantus.Assessment.Dto.GeneralQuestionDto;
import com.promantus.Assessment.Entity.GeneralQuestion;

public interface GeneralQuestionService {

	GeneralQuestionDto addGeneralQuestion(GeneralQuestionDto generalQuestionDto, String lang) throws Exception;

	List<GeneralQuestionDto> getAllGeneralQuestions() throws Exception;

	GeneralQuestionDto updateGeneralQuestion(GeneralQuestionDto generalQuestionDto, String lang) throws Exception;

	GeneralQuestionDto deleteGeneralQuestionById(String id) throws Exception;

	GeneralQuestionDto getGeneralQuestionById(String id) throws Exception;

	List<GeneralQuestionDto> search(String type, String keyword) throws Exception;

	Map<String, Object> getAllGeneralQuestionsPage(Pageable paging) throws Exception;

	List<GeneralQuestionDto> activateAllGenQns() throws Exception;

	Map<String, Object> searchGenQnPage(Pageable paging, String type, String keyword) throws Exception;

	GeneralQuestionDto inactiveGeneralQuestionById(String id) throws Exception;

	Map<String, Object> getInactiveQns(String type, String keyword) throws Exception;

	Map<String, Object> activeQuestionById(String type, String id) throws Exception;

	Map<String, Object> saveBulkGeneralQuestions(List<GeneralQuestion> generalQuestion)throws Exception;

}
