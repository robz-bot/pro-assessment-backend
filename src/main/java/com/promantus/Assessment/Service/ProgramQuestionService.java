package com.promantus.Assessment.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.promantus.Assessment.Dto.ProgramQuestionDto;
import com.promantus.Assessment.Entity.ProgramQuestion;
import com.promantus.Assessment.Entity.TechQuestion;

public interface ProgramQuestionService {

	ProgramQuestionDto addProgramQuestion(ProgramQuestionDto programQuestionDto, String lang) throws Exception;

	List<ProgramQuestionDto> getAllProgramQuestions() throws Exception;

	ProgramQuestionDto updateProgramQuestion(ProgramQuestionDto programQuestionDto, String lang) throws Exception;

	ProgramQuestionDto deleteProgramQuestionById(String id) throws Exception;

	ProgramQuestionDto getProgramQuestionById(String id) throws Exception;

	List<ProgramQuestionDto> search(String type, String keyword) throws Exception;

	Map<String, Object> getAllProgramQuestionsPage(Pageable paging) throws Exception;

	ProgramQuestionDto inactiveProgramQuestionById(String id) throws Exception;

	Map<String, Object> getInactiveQns(String type, String keyword) throws Exception;

	Map<String, Object> activeQuestionById(String type, String id) throws Exception;

	Map<String, Object> saveBulkProgramQuestions(List<ProgramQuestion> programQuestion) throws Exception;


}
