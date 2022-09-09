package com.promantus.Assessment.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.ExamDto;
import com.promantus.Assessment.Service.ExamService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ExamController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	ExamService examService;

	@GetMapping("/getExamQns/{teamId}/{userId}")
	public List<ExamDto> getExamQns(@PathVariable String teamId ,@PathVariable String userId,@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return examService.getExamQns(teamId,userId);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<ExamDto>();
	}

}

