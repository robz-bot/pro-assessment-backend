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
import com.promantus.Assessment.Dto.GeneralQuestionDto;
import com.promantus.Assessment.Dto.SearchDto;
import com.promantus.Assessment.Entity.GeneralQuestion;
import com.promantus.Assessment.Service.GeneralQuestionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class GeneralQuestionController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(GeneralQuestionController.class);

	@Autowired
	private GeneralQuestionService generalQuestionService;

	@PostMapping("/addGeneralQuestion")
	public GeneralQuestionDto addGeneralQuestion(@RequestBody GeneralQuestionDto generalQuestionDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

			// GeneralQuestion

			if (generalQuestionDto.getQuestion() == null || generalQuestionDto.getQuestion().isEmpty()) {
				errorParam.append("Question is Missing");
			}
			if (generalQuestionDto.getOption1() == null || generalQuestionDto.getOption1().isEmpty()) {
				errorParam.append("Option1 is Missing");
			}
			if (generalQuestionDto.getOption2() == null || generalQuestionDto.getOption2().isEmpty()) {
				errorParam.append("Option2 is Missing");
			}
			if (generalQuestionDto.getOption3() == null || generalQuestionDto.getOption3().isEmpty()) {
				errorParam.append("Option3 is Missing");
			}
			if (generalQuestionDto.getOption4() == null || generalQuestionDto.getOption4().isEmpty()) {
				errorParam.append("Option4 is Missing");
			}
			if (generalQuestionDto.getAnswer() == null || generalQuestionDto.getAnswer().isEmpty()) {
				errorParam.append("Answer is Missing");
			}
			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = generalQuestionService.addGeneralQuestion(generalQuestionDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}
	
	@PostMapping("/saveBulkGeneralQuestions")
	public Map<String, Object> saveBulkGeneralQuestions(
			@RequestBody List<GeneralQuestion> generalQuestion,
			@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return generalQuestionService.saveBulkGeneralQuestions(generalQuestion);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}

	@GetMapping("/getAllGeneralQuestions")
	public List<GeneralQuestionDto> getAllGeneralQuestions(
			@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return generalQuestionService.getAllGeneralQuestions();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<GeneralQuestionDto>();
	}

	@GetMapping("/getAllGeneralQuestionsPage")
	public Map<String, Object> getAllGeneralQuestionsPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestHeader(name = "lang", required = false) String lang) {

		Pageable paging = PageRequest.of(page, size, Sort.by("updatedon").descending());
		try {

			return generalQuestionService.getAllGeneralQuestionsPage(paging);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}

	@GetMapping("/getGeneralQuestionById/{id}")
	public GeneralQuestionDto getGeneralQuestionById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		GeneralQuestionDto generalQuestionDto = new GeneralQuestionDto();
		try {
			generalQuestionDto = generalQuestionService.getGeneralQuestionById(id);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return generalQuestionDto;
	}

	@PutMapping("/updateGeneralQuestion")
	public GeneralQuestionDto updateGeneralQuestion(@RequestBody GeneralQuestionDto generalQuestionDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

			if (generalQuestionDto.getQuestion() == null || generalQuestionDto.getQuestion().isEmpty()) {
				errorParam.append("Question is Missing");
			}
			if (generalQuestionDto.getOption1() == null || generalQuestionDto.getOption1().isEmpty()) {
				errorParam.append("Option1 is Missing");
			}
			if (generalQuestionDto.getOption2() == null || generalQuestionDto.getOption2().isEmpty()) {
				errorParam.append("Option2 is Missing");
			}
			if (generalQuestionDto.getOption3() == null || generalQuestionDto.getOption3().isEmpty()) {
				errorParam.append("Option3 is Missing");
			}
			if (generalQuestionDto.getOption4() == null || generalQuestionDto.getOption4().isEmpty()) {
				errorParam.append("Option4 is Missing");
			}
			if (generalQuestionDto.getAnswer() == null || generalQuestionDto.getAnswer().isEmpty()) {
				errorParam.append("Answer is Missing");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = generalQuestionService.updateGeneralQuestion(generalQuestionDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@DeleteMapping("/deleteGeneralQuestionById/{id}")
	public GeneralQuestionDto deleteGeneralQuestionById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		try {

			return generalQuestionService.deleteGeneralQuestionById(id);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return resultDto;
		}
	}
	
	@GetMapping("/inactiveGeneralQuestionById/{id}")
	public GeneralQuestionDto inactiveGeneralQuestionById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		try {

			return generalQuestionService.inactiveGeneralQuestionById(id);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return resultDto;
		}
	}

	@GetMapping("/search/{type}/{keyword}")
	public List<GeneralQuestionDto> search(@PathVariable String keyword, @PathVariable String type,
			@RequestHeader(name = "lang", required = false) String lang) {

		List<GeneralQuestionDto> generalQuestionDto = new ArrayList<>();
		try {
			generalQuestionDto = generalQuestionService.search(type, keyword);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return generalQuestionDto;
	}

	@PostMapping("/searchGenQnPage")
	public Map<String, Object> searchGenQnPage(@RequestBody SearchDto searchValues,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
			@RequestHeader(name = "lang", required = false) String lang) {

		Pageable paging = PageRequest.of(page, size, Sort.by("updatedon").descending());

		try {
			return generalQuestionService.searchGenQnPage(paging, searchValues.getType(), searchValues.getKeyword());
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}

	@GetMapping("activateAllGenQns")
	public List<GeneralQuestionDto> activateAllGenQns(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return generalQuestionService.activateAllGenQns();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<GeneralQuestionDto>();
	}
	
	@GetMapping("getInactiveQns/{type}/{keyword}")
	public Map<String, Object> getInactiveQns(@PathVariable String keyword, @PathVariable String type,@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return generalQuestionService.getInactiveQns(type, keyword);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}
	
	@GetMapping("/activeQuestionById/{type}/{id}")
	public Map<String, Object> activeQuestionById(@PathVariable String type, @PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		GeneralQuestionDto resultDto = new GeneralQuestionDto();
		try {

			return generalQuestionService.activeQuestionById(type,id);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return new HashMap<>();
		}
	}

}
