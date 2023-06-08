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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.SettingsDto;

import com.promantus.Assessment.Service.SettingsService;

/**
 * @author Promantus
 *
 */

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class SettingsController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);

	@Autowired
	SettingsService settingsService;

	public SettingsDto addSettings(@RequestBody SettingsDto settingsDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		SettingsDto resultDto = new SettingsDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

			// Settings
			if (settingsDto.getGenQns() == 0) {
				errorParam.append("General Qns Count");
			}

			if (settingsDto.getTechQns() == 0) {
				errorParam.append("General Qns Count");
			}
			

			if (settingsDto.getPassPercentage() == 0) {
				errorParam.append("Pass Percentage Required");
			}
			

			if (settingsDto.getFailPercentage() == 0) {
				errorParam.append("Fail Percentage Required");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = settingsService.addSettings(settingsDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}


	@GetMapping("/getAllSettings")
	public List<SettingsDto> getAllSettings(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return settingsService.getAllSettings();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<SettingsDto>();
	}
	
	@PutMapping("/updateSettings")
	public SettingsDto updateSettings(@RequestBody SettingsDto settingsDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		SettingsDto resultDto = new SettingsDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

			// Settings
				if (settingsDto.getGenQns() == 0) {
					errorParam.append("General Qns Count");
				}

				if (settingsDto.getTechQns() == 0) {
					errorParam.append("General Qns Count");
				}
				

				if (settingsDto.getPassPercentage() == 0) {
					errorParam.append("Pass Percentage Required");
				}
				

				if (settingsDto.getFailPercentage() == 0) {
					errorParam.append("Fail Percentage Required");
				}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = settingsService.updateSettings(settingsDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}
	
}
