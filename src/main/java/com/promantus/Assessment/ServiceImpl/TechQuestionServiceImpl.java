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
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentDefaultMethods;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.GeneralQuestionDto;
import com.promantus.Assessment.Dto.TechQuestionDto;
import com.promantus.Assessment.Entity.GeneralQuestion;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.TechQuestion;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Repository.TechQuestionRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.TechQuestionService;

@Service
public class TechQuestionServiceImpl implements TechQuestionService {

	@Autowired
	TechQuestionRepository techQuestionRepository;

	@Autowired
	CommonService commonService;

	@Autowired
	TeamRepository teamRepo;

	@Override
	public TechQuestionDto addTechQuestion(final TechQuestionDto techQuestionDto, String lang) throws Exception {

		TechQuestionDto resultDto = new TechQuestionDto();

		if (techQuestionRepository.existsByQuestion(techQuestionDto.getQuestion())) {
			resultDto.setMessage("This Question already exists");
			resultDto.setStatus(1);
			return resultDto;
		}

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
			techQuestion.setUpdatedOn(LocalDateTime.now());
			techQuestion.setisActive(true);
			techQuestionRepository.save(techQuestion);
		}
		resultDto.setMessage("TechQuestion added successfully");
		return resultDto;
	}

	@Override
	public List<TechQuestionDto> getAllTechQuestions() throws Exception {
		List<TechQuestion> TechQuestionsList = techQuestionRepository.findAllByIsActive(true,
				AssessmentUtil.orderByUpdatedOnDesc());

		List<TechQuestionDto> TechQuestionDtoList = new ArrayList<TechQuestionDto>();
		for (TechQuestion TechQuestion : TechQuestionsList) {
			TechQuestionDtoList.add(this.getTechQuestionDto(TechQuestion));
		}

		return TechQuestionDtoList;
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
		techQuestion.setUpdatedBy(techQuestionDto.getUpdatedBy());
		techQuestion.setUpdatedOn(LocalDateTime.now());
		techQuestion.setisActive(true);
		techQuestionRepository.save(techQuestion);
		resultDto.setMessage("Record Updated successfully");
		return resultDto;

	}

	@Override
	public TechQuestionDto deleteTechQuestionById(String id) throws Exception {
		TechQuestionDto resultDto = new TechQuestionDto();
		TechQuestion techQuestion = techQuestionRepository.findByIdAndIsActive(Long.parseLong(id), true);
		if (techQuestion == null) {

			resultDto.setMessage("data does not exist");
			return resultDto;
		}

		techQuestion.setisActive(false);
		techQuestionRepository.save(techQuestion);
		resultDto.setMessage("Record Deleted successfully");
		return resultDto;
	}

	@Override
	public TechQuestionDto getTechQuestionById(String id) throws Exception {

		TechQuestion techQuestion = techQuestionRepository.findById(Long.parseLong(id));

		return techQuestion != null ? this.getTechQuestionDto(techQuestion) : new TechQuestionDto();

	}

	@Override
	public List<TechQuestionDto> findAndReplceByOtherTeamId(long findId, long replaceId) throws Exception {

		List<TechQuestionDto> TechQuestionDtoList = new ArrayList<TechQuestionDto>();

		// Step 1: Find all the records with findId variable
		if (techQuestionRepository.existsByTeamId(findId)) {
			List<TechQuestion> list = techQuestionRepository.findByTeamId(findId);
			if (list != null) {
				for (TechQuestion techQuestion : list) {
					// Step 2: Replace it to new teamId ie.,replaceId
					techQuestion.setTeamId(replaceId);
					TechQuestionDtoList.add(this.getTechQuestionDto(techQuestion));

					techQuestionRepository.save(techQuestion);
				}
			}
		}

		return TechQuestionDtoList;
	}

	@Override
	public List<TechQuestionDto> searchtechQns(String type, String keyword) throws Exception {
		List<TechQuestionDto> resultDto = new ArrayList<>();
		List<TechQuestion> techQuestion = new ArrayList<>();

		if (type.equals(AssessmentConstants.TYPE1)) {
			techQuestion = techQuestionRepository.findByQuestionRegex(keyword);
		}
		if (type.equals(AssessmentConstants.TYPE2)) {
			List<TechQuestion> option1List = techQuestionRepository.findByOption1Regex(keyword);
			List<TechQuestion> option2List = techQuestionRepository.findByOption2Regex(keyword);
			List<TechQuestion> option3List = techQuestionRepository.findByOption3Regex(keyword);
			List<TechQuestion> option4List = techQuestionRepository.findByOption4Regex(keyword);

			for (TechQuestion techQuestion2 : option1List) {
				techQuestion.add(techQuestion2);
			}

			for (TechQuestion techQuestion3 : option2List) {
				techQuestion.add(techQuestion3);
			}

			for (TechQuestion techQuestion4 : option3List) {
				techQuestion.add(techQuestion4);
			}

			for (TechQuestion techQuestion5 : option4List) {
				techQuestion.add(techQuestion5);
			}
		}
		if (type.equals(AssessmentConstants.TYPE3)) {
			techQuestion = techQuestionRepository.findByAnswerRegex(keyword);
		}

		if (type.equals(AssessmentConstants.TYPE4)) {
			List<Team> teamList = teamRepo.findByTeamRegex(keyword);
			List<TechQuestion> allTechQns = techQuestionRepository.findAll();

			for (TechQuestion techQuestion2 : allTechQns) {
				for (Team team : teamList) {
					if (techQuestion2.getTeamId().equals(team.getId())) {
						techQuestion.add(techQuestion2);
					}
				}
			}

		}
		for (TechQuestion techQuestion2 : techQuestion) {
			resultDto.add(getTechQuestionDto(techQuestion2));
		}

		return resultDto;
	}

	@Override
//	@Cacheable(value = "cacheTechQnList")
	public Map<String, Object> getAllTechQuestionsPage(Pageable paging) throws Exception {
		long startTime = System.nanoTime();

		System.out.println("Inside tech Qn Page List");
		simulateSlowService();
		Page<TechQuestion> techQnPage = techQuestionRepository.findAllByIsActive(true, paging);

		List<TechQuestion> TechQuestionsList = techQnPage.getContent();
		List<TechQuestionDto> resultDto = new ArrayList<>();
		for (TechQuestion techQuestion : TechQuestionsList) {
			resultDto.add(this.getTechQuestionDto(techQuestion));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("techQns", resultDto);
		response.put("currentPage", techQnPage.getNumber());
		response.put("totalItems", techQnPage.getTotalElements());
		response.put("totalPages", techQnPage.getTotalPages());

		long endTime = System.nanoTime();
		System.out.println("Start Time: " + (startTime / 1000) % 60);
		System.out.println("End Time: " + (endTime / 1000) % 60);
		return response;
	}

	private void simulateSlowService() {
		try {
			long time = 3000L;
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public List<TechQuestionDto> activateAllTechQns() throws Exception {
		List<TechQuestion> TechQuestionList = techQuestionRepository.findAllByIsActive(false,
				AssessmentUtil.orderByUpdatedOnDesc());

		List<TechQuestionDto> GeneralQuestionDtoList = new ArrayList<TechQuestionDto>();
		for (TechQuestion TechQuestion : TechQuestionList) {
			TechQuestion.setisActive(true);
			techQuestionRepository.save(TechQuestion);
			GeneralQuestionDtoList.add(this.getTechQuestionDto(TechQuestion));
		}

		return GeneralQuestionDtoList;
	}

	@Override
	public Map<String, Object> searchtechQnsPage(Pageable paging, String type, String keyword) throws Exception {
		List<TechQuestion> techQuestion = new ArrayList<>();

		Page<TechQuestion> techQnPage = null;
		keyword = AssessmentDefaultMethods.replaceSplCharKeyword(keyword);

		if (type.equals(AssessmentConstants.TYPE1)) {
			techQnPage = techQuestionRepository.findByQuestionAndIsActiveRegex(keyword, true, paging);
			techQuestion = techQnPage.getContent();
		}
		if (type.equals(AssessmentConstants.TYPE2)) {

			techQnPage = techQuestionRepository.getAllOptionsIsActiveRegex(keyword, true, paging);

			techQuestion = techQnPage.getContent();
		}
		if (type.equals(AssessmentConstants.TYPE3)) {
			techQnPage = techQuestionRepository.findByAnswerAndIsActiveRegex(keyword, true, paging);
			techQuestion = techQnPage.getContent();
		}
		if (type.equals(AssessmentConstants.TYPE4)) {
			techQnPage = techQuestionRepository.findAllByTeamIdAndIsActive(Integer.parseInt(keyword), true, paging);

			techQuestion = techQnPage.getContent();
		}

		List<TechQuestionDto> TechQuestionDtoList = new ArrayList<TechQuestionDto>();

		for (TechQuestion techQuestion2 : techQuestion) {
			TechQuestionDtoList.add(this.getTechQuestionDto(techQuestion2));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("techQns", TechQuestionDtoList);
		response.put("currentPage", techQnPage.getNumber());
		response.put("totalItems", techQnPage.getTotalElements());
		response.put("totalPages", techQnPage.getTotalPages());
		response.put("totalRecords", techQnPage.getTotalPages());
		return response;
	}

	@Override
	public TechQuestionDto inactiveTechQuestionById(String id) throws Exception {
		TechQuestionDto resultDto = new TechQuestionDto();
		TechQuestion techQuestion = techQuestionRepository.findByIdAndIsActive(Long.parseLong(id), true);
		if (techQuestion == null) {
			resultDto.setMessage("Data does not exist");
			return resultDto;
		}

		techQuestion.setisActive(false);
		techQuestionRepository.save(techQuestion);
		resultDto.setMessage("This question is moved inactive state");
		return resultDto;
	}

	private static <T> Set<T> findDuplicates(List<T> list) {
		return list.stream().distinct().filter(i -> Collections.frequency(list, i) > 1).collect(Collectors.toSet());
	}

	@Override
	public Map<String, Object> saveBulkTechQuestions(List<TechQuestion> technicalQuestion) throws Exception {

		Map<String, Object> response = new HashMap<>();

		// Duplicate Check
		int index = 0;
		for (TechQuestion technicalQuestion2 : technicalQuestion) {
			index++;
			if (technicalQuestion2.getQuestion() == null || technicalQuestion2.getOption1() == null
					|| technicalQuestion2.getOption2() == null || technicalQuestion2.getOption3() == null
					|| technicalQuestion2.getOption4() == null || technicalQuestion2.getAnswer() == null) {
				response.put("status", 1);
				response.put("message", "One of the cell value has empty value");
				return response;

			}

			if (!(technicalQuestion2.getAnswer().equals(technicalQuestion2.getOption1())
					|| technicalQuestion2.getAnswer().equals(technicalQuestion2.getOption2())
					|| technicalQuestion2.getAnswer().equals(technicalQuestion2.getOption3())
					|| technicalQuestion2.getAnswer().equals(technicalQuestion2.getOption4()))) {
				response.put("status", 1);
				response.put("message",
						"Answer not matches with given options for the Question - " + technicalQuestion2.getQuestion());
				return response;
			}

			if (technicalQuestion2.getOption1().equals(technicalQuestion2.getOption2())
					|| technicalQuestion2.getOption1().equals(technicalQuestion2.getOption3())
					|| technicalQuestion2.getOption1().equals(technicalQuestion2.getOption4())
					|| technicalQuestion2.getOption2().equals(technicalQuestion2.getOption1())
					|| technicalQuestion2.getOption2().equals(technicalQuestion2.getOption3())
					|| technicalQuestion2.getOption2().equals(technicalQuestion2.getOption4())
					|| technicalQuestion2.getOption3().equals(technicalQuestion2.getOption1())
					|| technicalQuestion2.getOption3().equals(technicalQuestion2.getOption2())
					|| technicalQuestion2.getOption3().equals(technicalQuestion2.getOption4())
					|| technicalQuestion2.getOption4().equals(technicalQuestion2.getOption1())
					|| technicalQuestion2.getOption4().equals(technicalQuestion2.getOption2())
					|| technicalQuestion2.getOption4().equals(technicalQuestion2.getOption3())) {
				response.put("status", 1);
				response.put("message",
						"One of the options has duplication in Question - " + technicalQuestion2.getQuestion());
				return response;
			}

		}

		// List of All Questions
		List<String> allQns = new ArrayList<>();

		for (TechQuestion technicalQuestion2 : technicalQuestion) {
			allQns.add(technicalQuestion2.getQuestion());
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
		for (TechQuestion technicalQuestion2 : technicalQuestion) {
			if (techQuestionRepository.existsByQuestion(technicalQuestion2.getQuestion())) {
				response.put("status", 1);
				response.put("message", "This Question already exists - " + technicalQuestion2.getQuestion());
				return response;
			}

			TechQuestion saveQn = new TechQuestion();
			saveQn.setId(commonService.nextSequenceNumber());
			saveQn.setQuestion(technicalQuestion2.getQuestion());
			saveQn.setOption1(technicalQuestion2.getOption1());
			saveQn.setOption2(technicalQuestion2.getOption2());
			saveQn.setOption3(technicalQuestion2.getOption3());
			saveQn.setOption4(technicalQuestion2.getOption4());
			saveQn.setAnswer(technicalQuestion2.getAnswer());
			saveQn.setTeamId(technicalQuestion2.getTeamId());
			saveQn.setUpdatedOn(LocalDateTime.now());
			saveQn.setisActive(true);
			techQuestionRepository.save(saveQn);
		}
		response.put("status", 0);
		response.put("message", "Questions added successfully");
		return response;
	}

}
