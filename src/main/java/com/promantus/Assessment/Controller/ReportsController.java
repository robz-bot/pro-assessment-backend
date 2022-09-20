package com.promantus.Assessment.Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.google.common.net.HttpHeaders;
import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.TwilioMethods;
import com.promantus.Assessment.Dto.ReportsDto;
import com.promantus.Assessment.Service.ReportsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ReportsController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

	@Autowired
	private ReportsService reportsService;

	@Value("${download.path}")
	private String downloadsPath;


	@PostMapping("/addReports")
	public ReportsDto addReports(@RequestBody ReportsDto reportsDto,
			@RequestHeader(name = "lang", required = false) String lang) {

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
			// No.Of Questions Answered
			if (reportsDto.getNoOfQuestionsAnswered() == null) {
				errorParam.append("No.Of Questions Answered");
			}
			// No.Of Questions Not Answered
			if (reportsDto.getNoOfQuestionsNotAnswered() == null) {
				errorParam.append("No.Of Questions Not Answered ");
			}
			// Total Marks
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

	@GetMapping("/getAllReportsPage")
	public Map<String, Object> getAllReportsPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestHeader(name = "lang", required = false) String lang) {

		Pageable paging = PageRequest.of(page, size, Sort.by("updatedOn").descending());
		try {

			return reportsService.getAllReportsPage(paging);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
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

			// UserId
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
			// No.Of Questions Answered
			if (reportsDto.getNoOfQuestionsAnswered() == null) {
				errorParam.append("No.Of Questions Answered");
			}
			// No.Of Questions Not Answered
			if (reportsDto.getNoOfQuestionsNotAnswered() == null) {
				errorParam.append("No.Of Questions Not Answered ");
			}
			// Total Marks
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

	@GetMapping("/searchByReport/{type}/{keyword}")
	public List<ReportsDto> search(@PathVariable String type, @PathVariable String keyword,
			@RequestHeader(name = "lang", required = false) String lang) {

		List<ReportsDto> reportsDto = new ArrayList<>();
		try {
			reportsDto = reportsService.search(type, keyword);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return reportsDto;
	}
	
	@GetMapping("/searchReportPage/{type}/{keyword}")
	public Map<String, Object> searchReportPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @PathVariable String keyword, @PathVariable String type,
			@RequestHeader(name = "lang", required = false) String lang) {

		Pageable paging = PageRequest.of(page, size, Sort.by("updatedOn").descending());

		try {
			return reportsService.searchReportPage(paging, type, keyword);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}

	@GetMapping("/searchByExamStartDate/{reportedOn}")
	public List<ReportsDto> searchByExamStartDate(@PathVariable String reportedOn,
			@RequestHeader(name = "lang", required = false) String lang) {

		List<ReportsDto> reportsDto = new ArrayList<>();
		try {
			reportsDto = reportsService.searchByExamStartDate(reportedOn);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return reportsDto;
	}

	@GetMapping("/searchByStatus/{status}")
	public List<ReportsDto> searchByStatus(@PathVariable String status,
			@RequestHeader(name = "lang", required = false) String lang) {

		List<ReportsDto> reportsDto = new ArrayList<>();
		try {
			return reportsService.searchByStatus(status);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}
		return reportsDto;
	}

	@GetMapping("/searchByPercentage/{percentage}")
	public List<ReportsDto> searchByPercentage(@PathVariable String percentage,
			@RequestHeader(name = "lang", required = false) String lang) {

		List<ReportsDto> reportsDto = new ArrayList<>();
		try {
			reportsDto = reportsService.searchByPercentage(percentage);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}
		return reportsDto;
	}

	@PutMapping("/downloadReports")
	public void downloadReports(@RequestBody List<ReportsDto> reportsDtoList,
			@RequestHeader(name = "lang", required = false) String lang, HttpServletResponse response) {

		BufferedInputStream inStream = null;
		BufferedOutputStream outStream = null;
		try {

			File assessmentReportsFile = new File(downloadsPath + "Assessment_Reports.xlsx");
			FileUtils.writeByteArrayToFile(assessmentReportsFile, reportsService.downloadReports(reportsDtoList, lang));

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment;filename=" + assessmentReportsFile.getName());
			response.setContentLength((int) assessmentReportsFile.length());

			inStream = new BufferedInputStream(new FileInputStream(assessmentReportsFile));
			outStream = new BufferedOutputStream(response.getOutputStream());

			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			response.flushBuffer();
			assessmentReportsFile.deleteOnExit();

		} catch (final Exception e) {

			logger.error(AssessmentUtil.getErrorMessage(e));

		} finally {
			try {
				if (outStream != null) {
					outStream.flush();
				}
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				logger.error(AssessmentUtil.getErrorMessage(e));
			}
		}
	}

	@GetMapping(value = "/sendSMS")
	public ResponseEntity<String> sendSMS() {

		Twilio.init(AssessmentConstants.ACCOUNT_SID, AssessmentConstants.AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber("+918526774450"), new PhoneNumber("+18156271503"),
				"Pro Assessment Results - 25/30. Pass").create();

		System.out.print(message.getSid().toString());

		return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
	}

	@GetMapping(value = "/validatePhnNumber/{number}")
	public boolean validatePhnNumber(@PathVariable String number) {

		return TwilioMethods.verifyPhnNumber(number);
	}
}
