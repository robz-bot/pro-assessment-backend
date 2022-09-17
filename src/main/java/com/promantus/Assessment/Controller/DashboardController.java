/**
 * 
 */
package com.promantus.Assessment.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/widgetData")
	public List<WidgetDto> widgetData(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return dashboardService.widgetData();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<WidgetDto>();
	}

}
