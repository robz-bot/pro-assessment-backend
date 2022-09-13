package com.promantus.Assessment.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.UserDto;
import com.promantus.Assessment.Service.LoginService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class LoginController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public UserDto login(@RequestBody(required = true) UserDto userDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		UserDto resultDto = new UserDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
			// User Name.
			if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
				errorParam.append("User Name");
			}
			// Password.
			if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Password" : "Password");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			if (userDto.getEmail() == null) {
				resultDto = loginService.login(userDto.getEmail(), userDto.getPassword(), lang);
			}
		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
			return resultDto;
		}
		resultDto.setMessage("Login successfully");
		return resultDto;
	}

	@PutMapping("/updateProfile")
	public UserDto updateProfile(@RequestBody UserDto userDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		UserDto resultDto = new UserDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
			// User Id.
			if (userDto.getId() == 0) {
				errorParam.append("User Id");
			}
			// User Name.
			if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", User Name" : "User Name");
			}
			// First Name.
			if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", First Name" : "First Name");
			}
			// Last Name.
			if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Last Name" : "Last Name");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = loginService.updateProfile(userDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
			return resultDto;
		}

		return resultDto;
	}

	/**
	 * @param userDto
	 * @return
	 */
	@PutMapping("/changePassword")
	public UserDto changePassword(@RequestBody UserDto userDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		UserDto resultDto = new UserDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
			// User Id.
			if (userDto.getId() == 0) {
				errorParam.append("User Id");
			}
			// Current Password.
			if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Current Password" : "Current Password");
			}
			// New Password.
			if (userDto.getNewPassword() == null || userDto.getNewPassword().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", New Password" : "New Password");
			}
			// Current Password AND New Password.
			if (!(userDto.getPassword() == null || userDto.getPassword().isEmpty())
					&& !(userDto.getNewPassword() == null || userDto.getNewPassword().isEmpty())
					&& userDto.getPassword().equalsIgnoreCase(userDto.getNewPassword())) {
				errorParam.append(errorParam.length() > 0 ? ", Current Password and New Password are SAME"
						: "Current Password and New Password are SAME");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = loginService.changePassword(userDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
			return resultDto;
		}

		return resultDto;
	}

	/**
	 * @param userDto
	 * @return
	 */
	@PutMapping("/resetPassword")
	public UserDto resetPassword(@RequestBody UserDto userDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		UserDto resultDto = new UserDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
			// Email.
			if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
				errorParam.append("Email");
			}
			// New Password.
			if (userDto.getNewPassword() == null || userDto.getNewPassword().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", New Password" : "New Password");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = loginService.resetPassword(userDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
			return resultDto;
		}

		return resultDto;
	}
}
