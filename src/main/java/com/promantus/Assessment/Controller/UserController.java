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
import com.promantus.Assessment.Dto.UserDto;
import com.promantus.Assessment.Service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class UserController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/addUser")
	public UserDto addUser(@RequestBody UserDto userDto, @RequestHeader(name = "lang", required = false) String lang) {

		UserDto resultDto = new UserDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
			// Email
			if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
				errorParam.append("Email");
			}
			// First Name
			if (userDto.getFirstName() == null || userDto.getLastName().isEmpty()) {
				errorParam.append("First Name");
			}
			// Last Name
			if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
				errorParam.append("Last Name");
			}
			// Employee Code.
			if (userDto.getEmpCode() == null || userDto.getEmpCode().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Employee Code" : "Employeee Code");
			}
			// Manager
			if (userDto.getManager() == null || userDto.getManager().isEmpty()) {
				errorParam.append("Manager");
			}
			// Team.
			if (userDto.getTeamId() == null) {
				errorParam.append("Team");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = userService.addUser(userDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@GetMapping("/getAllUsers")
	public List<UserDto> getAllUsers(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return userService.getAllUsers();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<UserDto>();
	}

	@GetMapping("/getUserById/{userId}")
	public UserDto getUserById(@PathVariable String userId,
			@RequestHeader(name = "lang", required = false) String lang) {

		UserDto userDto = new UserDto();
		try {
			userDto = userService.getUserById(userId);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return userDto;
	}

	@GetMapping("/getUserByEmail/{email}")
	public UserDto getUserByEmail(@PathVariable String email,
			@RequestHeader(name = "lang", required = false) String lang) {

		UserDto userDto = new UserDto();
		try {
			userDto = userService.getUserByEmail(email);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return userDto;
	}

	@PostMapping("/checkUserName")
	public Boolean checkUserName(@RequestBody String userName,
			@RequestHeader(name = "lang", required = false) String lang) {
		try {
			return userService.checkUserName(userName);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return true;
	}

	@PutMapping("/updateUser")
	public UserDto updateUser(@RequestBody UserDto userDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		UserDto resultDto = new UserDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

			// Email
			if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
				errorParam.append("Email");
			}
			// First Name
			if (userDto.getFirstName() == null || userDto.getLastName().isEmpty()) {
				errorParam.append("First Name");
			}
			// Last Name
			if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
				errorParam.append("Last Name");
			}
			// Employee Code.
			if (userDto.getEmpCode() == null || userDto.getEmpCode().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Employee Code" : "Employeee Code");
			}
			// Manager Name.
			if (userDto.getManager() == null || userDto.getManager().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Manager Name" : "Manager Name");
			}

			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = userService.updateUser(userDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@DeleteMapping("/deleteUserById/{userId}")
	public UserDto deleteUserById(@PathVariable String userId,
			@RequestHeader(name = "lang", required = false) String lang) {

		UserDto resultDto = new UserDto();
		try {

			return userService.deleteUserById(userId);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return resultDto;
		}
	}

}
