package com.promantus.Assessment.ServiceImpl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.UserDto;
import com.promantus.Assessment.Entity.User;
import com.promantus.Assessment.Repository.UserRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	CommonService commonService;

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto login(final String userName, final String password, final String lang) throws Exception {

		UserDto resultDto = new UserDto();

		User user = userRepository.findByEmail(userName);

		if (user == null) {
			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(commonService.getMessage("invalid", new String[] { "User Name" }, lang));

			logger.info(resultDto.getMessage());
			return resultDto;
		}

		if (!password.equals(user.getPassword())) { 
			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(commonService.getMessage("invalid", new String[] { "Password" }, lang));

			logger.info(resultDto.getMessage());
			return resultDto;
		}

		resultDto.setId(user.getId());
		resultDto.setEmail(user.getEmail());
		resultDto.setFirstName(user.getFirstName());
		resultDto.setLastName(user.getLastName());
		resultDto.setEmpCode(user.getEmpCode());
		resultDto.setAttempts(user.getAttempts());
		resultDto.setManager(user.getManager());
		resultDto.setTeamId(user.getTeamId());
	
		resultDto.setStatus(AssessmentConstants.RETURN_STATUS_OK);
		resultDto.setMessage("Login Successfully");

		return resultDto;
	}

	@Override
	public UserDto updateProfile(final UserDto userDto, final String lang) throws Exception {

		UserDto resultDto = new UserDto();

		User user = userRepository.findById(userDto.getId());

		if (user == null) {
			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(commonService.getMessage("invalid", new String[] { "User Id" }, lang));

			logger.info(resultDto.getMessage());
			return resultDto;
		}
        
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmpCode(userDto.getEmpCode());
		user.setAttempts(userDto.getAttempts());
		user.setManager(userDto.getManager());
		user.setTeamId(userDto.getTeamId());
		
		userRepository.save(user);

		resultDto.setStatus(AssessmentConstants.RETURN_STATUS_OK);
		resultDto.setMessage("Updated Successfully");
		return resultDto;
	}

	@Override
	public UserDto changePassword(final UserDto userDto, String lang) throws Exception {

		UserDto resultDto = new UserDto();

		User user = userRepository.findById(userDto.getId());

		if (user == null) {
			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(commonService.getMessage("invalid", new String[] { "User Id" }, lang));

			logger.info(resultDto.getMessage());
			return resultDto;
		}
		if (!AssessmentUtil.decrypt(user.getPassword()).equals(userDto.getPassword())) {
			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(commonService.getMessage("invalid", new String[] { "Current Password" }, lang));

			logger.info(resultDto.getMessage());
			return resultDto;
		}
		if (AssessmentUtil.decrypt(user.getPassword()).equalsIgnoreCase(userDto.getNewPassword())) {
			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(
					commonService.getMessage("password.invalid", new String[] { userDto.getNewPassword() }, lang));

			logger.info(resultDto.getMessage());
			return resultDto;
		}


		user.setPassword(userDto.getNewPassword());
		user.setUpdatedOn(LocalDateTime.now());

		userRepository.save(user);

		resultDto.setStatus(AssessmentConstants.RETURN_STATUS_OK);
		resultDto.setMessage("Password Updated Successfully");
		return resultDto;
	}


	@Override
	public UserDto resetPassword(final UserDto userDto, String lang) throws Exception {

		UserDto resultDto = new UserDto();

		User user = userRepository.findByEmailIgnoreCase(userDto.getEmail());

		if (user == null) {
			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(commonService.getMessage("invalid", new String[] { "Email" }, lang));

			logger.info(resultDto.getMessage());
			return resultDto;
		}

		user.setPassword(userDto.getNewPassword());

		user.setUpdatedBy(userDto.getUpdatedBy());
		user.setUpdatedOn(LocalDateTime.now());

		userRepository.save(user);
		resultDto.setStatus(AssessmentConstants.RETURN_STATUS_OK);
		resultDto.setMessage("Password Reset Successfully");
		return resultDto;
	}
}
