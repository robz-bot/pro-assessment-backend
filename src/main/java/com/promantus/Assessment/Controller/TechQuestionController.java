package com.promantus.Assessment.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.TechQuestionDto;
import com.promantus.Assessment.Service.TechQuestionService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class TechQuestionController extends CommonController{

	private static final Logger logger = LoggerFactory.getLogger(TechQuestionController.class);

	@Autowired
	private TechQuestionService techQuestionService;


	@PostMapping("/addTechQuestion")
	public TechQuestionDto addTechQuestion(@RequestBody TechQuestionDto techQuestionDto, @RequestHeader(name = "lang", required = false) String lang) {

		TechQuestionDto resultDto = new TechQuestionDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
			
			//TechQuestion
			if (techQuestionDto.getTeamId() == null ){
				errorParam.append("Team Id Missing");
			}
			if (techQuestionDto.getQuestion() == null ||techQuestionDto.getQuestion().isEmpty()){
				errorParam.append("Question is Missing");
			}
			if (techQuestionDto.getOption1() == null ||techQuestionDto.getOption1().isEmpty()){
				errorParam.append("Option1 are Missing");
			}
			if (techQuestionDto.getOption2() == null ||techQuestionDto.getOption2().isEmpty()){
				errorParam.append("Option2 are Missing");
			}
			if (techQuestionDto.getOption3() == null ||techQuestionDto.getOption3().isEmpty()){
				errorParam.append("Option3 are Missing");
			}
			if (techQuestionDto.getOption4() == null ||techQuestionDto.getOption4().isEmpty()){
				errorParam.append("Option4 are Missing");
			}
			if (techQuestionDto.getAnswer() == null ||techQuestionDto.getAnswer().isEmpty()){
				errorParam.append("Answer is Missing");
			}
			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = techQuestionService.addTechQuestion(techQuestionDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@GetMapping("/getAllTechQuestions")
	public List<TechQuestionDto> getAllTechQuestions(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return techQuestionService.getAllTechQuestions();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<TechQuestionDto>();
	}
	@GetMapping("/getTechQuestionById/{id}")
	public TechQuestionDto getTechQuestionById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		TechQuestionDto techQuestionDto = new TechQuestionDto();
		try {
			techQuestionDto = techQuestionService.getTechQuestionById(id);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return techQuestionDto;
	}
	@PutMapping("/updateTechQuestion")
	public TechQuestionDto updateTechQuestion(@RequestBody TechQuestionDto techQuestionDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		TechQuestionDto resultDto = new TechQuestionDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
	
				//Email
			if (techQuestionDto.getTeamId() == null ){
				errorParam.append("Team Id Missing");
			}
			if (techQuestionDto.getQuestion() == null ||techQuestionDto.getQuestion().isEmpty()){
				errorParam.append("Question is Missing");
			}
			if (techQuestionDto.getOption1() == null ||techQuestionDto.getOption1().isEmpty()){
				errorParam.append("Option1 are Missing");
			}
			if (techQuestionDto.getOption2() == null ||techQuestionDto.getOption2().isEmpty()){
				errorParam.append("Option2 are Missing");
			}
			if (techQuestionDto.getOption3() == null ||techQuestionDto.getOption3().isEmpty()){
				errorParam.append("Option3 are Missing");
			}
			if (techQuestionDto.getOption4() == null ||techQuestionDto.getOption4().isEmpty()){
				errorParam.append("Option4 are Missing");
			}
			if (techQuestionDto.getAnswer() == null ||techQuestionDto.getAnswer().isEmpty()){
				errorParam.append("Answer is Missing");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = techQuestionService.updateTechQuestion(techQuestionDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@DeleteMapping("/deleteTechQuestionById/{id}")
	public TechQuestionDto deleteTechQuestionById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		TechQuestionDto resultDto = new TechQuestionDto();
		try {

			return techQuestionService.deleteTechQuestionById(id);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return resultDto;
		}
	}
}
