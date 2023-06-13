package com.promantus.Assessment.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.ProgramQuestionDto;
import com.promantus.Assessment.Dto.SearchDto;
import com.promantus.Assessment.Entity.ProgramQuestion;
import com.promantus.Assessment.Service.ProgramQuestionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ProgramQuestionController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(ProgramQuestionController.class);

	@Autowired
	private ProgramQuestionService programQuestionService;

	@PostMapping("/addProgramQuestion")
	public ProgramQuestionDto addProgramQuestion(@RequestBody ProgramQuestionDto programQuestionDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		ProgramQuestionDto resultDto = new ProgramQuestionDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

			if (programQuestionDto.getTeamId() == null || programQuestionDto.getTeamId().isEmpty()) {
				resultDto.setMessage("Team Id is Missing");
				resultDto.setStatus(1);
				return resultDto;
			}

			if (programQuestionDto.getProgram() == null || programQuestionDto.getProgram().isEmpty()) {
				resultDto.setMessage("Program is Missing");
				resultDto.setStatus(1);
				return resultDto;
			}
			
			if (programQuestionDto.getProgramLevel() == null || programQuestionDto.getProgramLevel().isEmpty()) {
				resultDto.setMessage("Program Level is Missing");
				resultDto.setStatus(1);
				return resultDto;
			}


			resultDto = programQuestionService.addProgramQuestion(programQuestionDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@GetMapping("/getAllProgramQuestions")
	public List<ProgramQuestionDto> getAllProgramQuestions(
			@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return programQuestionService.getAllProgramQuestions();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<ProgramQuestionDto>();
	}

	@GetMapping("/getAllProgramQuestionsPage")
	public Map<String, Object> getAllProgramQuestionsPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestHeader(name = "lang", required = false) String lang) {

		Pageable paging = PageRequest.of(page, size, Sort.by("updatedon").descending());
		try {

			return programQuestionService.getAllProgramQuestionsPage(paging);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}

	@GetMapping("/getProgramQuestionById/{id}")
	public ProgramQuestionDto getProgramQuestionById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		ProgramQuestionDto programQuestionDto = new ProgramQuestionDto();
		try {
			programQuestionDto = programQuestionService.getProgramQuestionById(id);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return programQuestionDto;
	}

	@PutMapping("/updateProgramQuestion")
	public ProgramQuestionDto updateProgramQuestion(@RequestBody ProgramQuestionDto programQuestionDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		ProgramQuestionDto resultDto = new ProgramQuestionDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

			if (programQuestionDto.getProgramLevel() == null || programQuestionDto.getProgramLevel().isEmpty()) {
				errorParam.append("Program Level is Missing");
			}
			if (programQuestionDto.getProgram() == null || programQuestionDto.getProgram().isEmpty()) {
				errorParam.append("Program is Missing");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = programQuestionService.updateProgramQuestion(programQuestionDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@DeleteMapping("/deleteProgramQuestionById/{id}")
	public ProgramQuestionDto deleteProgramQuestionById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		ProgramQuestionDto resultDto = new ProgramQuestionDto();
		try {

			return programQuestionService.deleteProgramQuestionById(id);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return resultDto;
		}
	}

	@GetMapping("/inactiveProgramQuestionById/{id}")
	public ProgramQuestionDto inactiveProgramQuestionById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		ProgramQuestionDto resultDto = new ProgramQuestionDto();
		try {

			return programQuestionService.inactiveProgramQuestionById(id);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return resultDto;
		}
	}

//	@GetMapping("/searchProgramQuestionPage/{type}/{keyword}")
//	public List<ProgramQuestionDto> search(@PathVariable String keyword, @PathVariable String type,
//			@RequestHeader(name = "lang", required = false) String lang) {
//
//		List<ProgramQuestionDto> programQuestionDto = new ArrayList<>();
//		try {
//			programQuestionDto = programQuestionService.search(type, keyword);
//		} catch (final Exception e) {
//			logger.error(AssessmentUtil.getErrorMessage(e));
//		}
//
//		return programQuestionDto;
//	}

	@PostMapping("/searchProgramQuestionPage")
	public Map<String, Object> searchProgQnPage(@RequestBody SearchDto searchValues,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
			@RequestHeader(name = "lang", required = false) String lang) {

		Pageable paging = PageRequest.of(page, size, Sort.by("updatedon").descending());

		try {
			return programQuestionService.searchProgramQuestionPage(paging, searchValues.getType(), searchValues.getKeyword());
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}
//
//	@GetMapping("activateAllProgQns")
//	public List<ProgramQuestionDto> activateAllProgQns(@RequestHeader(name = "lang", required = false) String lang) {
//
//		try {
//
//			return programQuestionService.activateAllProgQns();
//
//		} catch (final Exception e) {
//			logger.error(AssessmentUtil.getErrorMessage(e));
//		}
//
//		return new ArrayList<ProgramQuestionDto>();
//	}
//	
	@PostMapping("/saveBulkProgramQuestions")
	public Map<String, Object> saveBulkProgramQuestions(@RequestBody List<ProgramQuestion> programQuestion,
			@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return programQuestionService.saveBulkProgramQuestions(programQuestion);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}

	@GetMapping("getInactiveProgramQns/{type}/{keyword}")
	public Map<String, Object> getInactiveQns(@PathVariable String keyword, @PathVariable String type,
			@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return programQuestionService.getInactiveQns(type, keyword);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}

	@GetMapping("/activeProgramQuestionById/{type}/{id}")
	public Map<String, Object> activeQuestionById(@PathVariable String type, @PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		ProgramQuestionDto resultDto = new ProgramQuestionDto();
		try {

			return programQuestionService.activeQuestionById(type, id);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return new HashMap<>();
		}

	}

}
