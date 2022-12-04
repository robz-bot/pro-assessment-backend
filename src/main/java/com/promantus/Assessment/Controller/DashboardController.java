/**
 * 
 */
package com.promantus.Assessment.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.promantus.Assessment.Dto.WidgetDto;
import com.promantus.Assessment.Service.DashboardService;

/**
 * @author Promantus
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class DashboardController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	DashboardService dashboardService;

	@GetMapping("/")
	public String hello() {
		return "Application Started";
	}

	@GetMapping("/widgetData")
	public List<WidgetDto> widgetData(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return dashboardService.widgetData();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<WidgetDto>();
	}

	@GetMapping("/userAttemptsChart")
	public Map<Object, Object> userAttemptsChart(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return dashboardService.userAttemptsChart();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<Object, Object>();
	}

	@GetMapping("/datewisePassFail/{date}")
	public Map<Object, Object> datewisePassFail(@PathVariable String date,
			@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return dashboardService.datewisePassFail(date);

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<Object, Object>();
	}

	@GetMapping("/teamExamReadiness")
	public Map<Object, Object> teamExamReadiness(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return dashboardService.teamExamReadiness();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<Object, Object>();
	}

	@GetMapping("/questionsActiveInactive")
	public Map<Object, Object> questionsActiveInactive(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return dashboardService.questionsActiveInactive();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new HashMap<Object, Object>();
	}

}
