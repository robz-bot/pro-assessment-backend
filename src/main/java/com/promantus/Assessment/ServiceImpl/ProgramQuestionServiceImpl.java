package com.promantus.Assessment.ServiceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentDefaultMethods;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.ProgramQuestionDto;
import com.promantus.Assessment.Dto.SearchDto;
import com.promantus.Assessment.Dto.ProgramQuestionDto;
import com.promantus.Assessment.Entity.ProgramQuestion;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.ProgramQuestion;
import com.promantus.Assessment.Repository.ProgramQuestionRepository;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Repository.ProgramQuestionRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.ProgramQuestionService;
import com.twilio.rest.api.v2010.account.availablephonenumbercountry.Local;

@Service
public class ProgramQuestionServiceImpl implements ProgramQuestionService {

	@Autowired
	ProgramQuestionRepository programQuestionRepository;

	@Autowired
	CommonService commonService;

	@Autowired
	MongoOperations mongoOperation;

	@Autowired
	TeamRepository teamRepo;

	@Override
	public ProgramQuestionDto addProgramQuestion(final ProgramQuestionDto programQuestionDto, String lang)
			throws Exception {

		ProgramQuestionDto resultDto = new ProgramQuestionDto();

		if (programQuestionRepository.existsByQuestion(programQuestionDto.getQuestion())) {
			resultDto.setMessage("This Question already exists");
			resultDto.setStatus(1);
			return resultDto;
		}

		ProgramQuestion programQuestion = new ProgramQuestion();
		programQuestion.setId(commonService.nextSequenceNumber());
		programQuestion.setTeamId(programQuestionDto.getTeamId());
		programQuestion.setQuestion(programQuestionDto.getQuestion());
		programQuestion.setProgram(programQuestionDto.getProgram());
		programQuestion.setCreatedBy(programQuestionDto.getCreatedBy());
		programQuestion.setUpdatedBy(programQuestionDto.getUpdatedBy());
		programQuestion.setCreatedOn(LocalDateTime.now());
		programQuestion.setUpdatedOn(LocalDateTime.now());
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(formatter);
		programQuestion.setDate(formattedDate);
		programQuestion.setisActive(true);
		programQuestionRepository.save(programQuestion);

		resultDto.setMessage("ProgramQuestion added successfully");
		return resultDto;
	}

	@Override
	public List<ProgramQuestionDto> getAllProgramQuestions() throws Exception {
		List<ProgramQuestion> ProgramQuestionsList = programQuestionRepository.findAllByIsActive(true,
				AssessmentUtil.orderByUpdatedOnDesc());

		List<ProgramQuestionDto> ProgramQuestionDtoList = new ArrayList<ProgramQuestionDto>();
		for (ProgramQuestion ProgramQuestion : ProgramQuestionsList) {
			ProgramQuestionDtoList.add(this.getProgramQuestionDto(ProgramQuestion));
		}

		return ProgramQuestionDtoList;
	}

	private ProgramQuestionDto getProgramQuestionDto(ProgramQuestion programQuestion) {
		ProgramQuestionDto programQuestionDto = new ProgramQuestionDto();

		programQuestionDto.setId(programQuestion.getId());
		programQuestionDto.setTeamId(programQuestion.getTeamId());
		programQuestionDto.setQuestion(programQuestion.getQuestion());
		programQuestionDto.setProgram(programQuestion.getProgram());
		programQuestionDto.setisActive(programQuestion.getisActive());
		programQuestionDto.setCreatedBy(programQuestion.getCreatedBy());
		programQuestionDto.setUpdatedBy(programQuestion.getUpdatedBy());
		programQuestionDto.setCreatedOn(programQuestion.getCreatedOn());
		programQuestionDto.setUpdatedOn(programQuestion.getUpdatedOn());
		programQuestionDto.setDate(programQuestion.getDate());

		return programQuestionDto;

	}

	@Override
	public ProgramQuestionDto updateProgramQuestion(ProgramQuestionDto programQuestionDto, String lang)
			throws Exception {

		ProgramQuestionDto resultDto = new ProgramQuestionDto();
		System.out.println(programQuestionDto.getId());
		ProgramQuestion programQuestion = programQuestionRepository.findById(programQuestionDto.getId());

		if (programQuestion == null) {

			resultDto.setMessage("ProgramQuestion does not exist");
			return resultDto;
		}
		programQuestion.setQuestion(programQuestionDto.getQuestion());
		programQuestion.setTeamId(programQuestionDto.getTeamId());
		programQuestion.setProgram(programQuestionDto.getProgram());
		programQuestion.setCreatedBy(programQuestionDto.getCreatedBy());
		programQuestion.setUpdatedBy(programQuestionDto.getUpdatedBy());
		programQuestion.setCreatedOn(programQuestionDto.getCreatedOn());
		programQuestion.setUpdatedOn(programQuestionDto.getUpdatedOn());
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(formatter);
		programQuestion.setDate(formattedDate);
		programQuestion.setisActive(true);
		programQuestionRepository.save(programQuestion);
		resultDto.setMessage("Record Updated successfully");
		return resultDto;

	}

	@Override
	public ProgramQuestionDto deleteProgramQuestionById(String id) throws Exception {
		ProgramQuestionDto resultDto = new ProgramQuestionDto();
		ProgramQuestion programQuestion = programQuestionRepository.findByIdAndIsActive(Long.parseLong(id), true);
		if (programQuestion == null) {
			resultDto.setMessage("Data does not exist");
			return resultDto;
		}

		programQuestionRepository.delete(programQuestion);
		resultDto.setMessage("Record Deleted successfully");
		return resultDto;
	}

	@Override
	public ProgramQuestionDto getProgramQuestionById(String id) throws Exception {

		ProgramQuestion programQuestion = programQuestionRepository.findById(Long.parseLong(id));

		return programQuestion != null ? this.getProgramQuestionDto(programQuestion) : new ProgramQuestionDto();

	}

	@Override
	public List<ProgramQuestionDto> search(String type, String keyword) throws Exception {
		List<ProgramQuestionDto> resultDto = new ArrayList<>();
		List<ProgramQuestion> programQuestion = new ArrayList<>();

		if (type.equals(AssessmentConstants.TYPE1)) {
//			keyword = "'.*\\\\"+ keyword + "*'";
			keyword = keyword.replace("+", "\\+");
			programQuestion = programQuestionRepository.findByQuestionAndIsActiveRegex(keyword, true);
		}
		if (type.equals(AssessmentConstants.TYPE8)) {
			keyword = keyword.replace("+", "\\+");
			List<ProgramQuestion> progQns = programQuestionRepository.findByQuestionAndIsActiveRegex(keyword, true);

			for (ProgramQuestion programQuestion2 : progQns) {
				if (programQuestion2.getUpdatedOn().toString().split("T")[0] == keyword) {
					programQuestion.add(programQuestion2);
				}
			}
		}
		
		if (type.equals(AssessmentConstants.TYPE15)) {
			programQuestion = programQuestionRepository.findByProgramAndIsActiveRegex(keyword, true);
		}
		for (ProgramQuestion programQuestion2 : programQuestion) {
			resultDto.add(getProgramQuestionDto(programQuestion2));
		}

		return resultDto;
	}

	
	@Override
	public Map<String, Object> getAllProgramQuestionsPage(Pageable paging) throws Exception {
		paging.getSort();
		Page<ProgramQuestion> progQnPage = programQuestionRepository.findAllByIsActive(true, paging);
		List<ProgramQuestionDto> resultDto = new ArrayList<>();
		List<ProgramQuestion> ProgramQuestionsList = progQnPage.getContent();
		for (ProgramQuestion ProgramQuestion : ProgramQuestionsList) {
			resultDto.add(this.getProgramQuestionDto(ProgramQuestion));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("programQns", ProgramQuestionsList);
		response.put("currentPage", progQnPage.getNumber());
		response.put("totalItems", progQnPage.getTotalElements());
		response.put("totalPages", progQnPage.getTotalPages());
		response.put("totalRecords", progQnPage.getTotalPages());
		return response;
	}

//	@Override
//	public List<ProgramQuestionDto> activateAllProgQns() throws Exception {
//
//		List<ProgramQuestion> ProgramQuestionsList = programQuestionRepository.findAllByIsActive(false,
//				AssessmentUtil.orderByUpdatedOnDesc());
//
//		List<ProgramQuestionDto> ProgramQuestionDtoList = new ArrayList<ProgramQuestionDto>();
//		for (ProgramQuestion ProgramQuestion : ProgramQuestionsList) {
//			ProgramQuestion.setisActive(true);
//			programQuestionRepository.save(ProgramQuestion);
//			ProgramQuestionDtoList.add(this.getProgramQuestionDto(ProgramQuestion));
//		}
//
//		return ProgramQuestionDtoList;
//	}

	@Override
	public ProgramQuestionDto inactiveProgramQuestionById(String id) throws Exception {
		ProgramQuestionDto resultDto = new ProgramQuestionDto();
		ProgramQuestion programQuestion = programQuestionRepository.findByIdAndIsActive(Long.parseLong(id), true);
		if (programQuestion == null) {
			resultDto.setMessage("Data does not exist");
			return resultDto;
		}

		programQuestion.setisActive(false);
		programQuestionRepository.save(programQuestion);
		resultDto.setMessage("This question is moved inactive state");
		return resultDto;
	}

	@Override
	public Map<String, Object> getInactiveQns(String type, String keyword) throws Exception {

		Map<String, Object> response = new HashMap<>();

		// program qn
		if (type.equals(AssessmentConstants.TYPE13)) {
			List<ProgramQuestion> programList = programQuestionRepository.findAllByTeamIdAndIsActive(Long.parseLong(keyword), false);
			List<ProgramQuestionDto> resultDto = new ArrayList<>();
			for (ProgramQuestion programQuestion : programList) {
				resultDto.add(this.getProgramQuestionDto(programQuestion));
			}
			response.put("inactiveQns", resultDto);
		}

		// prog qn
		if (type.equals(AssessmentConstants.TYPE14)) {
			List<ProgramQuestion> progList = programQuestionRepository.findAllByIsActive(false);
			List<ProgramQuestionDto> resultDto = new ArrayList<>();
			for (ProgramQuestion programQuestion : progList) {
				resultDto.add(this.getProgramQuestionDto(programQuestion));
			}
			response.put("inactiveQns", resultDto);
		}

		return response;
	}

	@Override
	public Map<String, Object> activeQuestionById(String type, String id) throws Exception {
		Map<String, Object> response = new HashMap<>();

		if (type.equals(AssessmentConstants.TYPE14)) {
			ProgramQuestion programQuestion = programQuestionRepository.findByIdAndIsActive(Long.parseLong(id), false);
			if (programQuestion == null) {
				response.put("status", 1);
				response.put("message", "Data does not exist");
				return response;
			}

			programQuestion.setisActive(true);
			programQuestion.setUpdatedOn(LocalDateTime.now());
			programQuestionRepository.save(programQuestion);
			response.put("status", 0);
			response.put("message", "This question is moved active state");
			return response;
		} else if (type.equals(AssessmentConstants.TYPE13)) {
			ProgramQuestion programQuestion = programQuestionRepository.findByIdAndIsActive(Long.parseLong(id), false);
			if (programQuestion == null) {
				response.put("status", 1);
				response.put("message", "Data does not exist");
				return response;
			}

			programQuestion.setisActive(true);
			programQuestion.setUpdatedOn(LocalDateTime.now());
			programQuestionRepository.save(programQuestion);
			response.put("status", 0);
			response.put("message", "This question is moved active state");
			return response;
		}
		return new HashMap<>();
	}

}
