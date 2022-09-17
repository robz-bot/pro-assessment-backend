package com.promantus.Assessment.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.GeneralQuestionDto;
import com.promantus.Assessment.Entity.GeneralQuestion;
import com.promantus.Assessment.Repository.GeneralQuestionRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.GeneralQuestionService;

@Service
public class GeneralQuestionServiceImpl implements GeneralQuestionService {

	@Autowired
	GeneralQuestionRepository generalQuestionRepository;

	@Autowired
	CommonService commonService;

	@Autowired
	MongoOperations mongoOperation;

	@Override
	public GeneralQuestionDto addGeneralQuestion(final GeneralQuestionDto generalQuestionDto, String lang)
			throws Exception {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();

		if (generalQuestionRepository.existsByQuestion(generalQuestionDto.getQuestion())) {
			resultDto.setMessage("This Question already exists");
			resultDto.setStatus(1);
			return resultDto;
		}

		if (generalQuestionRepository.findAll().size() > 0) {
			GeneralQuestion generalQuestion = new GeneralQuestion();
			generalQuestion.setId(commonService.nextSequenceNumber());
			generalQuestion.setQuestion(generalQuestionDto.getQuestion());
			generalQuestion.setOption1(generalQuestionDto.getOption1());
			generalQuestion.setOption2(generalQuestionDto.getOption2());
			generalQuestion.setOption3(generalQuestionDto.getOption3());
			generalQuestion.setOption4(generalQuestionDto.getOption4());
			generalQuestion.setAnswer(generalQuestionDto.getAnswer());
			generalQuestion.setUpdatedon(LocalDateTime.now());
			generalQuestion.setisActive(true);
			generalQuestionRepository.save(generalQuestion);
		}
		resultDto.setMessage("General Question added successfully");
		return resultDto;
	}

	@Override
	public List<GeneralQuestionDto> getAllGeneralQuestions() throws Exception {
		List<GeneralQuestion> GeneralQuestionsList = generalQuestionRepository.findAllByIsActive(true,AssessmentUtil.orderByUpdatedOnDesc());

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
		generalQuestionDto.setisActive(generalQuestion.getisActive());
		generalQuestionDto.setUpdatedon(generalQuestion.getUpdatedon());
		return generalQuestionDto;

	}

	@Override
	public GeneralQuestionDto updateGeneralQuestion(GeneralQuestionDto generalQuestionDto, String lang)
			throws Exception {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		System.out.println(generalQuestionDto.getId());
		GeneralQuestion generalQuestion = generalQuestionRepository.findById(generalQuestionDto.getId());

		if (generalQuestion == null) {

			resultDto.setMessage("General Question does not exist");
			return resultDto;
		}
		generalQuestion.setQuestion(generalQuestionDto.getQuestion());
		generalQuestion.setOption1(generalQuestionDto.getOption1());
		generalQuestion.setOption2(generalQuestionDto.getOption2());
		generalQuestion.setOption3(generalQuestionDto.getOption3());
		generalQuestion.setOption4(generalQuestionDto.getOption4());
		generalQuestion.setAnswer(generalQuestionDto.getAnswer());
		generalQuestion.setisActive(generalQuestionDto.getisActive());
		generalQuestion.setUpdatedBy(generalQuestionDto.getUpdatedBy());
		generalQuestion.setUpdatedon(LocalDateTime.now());
		generalQuestion.setisActive(true);
		generalQuestionRepository.save(generalQuestion);
		resultDto.setMessage("Record Updated successfully");
		return resultDto;

	}

	@Override
	public GeneralQuestionDto deleteGeneralQuestionById(String id) throws Exception {
		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		GeneralQuestion generalQuestion = generalQuestionRepository.findByIdAndIsActive(Long.parseLong(id),true);
		if (generalQuestion == null) {
			resultDto.setMessage("data does not exist");
			return resultDto;
		}
		
		generalQuestion.setisActive(false);
		generalQuestionRepository.save(generalQuestion);
		resultDto.setMessage("Record Deleted successfully");
		return resultDto;
	}

	@Override
	public GeneralQuestionDto getGeneralQuestionById(String id) throws Exception {

		GeneralQuestion generalQuestion = generalQuestionRepository.findById(Long.parseLong(id));

		return generalQuestion != null ? this.getGeneralQuestionDto(generalQuestion) : new GeneralQuestionDto();

	}

	@Override
	public List<GeneralQuestionDto> search(String type, String keyword) throws Exception {
		List<GeneralQuestionDto> resultDto = new ArrayList<>();
		List<GeneralQuestion> generalQuestion = new ArrayList<>();

		if (type.equals(AssessmentConstants.TYPE1)) {
			generalQuestion = generalQuestionRepository.findByQuestionAndIsActiveRegex(keyword,true);
		}
		if (type.equals(AssessmentConstants.TYPE2)) {
			List<GeneralQuestion> option1List = generalQuestionRepository.findByOption1AndIsActiveRegex(keyword,true);
			List<GeneralQuestion> option2List = generalQuestionRepository.findByOption2AndIsActiveRegex(keyword,true);
			List<GeneralQuestion> option3List = generalQuestionRepository.findByOption3AndIsActiveRegex(keyword,true);
			List<GeneralQuestion> option4List = generalQuestionRepository.findByOption4AndIsActiveRegex(keyword,true);

			for (GeneralQuestion generalQuestion2 : option1List) {
				generalQuestion.add(generalQuestion2);
			}

			for (GeneralQuestion generalQuestion3 : option2List) {
				generalQuestion.add(generalQuestion3);
			}

			for (GeneralQuestion generalQuestion4 : option3List) {
				generalQuestion.add(generalQuestion4);
			}

			for (GeneralQuestion generalQuestion5 : option4List) {
				generalQuestion.add(generalQuestion5);
			}
		}
		if (type.equals(AssessmentConstants.TYPE3)) {
			generalQuestion = generalQuestionRepository.findByAnswerAndIsActiveRegex(keyword,true);
		}
		for (GeneralQuestion generalQuestion2 : generalQuestion) {
			resultDto.add(getGeneralQuestionDto(generalQuestion2));
		}

		return resultDto;
	}

	@Override
	@Cacheable(value = "cacheGenQnList")
	public Map<String, Object> getAllGeneralQuestionsPage(Pageable paging) throws Exception {
		paging.getSort();
		Page<GeneralQuestion> genQnPage = generalQuestionRepository.findAllByIsActive(true,paging);
		List<GeneralQuestionDto> resultDto = new ArrayList<>(); 
		List<GeneralQuestion> GeneralQuestionsList = genQnPage.getContent();
		for (GeneralQuestion GeneralQuestion : GeneralQuestionsList) {
			resultDto.add(this.getGeneralQuestionDto(GeneralQuestion));
		}
		
		Map<String, Object> response = new HashMap<>();
	      response.put("generalQns", GeneralQuestionsList);
	      response.put("currentPage", genQnPage.getNumber());
	      response.put("totalItems", genQnPage.getTotalElements());
	      response.put("totalPages", genQnPage.getTotalPages());
	      response.put("totalRecords", genQnPage.getTotalPages());
		return response;
	}

	@Override
	public List<GeneralQuestionDto> activateAllGenQns() throws Exception {
		
		List<GeneralQuestion> GeneralQuestionsList = generalQuestionRepository.findAllByIsActive(false,AssessmentUtil.orderByUpdatedOnDesc());

		List<GeneralQuestionDto> GeneralQuestionDtoList = new ArrayList<GeneralQuestionDto>();
		for (GeneralQuestion GeneralQuestion : GeneralQuestionsList) {
			GeneralQuestion.setisActive(true);
			generalQuestionRepository.save(GeneralQuestion);
			GeneralQuestionDtoList.add(this.getGeneralQuestionDto(GeneralQuestion));
		}

		return GeneralQuestionDtoList;
	}

}
