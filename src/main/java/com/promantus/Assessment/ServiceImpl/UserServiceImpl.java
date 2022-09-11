package com.promantus.Assessment.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.UserDto;
import com.promantus.Assessment.Entity.GeneralQuestion;
import com.promantus.Assessment.Entity.Reports;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.TechQuestion;
import com.promantus.Assessment.Entity.User;
import com.promantus.Assessment.Repository.GeneralQuestionRepository;
import com.promantus.Assessment.Repository.ReportsRepository;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Repository.TechQuestionRepository;
import com.promantus.Assessment.Repository.UserRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	TechQuestionRepository techQuestionRepository;

	@Autowired
	GeneralQuestionRepository generalQuestionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommonService commonService;

	@Autowired
	ReportsRepository reportsRepository;

	@Autowired
	TeamRepository teamRepository;

	@Override
	public Boolean checkUserName(String userName) throws Exception {

		if (userRepository.findByEmailIgnoreCase(userName) != null) {
			return true;
		}

		return false;
	}

	@Override
	public UserDto addUser(final UserDto userDto, String lang) throws Exception {

		UserDto resultDto = new UserDto();

		List<TechQuestion> listA = new ArrayList<TechQuestion>();
		listA = techQuestionRepository.findByTeamId(userDto.getTeamId());

		List<GeneralQuestion> listB = new ArrayList<GeneralQuestion>();
		listB = generalQuestionRepository.findAll();

		if (listA.size() > 0 && listB.size() >= 5 && listA.size() >= 25) {

			User loginUser = userRepository.findByEmail(userDto.getEmail());
			if (loginUser == null) {
				Long currentUserId = commonService.nextSequenceNumber();
				User user = new User();
				
				user.setId(currentUserId);
				user.setFirstName(userDto.getFirstName());
				user.setLastName(userDto.getLastName());
				user.setEmail(userDto.getEmail());
				user.setPassword(AssessmentUtil.encrypt(AssessmentUtil.generateUUID(7)));
				user.setEmpCode(userDto.getEmpCode());
				user.setManager(userDto.getManager());
				user.setTeamId(userDto.getTeamId());
				user.setAttempts(userDto.getAttempts());
				user.setRegisteredOn(LocalDateTime.now());

				userRepository.save(user);
				resultDto.setId(currentUserId);
			} else if (loginUser.getAttempts() != 0) {

				Reports repObj = reportsRepository.findByUserId(loginUser.getId());
				LocalDateTime reportAttemptDateTime = repObj.getReportedOn().plusDays(7);
				System.out.println(reportAttemptDateTime);
				if (reportAttemptDateTime.isEqual(LocalDateTime.now())
						|| reportAttemptDateTime.isBefore(LocalDateTime.now())) {
					resultDto = getUserDto(loginUser);
					resultDto.setMessage("You are registered to the assessment");

					return resultDto;
				} else {

					resultDto = getUserDto(loginUser);
					resultDto.setStatus(1);
					resultDto.setMessage("You have couple of days to attend the test, thanks for your interest");
					return resultDto;
				}
			}
			resultDto.setId(loginUser.getId());
			resultDto.setMessage("Assessment Started");
			return resultDto;
		} else {
			resultDto.setStatus(1);
			resultDto.setMessage("Something went wrong Contact admin");
			return resultDto;
		}

	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> UsersList = userRepository.findAll();

		List<UserDto> UserDtoList = new ArrayList<UserDto>();
		for (User User : UsersList) {
			UserDtoList.add(this.getUserDto(User));
		}

		return UserDtoList;
	}

	private UserDto getUserDto(User user) {
		UserDto userDto = new UserDto();

		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setEmpCode(user.getEmpCode());
		userDto.setTeamId(user.getTeamId());
		userDto.setManager(user.getManager());
		userDto.setAttempts(user.getAttempts());
		userDto.setRegisteredOn(user.getRegisteredOn());
		Team team = teamRepository.findById(user.getTeamId());
		userDto.setTeam(team.getTeam());
		return userDto;

	}

	@Override
	public UserDto updateUser(UserDto userDto, String lang) {

		UserDto resultDto = new UserDto();
		System.out.println(userDto.getId());
		User user = userRepository.findById(userDto.getId());

		if (user == null) {

			resultDto.setMessage("User does not exist");
			return resultDto;
		}
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setEmpCode(userDto.getEmpCode());
		user.setTeamId(userDto.getTeamId());
		user.setManager(userDto.getManager());
		user.setAttempts(userDto.getAttempts());
		user.setRegisteredOn(userDto.getRegisteredOn());
		user.setUpdatedOn(LocalDateTime.now());

		userRepository.save(user);
		resultDto.setMessage("Record Updated Successfully");
		return resultDto;

	}

	@Override
	public UserDto deleteUserById(String userId) {
		UserDto resultDto = new UserDto();
		User user = userRepository.findById(Long.parseLong(userId));
		if (user == null) {

			resultDto.setMessage("data does not exist");
			return resultDto;
		}

		userRepository.delete(user);
		resultDto.setMessage("Record Deleted Successfully");
		return resultDto;
	}

	@Override
	public UserDto getUserById(String userId) throws Exception {

		User user = userRepository.findById(Long.parseLong(userId));

		return user != null ? this.getUserDto(user) : new UserDto();

	}

	@Override
	public UserDto getUserByEmail(String email) throws Exception {
		User user = userRepository.findByEmail(email);
		UserDto userDto = new UserDto();
		if(user == null) {
			userDto.setStatus(1);
		}

		return user != null ? this.getUserDto(user) : userDto;
	}

}
