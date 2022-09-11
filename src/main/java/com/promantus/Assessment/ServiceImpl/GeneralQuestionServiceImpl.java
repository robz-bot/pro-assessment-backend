package com.promantus.Assessment.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.Dto.GeneralQuestionDto;
import com.promantus.Assessment.Entity.GeneralQuestion;
import com.promantus.Assessment.Repository.GeneralQuestionRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.GeneralQuestionService;

@Service
public class GeneralQuestionServiceImpl implements GeneralQuestionService {

	private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

	@Autowired
	GeneralQuestionRepository generalQuestionRepository;

	@Autowired
	CommonService commonService;

	@Override
	public GeneralQuestionDto addGeneralQuestion(final GeneralQuestionDto generalQuestionDto, String lang)
			throws Exception {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		if (generalQuestionRepository.findAll().size() > 0) {
			GeneralQuestion generalQuestion = new GeneralQuestion();
			generalQuestion.setId(commonService.nextSequenceNumber());
			generalQuestion.setQuestion(generalQuestionDto.getQuestion());
			generalQuestion.setOption1(generalQuestionDto.getOption1());
			generalQuestion.setOption2(generalQuestionDto.getOption2());
			generalQuestion.setOption3(generalQuestionDto.getOption3());
			generalQuestion.setOption4(generalQuestionDto.getOption4());
			generalQuestion.setAnswer(generalQuestionDto.getAnswer());
			generalQuestionRepository.save(generalQuestion);
		}
		resultDto.setMessage("General Question added successfully");
		return resultDto;
	}

	@Override
	public List<GeneralQuestionDto> getAllGeneralQuestions() throws Exception {
		List<GeneralQuestion> GeneralQuestionsList = generalQuestionRepository.findAll();

		List<GeneralQuestionDto> GeneralQuestionDtoList = new ArrayList<GeneralQuestionDto>();
		for (GeneralQuestion GeneralQuestion : GeneralQuestionsList) {
			GeneralQuestionDtoList.add(this.getGeneralQuestionDto(GeneralQuestion));
		}

		return GeneralQuestionDtoList;
	}

	private GeneralQuestionDto getGeneralQuestionDto(GeneralQuestion generalQuestion) {
		GeneralQuestionDto generalQuestionDto = new GeneralQuestionDto();

		generalQuestionDto.setId(generalQuestion.getId());
		generalQuestionDto.setQuestion(generalQuestion.getQuestion());
		generalQuestionDto.setOption1(generalQuestion.getOption1());
		generalQuestionDto.setOption2(generalQuestion.getOption2());
		generalQuestionDto.setOption3(generalQuestion.getOption3());
		generalQuestionDto.setOption4(generalQuestion.getOption4());
		generalQuestionDto.setAnswer(generalQuestion.getAnswer());
		return generalQuestionDto;

	}

	@Override
	public GeneralQuestionDto updateGeneralQuestion(GeneralQuestionDto generalQuestionDto, String lang)
			throws Exception {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		System.out.println(generalQuestionDto.getId());
		GeneralQuestion generalQuestion = generalQuestionRepository.findById(generalQuestionDto.getId());

		if (generalQuestion == null) {

			resultDto.setMessage("GeneralQuestion does not exist");
			return resultDto;
		}
		generalQuestion.setQuestion(generalQuestionDto.getQuestion());
		generalQuestion.setOption1(generalQuestionDto.getOption1());
		generalQuestion.setOption2(generalQuestionDto.getOption2());
		generalQuestion.setOption3(generalQuestionDto.getOption3());
		generalQuestion.setOption4(generalQuestionDto.getOption4());
		generalQuestion.setAnswer(generalQuestionDto.getAnswer());

		generalQuestionRepository.save(generalQuestion);
		resultDto.setMessage("Record Updated successfully");
		return resultDto;

	}

	@Override
	public GeneralQuestionDto deleteGeneralQuestionById(String id) throws Exception {
		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		GeneralQuestion generalQuestion = generalQuestionRepository.findById(Long.parseLong(id));
		if (generalQuestion == null) {

			resultDto.setMessage("data does not exist");
			return resultDto;
		}

		generalQuestionRepository.delete(generalQuestion);
		resultDto.setMessage("Record Deleted successfully");
		return resultDto;
	}

	@Override
	public GeneralQuestionDto getGeneralQuestionById(String id) throws Exception {

		GeneralQuestion generalQuestion = generalQuestionRepository.findById(Long.parseLong(id));

		return generalQuestion != null ? this.getGeneralQuestionDto(generalQuestion) : new GeneralQuestionDto();

	}

}
