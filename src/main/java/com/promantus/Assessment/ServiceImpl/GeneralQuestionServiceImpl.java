package com.promantus.Assessment.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentDefaultMethods;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.GeneralQuestionDto;
import com.promantus.Assessment.Dto.TechQuestionDto;
import com.promantus.Assessment.Entity.GeneralQuestion;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.TechQuestion;
import com.promantus.Assessment.Repository.GeneralQuestionRepository;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Repository.TechQuestionRepository;
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

	@Autowired
	TechQuestionRepository techRepo;

	@Autowired
	TeamRepository teamRepo;

	@Override
	public GeneralQuestionDto addGeneralQuestion(final GeneralQuestionDto generalQuestionDto, String lang)
			throws Exception {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();

		if (generalQuestionRepository.existsByQuestion(generalQuestionDto.getQuestion())) {
			resultDto.setMessage("This Question already exists");
			resultDto.setStatus(1);
			return resultDto;
		}

//		if (generalQuestionRepository.findAll().size() > 0) {
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
//		}
		resultDto.setMessage("General Question added successfully");
		return resultDto;
	}

	@Override
	public List<GeneralQuestionDto> getAllGeneralQuestions() throws Exception {
		List<GeneralQuestion> GeneralQuestionsList = generalQuestionRepository.findAllByIsActive(true,
				AssessmentUtil.orderByUpdatedOnDesc());

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
		GeneralQuestion generalQuestion = generalQuestionRepository.findByIdAndIsActive(Long.parseLong(id), true);
		if (generalQuestion == null) {
			resultDto.setMessage("Data does not exist");
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

	@Override
	public List<GeneralQuestionDto> search(String type, String keyword) throws Exception {
		List<GeneralQuestionDto> resultDto = new ArrayList<>();
		List<GeneralQuestion> generalQuestion = new ArrayList<>();

		if (type.equals(AssessmentConstants.TYPE1)) {
//			keyword = "'.*\\\\"+ keyword + "*'";
			keyword = keyword.replace("+", "\\+");
			generalQuestion = generalQuestionRepository.findByQuestionAndIsActiveRegex(keyword, true);
		}
		if (type.equals(AssessmentConstants.TYPE2)) {
			List<GeneralQuestion> option1List = generalQuestionRepository.findByOption1AndIsActiveRegex(keyword, true);
			List<GeneralQuestion> option2List = generalQuestionRepository.findByOption2AndIsActiveRegex(keyword, true);
			List<GeneralQuestion> option3List = generalQuestionRepository.findByOption3AndIsActiveRegex(keyword, true);
			List<GeneralQuestion> option4List = generalQuestionRepository.findByOption4AndIsActiveRegex(keyword, true);

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
			generalQuestion = generalQuestionRepository.findByAnswerAndIsActiveRegex(keyword, true);
		}
		for (GeneralQuestion generalQuestion2 : generalQuestion) {
			resultDto.add(getGeneralQuestionDto(generalQuestion2));
		}

		return resultDto;
	}

	@Override
	public Map<String, Object> searchGenQnPage(Pageable paging, String type, String keyword) throws Exception {
		List<GeneralQuestionDto> resultDto = new ArrayList<>();
		List<GeneralQuestion> generalQuestion = new ArrayList<>();

		Page<GeneralQuestion> genQnPage = null;
		keyword = AssessmentDefaultMethods.replaceSplCharKeyword(keyword);

		if (type.equals(AssessmentConstants.TYPE1)) {
//			keyword = keyword.replace("+", "\\+").replace("$", "\\$").replace("^", "\\^").replace("{", "\\{");
			genQnPage = generalQuestionRepository.findByQuestionAndIsActiveRegex(keyword, true, paging);
			generalQuestion = genQnPage.getContent();
		}
		if (type.equals(AssessmentConstants.TYPE2)) {

			genQnPage = generalQuestionRepository.getAllOptionsIsActiveRegex(keyword, true, paging);

			generalQuestion = genQnPage.getContent();
		}
		if (type.equals(AssessmentConstants.TYPE3)) {
			genQnPage = generalQuestionRepository.findByAnswerAndIsActiveRegex(keyword, true, paging);
			generalQuestion = genQnPage.getContent();
		}
		for (GeneralQuestion generalQuestion2 : generalQuestion) {
			resultDto.add(getGeneralQuestionDto(generalQuestion2));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("generalQns", resultDto);
		response.put("currentPage", genQnPage.getNumber());
		response.put("totalItems", genQnPage.getTotalElements());
		response.put("totalPages", genQnPage.getTotalPages());
		response.put("totalRecords", genQnPage.getTotalPages());
		return response;
	}

	@Override
//	@Cacheable(value = "cacheGenQnList")
	public Map<String, Object> getAllGeneralQuestionsPage(Pageable paging) throws Exception {
		paging.getSort();
		Page<GeneralQuestion> genQnPage = generalQuestionRepository.findAllByIsActive(true, paging);
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

		List<GeneralQuestion> GeneralQuestionsList = generalQuestionRepository.findAllByIsActive(false,
				AssessmentUtil.orderByUpdatedOnDesc());

		List<GeneralQuestionDto> GeneralQuestionDtoList = new ArrayList<GeneralQuestionDto>();
		for (GeneralQuestion GeneralQuestion : GeneralQuestionsList) {
			GeneralQuestion.setisActive(true);
			generalQuestionRepository.save(GeneralQuestion);
			GeneralQuestionDtoList.add(this.getGeneralQuestionDto(GeneralQuestion));
		}

		return GeneralQuestionDtoList;
	}

	@Override
	public GeneralQuestionDto inactiveGeneralQuestionById(String id) throws Exception {
		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		GeneralQuestion generalQuestion = generalQuestionRepository.findByIdAndIsActive(Long.parseLong(id), true);
		if (generalQuestion == null) {
			resultDto.setMessage("Data does not exist");
			return resultDto;
		}

		generalQuestion.setisActive(false);
		generalQuestionRepository.save(generalQuestion);
		resultDto.setMessage("This question is moved inactive state");
		return resultDto;
	}

	private TechQuestionDto getTechQuestionDto(TechQuestion techQuestion) {
		TechQuestionDto techQuestionDto = new TechQuestionDto();

		Team team = teamRepo.findById(techQuestion.getTeamId());

		techQuestionDto.setId(techQuestion.getId());
		techQuestionDto.setTeamId(techQuestion.getTeamId());
		if (team.getTeam() != null) {
			techQuestionDto.setTeam(team.getTeam());
		}
		techQuestionDto.setQuestion(techQuestion.getQuestion());
		techQuestionDto.setOption1(techQuestion.getOption1());
		techQuestionDto.setOption2(techQuestion.getOption2());
		techQuestionDto.setOption3(techQuestion.getOption3());
		techQuestionDto.setOption4(techQuestion.getOption4());
		techQuestionDto.setAnswer(techQuestion.getAnswer());
		techQuestionDto.setisActive(true);
		techQuestionDto.setUpdatedOn(techQuestion.getUpdatedOn());
		return techQuestionDto;

	}

	@Override
	public Map<String, Object> getInactiveQns(String type, String keyword) throws Exception {

		Map<String, Object> response = new HashMap<>();

		// tech qn
		if (type.equals(AssessmentConstants.TYPE13)) {
			List<TechQuestion> techList = techRepo.findAllByTeamIdAndIsActive(Long.parseLong(keyword), false);
			List<TechQuestionDto> resultDto = new ArrayList<>();
			for (TechQuestion techQuestion : techList) {
				resultDto.add(this.getTechQuestionDto(techQuestion));
			}
			response.put("inactiveQns", resultDto);
		}

		// gen qn
		if (type.equals(AssessmentConstants.TYPE14)) {
			List<GeneralQuestion> genList = generalQuestionRepository.findAllByIsActive(false);
			List<GeneralQuestionDto> resultDto = new ArrayList<>();
			for (GeneralQuestion generalQuestion : genList) {
				resultDto.add(this.getGeneralQuestionDto(generalQuestion));
			}
			response.put("inactiveQns", resultDto);
		}

		return response;
	}

	@Override
	public Map<String, Object> activeQuestionById(String type, String id) throws Exception {
		Map<String, Object> response = new HashMap<>();

		if (type.equals(AssessmentConstants.TYPE14)) {
			GeneralQuestion generalQuestion = generalQuestionRepository.findByIdAndIsActive(Long.parseLong(id), false);
			if (generalQuestion == null) {
				response.put("status", 1);
				response.put("message", "Data does not exist");
				return response;
			}

			generalQuestion.setisActive(true);
			generalQuestion.setUpdatedon(LocalDateTime.now());
			generalQuestionRepository.save(generalQuestion);
			response.put("status", 0);
			response.put("message", "This question is moved active state");
			return response;
		} else if (type.equals(AssessmentConstants.TYPE13)) {
			TechQuestion techQuestion = techRepo.findByIdAndIsActive(Long.parseLong(id), false);
			if (techQuestion == null) {
				response.put("status", 1);
				response.put("message", "Data does not exist");
				return response;
			}

			techQuestion.setisActive(true);
			techQuestion.setUpdatedOn(LocalDateTime.now());
			techRepo.save(techQuestion);
			response.put("status", 0);
			response.put("message", "This question is moved active state");
			return response;
		}
		return new HashMap<>();
	}

	private static <T> Set<T> findDuplicates(List<T> list) {
		return list.stream().distinct().filter(i -> Collections.frequency(list, i) > 1).collect(Collectors.toSet());
	}

	@Override
	public Map<String, Object> saveBulkGeneralQuestions(List<GeneralQuestion> generalQuestion) throws Exception {

		Map<String, Object> response = new HashMap<>();

		// Duplicate Check
		int index = 0;
		for (GeneralQuestion generalQuestion2 : generalQuestion) {
			index++;
			if (generalQuestion2.getQuestion() == null || generalQuestion2.getOption1() == null
					|| generalQuestion2.getOption2() == null || generalQuestion2.getOption3() == null
					|| generalQuestion2.getOption4() == null || generalQuestion2.getAnswer() == null) {
				response.put("status", 1);
				response.put("message", "One of the cell value has empty value");
				return response;

			}

			if (!(generalQuestion2.getAnswer().equals(generalQuestion2.getOption1())
					|| generalQuestion2.getAnswer().equals(generalQuestion2.getOption2())
					|| generalQuestion2.getAnswer().equals(generalQuestion2.getOption3())
					|| generalQuestion2.getAnswer().equals(generalQuestion2.getOption4()))) {
				response.put("status", 1);
				response.put("message",
						"Answer not matches with given options for the Question - " + generalQuestion2.getQuestion());
				return response;
			}

			if (generalQuestion2.getOption1().equals(generalQuestion2.getOption2())
					|| generalQuestion2.getOption1().equals(generalQuestion2.getOption3())
					|| generalQuestion2.getOption1().equals(generalQuestion2.getOption4())
					|| generalQuestion2.getOption2().equals(generalQuestion2.getOption1())
					|| generalQuestion2.getOption2().equals(generalQuestion2.getOption3())
					|| generalQuestion2.getOption2().equals(generalQuestion2.getOption4())
					|| generalQuestion2.getOption3().equals(generalQuestion2.getOption1())
					|| generalQuestion2.getOption3().equals(generalQuestion2.getOption2())
					|| generalQuestion2.getOption3().equals(generalQuestion2.getOption4())
					|| generalQuestion2.getOption4().equals(generalQuestion2.getOption1())
					|| generalQuestion2.getOption4().equals(generalQuestion2.getOption2())
					|| generalQuestion2.getOption4().equals(generalQuestion2.getOption3())) {
				response.put("status", 1);
				response.put("message",
						"One of the options has duplication in Question - " + generalQuestion2.getQuestion());
				return response;
			}

		}

		// List of All Questions
		List<String> allQns = new ArrayList<>();

		for (GeneralQuestion generalQuestion2 : generalQuestion) {
			allQns.add(generalQuestion2.getQuestion());
		}

		// Find Duplication Questions
		Set<String> duplicates = findDuplicates(allQns);
		if (duplicates.size() > 0) {
			response.put("status", 1);
			response.put("message", "The following questions has duplication");
			response.put("duplicateQns", duplicates);
			return response;
		}

		// Is everything is fine save all Questions
		for (GeneralQuestion generalQuestion2 : generalQuestion) {
			if (generalQuestionRepository.existsByQuestion(generalQuestion2.getQuestion())) {
				response.put("status", 1);
				response.put("message", "This Question already exists - " + generalQuestion2.getQuestion());
				return response;
			}

			GeneralQuestion saveQn = new GeneralQuestion();
			saveQn.setId(commonService.nextSequenceNumber());
			saveQn.setQuestion(generalQuestion2.getQuestion());
			saveQn.setOption1(generalQuestion2.getOption1());
			saveQn.setOption2(generalQuestion2.getOption2());
			saveQn.setOption3(generalQuestion2.getOption3());
			saveQn.setOption4(generalQuestion2.getOption4());
			saveQn.setAnswer(generalQuestion2.getAnswer());
			saveQn.setUpdatedon(LocalDateTime.now());
			saveQn.setisActive(true);
			generalQuestionRepository.save(saveQn);
		}
		response.put("status", 0);
		response.put("message", "Questions added successfully");
		return response;
	}

}
