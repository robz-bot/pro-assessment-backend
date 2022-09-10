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

import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.ReportsDto;
import com.promantus.Assessment.Service.ReportsService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ReportsController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

	@Autowired
	private ReportsService reportsService;

	@PostMapping("/addReports")
	public ReportsDto addReports(@RequestBody ReportsDto reportsDto, @RequestHeader(name = "lang", required = false) String lang) {

		ReportsDto resultDto = new ReportsDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
			
			// User Id.
			if (reportsDto.getUserId() == null) {
				errorParam.append(errorParam.length() > 0 ? ", User Id" : "User Id");
			}
			
			// Status
			if (reportsDto.getStatus() == null || reportsDto.getStatus().isEmpty()) {
				errorParam.append("Status");
			}
			// Total No.Of Questions
			if (reportsDto.getTotalNoOfQuestions() == null) {
				errorParam.append("Total No.Of Questions");
			}
			//No.Of Questions Answered
			if (reportsDto.getNoOfQuestionsAnswered() == null) {
				errorParam.append("No.Of Questions Answered");
			}
			//No.Of Questions Not Answered
			if (reportsDto.getNoOfQuestionsNotAnswered() == null) {
				errorParam.append("No.Of Questions Not Answered ");
			}
			//Total Marks
			if (reportsDto.getTotalMarks() == null) {
				errorParam.append("Total Marks");
			}
			if (errorParam.length() > 0) {
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = reportsService.addReports(reportsDto, lang);

		} catch (final Exception e) {
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;	
}
		
	@GetMapping("/getAllReports")
	public List<ReportsDto> getAllReports(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return reportsService.getAllReports();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<ReportsDto>();
	}

	 
	@GetMapping("/getReportsById/{id}")
	public ReportsDto getReportsById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		ReportsDto reportsDto = new ReportsDto();
		try {
			reportsDto = reportsService.getReportsById(id);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return reportsDto;
	}
	

	@PutMapping("/updateReports")
	public ReportsDto updateReports(@RequestBody ReportsDto reportsDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		ReportsDto resultDto = new ReportsDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
	
				//UserId
				if (reportsDto.getUserId() == null) {
					errorParam.append(errorParam.length() > 0 ? ", User Id" : "User Id");
				}
			
				// Status
				if (reportsDto.getStatus() == null || reportsDto.getStatus().isEmpty()) {
					errorParam.append("Status");
				}
				
				
				// Total No.Of Questions
				if (reportsDto.getTotalNoOfQuestions() == null) {
					errorParam.append("Total No.Of Questions");
				}
				//No.Of Questions Answered
				if (reportsDto.getNoOfQuestionsAnswered() == null) {
					errorParam.append("No.Of Questions Answered");
				}
				//No.Of Questions Not Answered
				if (reportsDto.getNoOfQuestionsNotAnswered() == null) {
					errorParam.append("No.Of Questions Not Answered ");
				}
				//Total Marks
				if (reportsDto.getTotalMarks() == null) {
					errorParam.append("Total Marks");
				}
				

			if (errorParam.length() > 0) {
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = reportsService.updateReports(reportsDto, lang);

		} catch (final Exception e) {


			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@DeleteMapping("/deleteReportsById/{id}")
	public ReportsDto deleteReportsById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		ReportsDto resultDto = new ReportsDto();
		try {

			return reportsService.deleteReportsById(id);

		} catch (final Exception e) {

			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return resultDto;
		}
	}

	

}
