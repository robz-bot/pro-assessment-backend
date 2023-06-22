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
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.ProgReportsDto;
import com.promantus.Assessment.Dto.ReportsDto;
import com.promantus.Assessment.Entity.Reports;
import com.promantus.Assessment.Service.ProgReportsService;



@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ProgReportsController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(ProgReportsController.class);

	@Autowired
	private ProgReportsService progReportsService;

	@Value("${download.path}")
	private String downloadsPath;

	
	@PostMapping("/addProgReports")
	public ProgReportsDto addProgReports(@RequestBody List<ProgReportsDto> progReportsDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		ProgReportsDto resultDto = new ProgReportsDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

//			// User Id.
//			if (progReportsDto.getUserId() == null) {
//				errorParam.append(errorParam.length() > 0 ? ", User Id" : "User Id");
//			}
//			// Question
//			if (progReportsDto.getQuestion() == null) {
//				errorParam.append("Question");
//			}
//			// Answer from User
//			if (progReportsDto.getAnswer() == null) {
//				errorParam.append("Answer");
//			}
//			// Level
//			if (progReportsDto.getLevel() == null) {
//				errorParam.append("Level");
//			}
			
			if (errorParam.length() > 0) {
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = progReportsService.addProgReports(progReportsDto, lang);

		} catch (final Exception e) {
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}
	
	@GetMapping("/getAllProgReports")
	public List<ProgReportsDto> getAllProgReports(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return progReportsService.getAllProgReports();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<ProgReportsDto>();
	}
	

	@GetMapping("/getAllProgReportsPage")
	public Map<String, Object> getAllProgReportsPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestHeader(name = "lang", required = false) String lang) {

		Pageable paging = PageRequest.of(page, size, Sort.by("updatedOn").descending());
		try {

			return progReportsService.getAllProgReportsPage(paging);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<String, Object>();
	}
	

	@GetMapping("/getProgReportsById/{id}")
	public ProgReportsDto getProgReportsById(@PathVariable String id,
			@RequestHeader(name = "lang", required = false) String lang) {

		ProgReportsDto ProgReportsDto = new ProgReportsDto();
		try {
			ProgReportsDto = progReportsService.getProgReportsById(id);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return ProgReportsDto;
	}
	

	@PutMapping("/updateProgReports")
	public ProgReportsDto updateProgReports(@RequestBody ProgReportsDto progReportsDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		ProgReportsDto resultDto = new ProgReportsDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

		
			//Scored Marks
			if (progReportsDto.getScoredMark() == null) {
				errorParam.append("Question");
			}
			//Remarks
			if (progReportsDto.getRemarks()== null) {
				errorParam.append("Answer");
			}
			

			if (errorParam.length() > 0) {
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = progReportsService.updateProgReports(progReportsDto, lang);

		} catch (final Exception e) {

			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@GetMapping("/searchByProgReport/{type}/{keyword}")
	public List<ProgReportsDto> searchProg(@PathVariable String type, @PathVariable String keyword,
			@RequestHeader(name = "lang", required = false) String lang) {

		List<ProgReportsDto> progReportsDto = new ArrayList<>();
		try {
			progReportsDto = progReportsService.searchProg(type, keyword);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return progReportsDto;
	}
	

	
	@PutMapping("/downloadProgReports")
	public void downloadProgReports(@RequestBody List<ProgReportsDto> progReportsDtoList,
			@RequestHeader(name = "lang", required = false) String lang, HttpServletResponse response) {

		BufferedInputStream inStream = null;
		BufferedOutputStream outStream = null;
		try {

			File assessmentProgReportsFile = new File(downloadsPath + "Program_Assessment_Reports.xlsx");
			FileUtils.writeByteArrayToFile(assessmentProgReportsFile, progReportsService.downloadProgReports(progReportsDtoList, lang));

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment;filename=" + assessmentProgReportsFile.getName());
			response.setContentLength((int) assessmentProgReportsFile.length());

			inStream = new BufferedInputStream(new FileInputStream(assessmentProgReportsFile));
			outStream = new BufferedOutputStream(response.getOutputStream());

			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			response.flushBuffer();
			assessmentProgReportsFile.deleteOnExit();

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
	
	@PostMapping("/getProgByReportedOn/{type}")
	public List<ProgReportsDto> getProgByReportedOn(@RequestBody ProgReportsDto progReportsDto,
			@PathVariable String type) throws Exception {
		
		List<ProgReportsDto> ProgReportsDto = new ArrayList<ProgReportsDto>();
		try {
			ProgReportsDto = progReportsService.getProgByReportedOn(progReportsDto,type);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return ProgReportsDto;
	}

}
