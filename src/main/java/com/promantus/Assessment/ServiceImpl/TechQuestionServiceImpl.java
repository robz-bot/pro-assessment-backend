package com.promantus.Assessment.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.Dto.TechQuestionDto;
import com.promantus.Assessment.Entity.TechQuestion;
import com.promantus.Assessment.Repository.TechQuestionRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.TechQuestionService;

@Service
public class TechQuestionServiceImpl implements TechQuestionService {

	private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

	@Autowired
	TechQuestionRepository techQuestionRepository;

	@Autowired
	CommonService commonService;

	@Override
	public TechQuestionDto addTechQuestion(final TechQuestionDto techQuestionDto, String lang) throws Exception {

		TechQuestionDto resultDto = new TechQuestionDto();
		if (techQuestionRepository.findById(techQuestionDto.getId()) == null) {
			TechQuestion techQuestion = new TechQuestion();
			techQuestion.setId(commonService.nextSequenceNumber());
			techQuestion.setTeamId(techQuestionDto.getTeamId());
			techQuestion.setQuestion(techQuestionDto.getQuestion());
			techQuestion.setOption1(techQuestionDto.getOption1());
			techQuestion.setOption2(techQuestionDto.getOption2());
			techQuestion.setOption3(techQuestionDto.getOption3());
			techQuestion.setOption4(techQuestionDto.getOption4());
			techQuestion.setAnswer(techQuestionDto.getAnswer());
			techQuestionRepository.save(techQuestion);
		}
		resultDto.setMessage("TechQuestion added successfully");
		return resultDto;
	}

	@Override
	public List<TechQuestionDto> getAllTechQuestions() throws Exception {
		List<TechQuestion> TechQuestionsList = techQuestionRepository.findAll();

		List<TechQuestionDto> TechQuestionDtoList = new ArrayList<TechQuestionDto>();
		for (TechQuestion TechQuestion : TechQuestionsList) {
			TechQuestionDtoList.add(this.getTechQuestionDto(TechQuestion));
		}

		return TechQuestionDtoList;
	}

	private TechQuestionDto getTechQuestionDto(TechQuestion techQuestion) {
		TechQuestionDto techQuestionDto = new TechQuestionDto();

		techQuestionDto.setId(techQuestion.getId());
		techQuestionDto.setTeamId(techQuestion.getTeamId());
		techQuestionDto.setQuestion(techQuestion.getQuestion());
		techQuestionDto.setOption1(techQuestion.getOption1());
		techQuestionDto.setOption2(techQuestion.getOption2());
		techQuestionDto.setOption3(techQuestion.getOption3());
		techQuestionDto.setOption4(techQuestion.getOption4());
		techQuestionDto.setAnswer(techQuestion.getAnswer());
		return techQuestionDto;

	}

	@Override
	public TechQuestionDto updateTechQuestion(TechQuestionDto techQuestionDto, String lang) throws Exception {

		TechQuestionDto resultDto = new TechQuestionDto();
		System.out.println(techQuestionDto.getId());
		TechQuestion techQuestion = techQuestionRepository.findById(techQuestionDto.getId());

		if (techQuestion == null) {

			resultDto.setMessage("TechQuestion does not exist");
			return resultDto;
		}

		techQuestion.setTeamId(techQuestionDto.getTeamId());
		techQuestion.setQuestion(techQuestionDto.getQuestion());
		techQuestion.setOption1(techQuestionDto.getOption1());
		techQuestion.setOption2(techQuestionDto.getOption2());
		techQuestion.setOption3(techQuestionDto.getOption3());
		techQuestion.setOption4(techQuestionDto.getOption4());
		techQuestion.setAnswer(techQuestionDto.getAnswer());

		techQuestionRepository.save(techQuestion);
		resultDto.setMessage("Record Updated successfully");
		return resultDto;

	}

	@Override
	public TechQuestionDto deleteTechQuestionById(String id) throws Exception {
		TechQuestionDto resultDto = new TechQuestionDto();
		TechQuestion techQuestion = techQuestionRepository.findById(Long.parseLong(id));
		if (techQuestion == null) {

			resultDto.setMessage("data does not exist");
			return resultDto;
		}

		techQuestionRepository.delete(techQuestion);
		resultDto.setMessage("Record Deleted successfully");
		return resultDto;
	}

	@Override
	public TechQuestionDto getTechQuestionById(String id) throws Exception {

		TechQuestion techQuestion = techQuestionRepository.findById(Long.parseLong(id));

		return techQuestion != null ? this.getTechQuestionDto(techQuestion) : new TechQuestionDto();

	}

}
