package com.promantus.Assessment.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentDefaultMethods;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.SmtpMailSender;
import com.promantus.Assessment.Dto.GeneralQuestionDto;
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

	@Autowired
	TechQuestionRepository techQuestionRepository;

	@Autowired
	GeneralQuestionRepository generalQuestionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommonService commonService;

	@Autowired
	SmtpMailSender smtpMailSender;

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
	public UserDto addUser(final UserDto userDto, String fromAlreadyAppeared, String lang) throws Exception {

		UserDto resultDto = new UserDto();

		List<User> userByEmpCode = userRepository.findAllByEmpCode(userDto.getEmpCode());

		if (fromAlreadyAppeared.equals("false")) {
			if (userByEmpCode.size() > 0) {
				resultDto.setStatus(1);
				resultDto.setMessage("Employee Code already exists");
				return resultDto;
			}
		}

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
				user.setisActive(true);
				user.setUpdatedOn(LocalDateTime.now());

//				user.setCode(userDto.getCode());
//				user.setPhnNumber(userDto.getPhnNumber());

				userRepository.save(user);

				user = userRepository.findById(currentUserId);

				UserDto getUser = this.getUserDto(user);

				// Mail Thread
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							smtpMailSender.sendUserMail(getUser);
						} catch (Exception e) {

							System.out.println("Email for Interview Scheduled is not Sent.");
							System.err.println(e);
						}
					}
				}).start();

//				String phnNumber = userDto.getCode() + userDto.getPhnNumber();

//				TwilioSMS.sendSMS(phnNumber);

				resultDto.setId(currentUserId);
				resultDto.setStatus(0);
				resultDto.setMessage("Assessment Started");
				return resultDto;
			} else if (loginUser.getAttempts() != 0) {

				List<Reports> repObj = reportsRepository.findByUserId(loginUser.getId());
				if (repObj.size() > 0) {
					Comparator<Reports> reportComparator = Comparator.comparing(Reports::getReportedOn);
					Reports repMax = repObj.stream().max(reportComparator).get();
					// System.out.println(repMax.getReportedOn());
					LocalDateTime reportAttemptDateTime = repMax.getReportedOn().plusDays(7);
					// System.out.println(reportAttemptDateTime);
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
				} else {
					resultDto.setStatus(1);
					resultDto.setMessage("Something went wrong Contact admin");
					return resultDto;
				}
			}
			resultDto = getUserDto(loginUser);
			// resultDto.setId(loginUser.getId());
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
		List<User> UsersList = userRepository.findAllByIsActive(true, AssessmentUtil.orderByUpdatedOnDesc());

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
		userDto.setisActive(user.getisActive());
		userDto.setUpdatedBy(user.getUpdatedBy());
		userDto.setUpdatedOn(user.getUpdatedOn());
		if (user.getTeamId() != null) {
			Team team = teamRepository.findById(user.getTeamId());
			userDto.setTeam(team.getTeam());
		}
		userDto.setUserName(user.getFirstName() + " " + user.getLastName());
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
		user.setUpdatedBy(userDto.getUpdatedBy());
		user.setisActive(true);

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
		if (user == null) {
			userDto.setStatus(1);
		}

		return user != null ? this.getUserDto(user) : userDto;
	}

	@Override
	public Map<String, Object> getAllUsersPage(Pageable paging) throws Exception {
		paging.getSort();
		Page<User> userPage = userRepository.findAllByIsActive(true, paging);
		List<UserDto> resultDto = new ArrayList<>();
		List<User> userList = userPage.getContent();
		for (User User : userList) {
			resultDto.add(this.getUserDto(User));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("userList", resultDto);
		response.put("currentPage", userPage.getNumber());
		response.put("totalItems", userPage.getTotalElements());
		response.put("totalPages", userPage.getTotalPages());
		response.put("totalRecords", userPage.getTotalPages());
		return response;
	}

	@Override
	public Map<String, Object> searchUserPage(Pageable paging, String type, String keyword) throws Exception {
		List<UserDto> resultDto = new ArrayList<>();
		List<User> userList = new ArrayList<>();

		Page<User> userPage = null;
		keyword = AssessmentDefaultMethods.replaceSplCharKeyword(keyword);

		// username
		if (type.equals(AssessmentConstants.TYPE5)) {
//			keyword = keyword.replace("+", "\\+").replace("$", "\\$").replace("^", "\\^").replace("{", "\\{");
			userPage = userRepository.findByFirstNameLastNameRegex(keyword, true, paging);
			userList = userPage.getContent();
		}
		// empcode
		if (type.equals(AssessmentConstants.TYPE10)) {

			userPage = userRepository.getAllEmpCodeRegex(keyword, true, paging);

			userList = userPage.getContent();
		}
		// email
		if (type.equals(AssessmentConstants.TYPE11)) {
			keyword  = keyword.split("@")[0];
			userPage = userRepository.getAllEmailRegex(keyword, true, paging);
			userList = userPage.getContent();
		}
		// manager
		if (type.equals(AssessmentConstants.TYPE12)) {
			userPage = userRepository.getAllManagerRegex(keyword, true, paging);
			userList = userPage.getContent();
		}
		// team
		if (type.equals(AssessmentConstants.TYPE4)) {
			userPage = userRepository.findAllByTeamId(Long.parseLong(keyword), paging);
			userList = userPage.getContent();
		}
		// attempts
		if (type.equals(AssessmentConstants.TYPE9)) {
			userPage = userRepository.findAllByAttempts(Integer.parseInt(keyword), true, paging);
			userList = userPage.getContent();
		}

		for (User user : userList) {
			resultDto.add(getUserDto(user));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("userList", resultDto);
		response.put("currentPage", userPage.getNumber());
		response.put("totalItems", userPage.getTotalElements());
		response.put("totalPages", userPage.getTotalPages());
		response.put("totalRecords", userPage.getTotalPages());
		return response;
	}

}
