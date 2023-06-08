package com.promantus.Assessment.ServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentDefaultMethods;
import com.promantus.Assessment.SmtpMailSender;
import com.promantus.Assessment.Dto.ReportsDto;
import com.promantus.Assessment.Dto.UserDto;
import com.promantus.Assessment.Entity.Reports;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.User;
import com.promantus.Assessment.Repository.ReportsRepository;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Repository.UserRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.ReportsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class ReportsServiceImpl implements ReportsService {

	private static final Logger logger = LoggerFactory.getLogger(ReportsServiceImpl.class);

	@Autowired
	ReportsRepository reportsRepository;

	@Autowired
	CommonService commonService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	SmtpMailSender smtpMailSender;

	@Autowired
	ResourceLoader resourceLoader;

	@Override
	public ReportsDto addReports(ReportsDto reportsDto, String lang) throws Exception {

		ReportsDto resultDto = new ReportsDto();
		User repUser = userRepository.findById(reportsDto.getUserId());

		Reports reports = new Reports();
		reports.setId(commonService.nextSequenceNumber());
		reports.setTeamId(reportsDto.getTeamId());
		reports.setUserId(reportsDto.getUserId());
		reports.setPercentage(reportsDto.getPercentage());
		reports.setStatus(reportsDto.getStatus());
		reports.setTotalNoOfQuestions(reportsDto.getTotalNoOfQuestions());
		reports.setNoOfQuestionsAnswered(reportsDto.getNoOfQuestionsAnswered());
		reports.setNoOfQuestionsNotAnswered(reportsDto.getNoOfQuestionsNotAnswered());
		reports.setReportedOn(LocalDateTime.now());
		reports.setTotalMarks(reportsDto.getTotalMarks());
		reports.setisActive(true);
		reports.setAttempts(repUser.getAttempts() + 1);

		repUser.setAttempts(repUser.getAttempts() + 1);
		userRepository.save(repUser);
		reportsRepository.save(reports);
		resultDto.setMessage("Reports added successfully");

//		Twilio.init(AssessmentConstants.ACCOUNT_SID, AssessmentConstants.AUTH_TOKEN);

//		Message.creator(new PhoneNumber(repUser.getPhnNumber()), new PhoneNumber("+18156271503"),
//				"Pro Assessment Results - " + reports.getTotalMarks() + "/30. " + reports.getStatus()).create();
		
		User user = userRepository.findById(reportsDto.getUserId());
		Team team = teamRepository.findById(Long.parseLong(reportsDto.getTeamId()));
		
		// Mail Thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					smtpMailSender.sendUserResMail(reportsDto,user,team);
				} catch (Exception e) {

					System.out.println("Email for User Result is not Sent.");
					System.err.println(e);
				}
			}
		}).start();

		return resultDto;
	}

	@Override
	public List<ReportsDto> getAllReports() throws Exception {
		List<Reports> ReportsList = reportsRepository.findAll();

		List<ReportsDto> ReportsDtoList = new ArrayList<ReportsDto>();
		for (Reports Reports : ReportsList) {
			ReportsDtoList.add(this.getReportsDto(Reports));
		}

		return ReportsDtoList;
	}

	private ReportsDto getReportsDto(Reports reports) {
		ReportsDto reportsDto = new ReportsDto();

		reportsDto.setId(reports.getId());
		reportsDto.setUserId(reports.getUserId());
		reportsDto.setPercentage(reports.getPercentage());
		reportsDto.setTotalNoOfQuestions(reports.getTotalNoOfQuestions());
		reportsDto.setNoOfQuestionsAnswered(reports.getNoOfQuestionsAnswered());
		reportsDto.setNoOfQuestionsNotAnswered(reports.getNoOfQuestionsNotAnswered());
		reportsDto.setStatus(reports.getStatus());
		reportsDto.setReportedOn(reports.getReportedOn());
		reportsDto.setTotalMarks(reports.getTotalMarks());
		reportsDto.setisActive(reports.getisActive());
		reportsDto.setUpdatedOn(reports.getUpdatedOn());
		reportsDto.setUpdatedBy(reports.getUpdatedBy());
		if (reports.getUserId() != null) {
			System.err.println(reports.getUserId());
			int attempts = userRepository.findById(reports.getUserId()).getAttempts();

			reportsDto.setAttempts(attempts);
		}

		if (reports.getTeamId() != null) {
			Team team = teamRepository.findById(Long.parseLong(reports.getTeamId()));
			if (team != null) {
				reportsDto.setTeamName(team.getTeam());
			}
		}

		if (reports.getUserId() != null) {
			User user = userRepository.findById(reports.getUserId());
			if (user != null) {
				reportsDto.setUserName(user.getFirstName() + " " + user.getLastName());
			}
		}

		return reportsDto;

	}

	@Override
	public ReportsDto updateReports(ReportsDto reportsDto, String lang) throws Exception {

		ReportsDto resultDto = new ReportsDto();
		System.out.println(reportsDto.getId());
		Reports reports = reportsRepository.findById(reportsDto.getId());
		if (reports == null) {
			resultDto.setMessage("Reports does not exist");
			return resultDto;
		}
		reports.setUserId(reportsDto.getUserId());
		reports.setPercentage(reportsDto.getPercentage());
		reports.setTotalNoOfQuestions(reportsDto.getTotalNoOfQuestions());
		reports.setNoOfQuestionsAnswered(reportsDto.getNoOfQuestionsAnswered());
		reports.setNoOfQuestionsNotAnswered(reportsDto.getNoOfQuestionsNotAnswered());
		reports.setStatus(reportsDto.getStatus());
		reports.setReportedOn(reportsDto.getReportedOn());
		reports.setUpdatedOn(LocalDateTime.now());
		reports.setUpdatedBy(reportsDto.getUpdatedBy());
		reports.setisActive(true);
		reportsRepository.save(reports);
		resultDto.setMessage("Record Updated successfully");
		return resultDto;

	}

	@Override
	public ReportsDto deleteReportsById(String id) throws Exception {
		ReportsDto resultDto = new ReportsDto();
		Reports reports = reportsRepository.findById(Long.parseLong(id));
		if (reports == null) {
			resultDto.setMessage("data does not exist");
			return resultDto;
		}
		reportsRepository.delete(reports);
		resultDto.setMessage("Record Deleted successfully");
		return resultDto;
	}

	@Override
	public ReportsDto getReportsById(String id) throws Exception {
		Reports reports = reportsRepository.findById(Long.parseLong(id));
		return reports != null ? this.getReportsDto(reports) : new ReportsDto();
	}

	@Override
	public List<ReportsDto> searchByExamStartDate(String reportedOn) throws Exception {

		List<ReportsDto> resultDto = new ArrayList<>();
		List<Reports> reports = reportsRepository.findByReportedOnRegex(reportedOn);
		for (Reports reports2 : reports) {
			resultDto.add(getReportsDto(reports2));
		}
		return resultDto;
	}

	@Override
	public List<ReportsDto> searchByStatus(String status) throws Exception {

		List<ReportsDto> ReportsDtoList = new ArrayList<ReportsDto>();
		List<Reports> ReportsList = reportsRepository.findByStatus(status);

		for (Reports Reports : ReportsList) {
			ReportsDtoList.add(this.getReportsDto(Reports));
		}
		return ReportsDtoList;
	}

	@Override
	public List<ReportsDto> searchByPercentage(String percentage) throws Exception {

		List<ReportsDto> ReportsDtoList = new ArrayList<ReportsDto>();
		List<Reports> ReportsList = reportsRepository.findByPercentage(percentage);

		for (Reports Reports : ReportsList) {
			ReportsDtoList.add(this.getReportsDto(Reports));
		}
		return ReportsDtoList;
	}

	@Override
	public List<ReportsDto> search(String type, String keyword) throws Exception {
		List<ReportsDto> resultDto = new ArrayList<>();
		List<Reports> report = new ArrayList<>();

		if (type.equals(AssessmentConstants.TYPE5)) {

			List<User> userList = userRepository.findByFirstNameLastNameRegex(keyword);
			List<Reports> allReport = reportsRepository.findAll();

			for (Reports reports : allReport) {
				for (User user : userList) {
					if (reports.getUserId().equals(user.getId())) {
						report.add(reports);
					}
				}
			}
		}
		
		if(type.equals(AssessmentConstants.TYPE4)) {
			report = reportsRepository.findByTeamId(keyword);
		}

		if (type.equals(AssessmentConstants.TYPE6)) {
			report = reportsRepository.findByStatusRegex(keyword);
		}

		if (type.equals(AssessmentConstants.TYPE7)) {
			List<Reports> allReport = reportsRepository.findAll();
			if (allReport.size() > 0) {
				report = getPercentageRange(keyword, report, allReport);
			}
		}

		if (type.equals(AssessmentConstants.TYPE8)) {
			List<Reports> allReport = reportsRepository.findAll();

			if (allReport.size() > 0) {
				for (Reports reports : allReport) {
					if (keyword.equals(reports.getReportedOn().toString().split("T")[0])) {
						report.add(reports);
					}
				}

			}
		}

		if (type.equals(AssessmentConstants.TYPE9)) {
			List<User> userList = userRepository.findAllByAttempts(Integer.parseInt(keyword));
			List<Reports> allReport = reportsRepository.findAll();

			if (allReport.size() > 0 && userList.size() > 0) {
				for (Reports reports : allReport) {
					for (User user : userList) {
						if (reports.getUserId().equals(user.getId())) {
							report.add(reports);
						}
					}
				}
			}
		}

		for (Reports report1 : report) {
			resultDto.add(getReportsDto(report1));
		}

		return resultDto;
	}

	/**
	 * @param keyword
	 * @param report
	 * @param allReport
	 * @throws NumberFormatException
	 */
	private List<Reports> getPercentageRange(String keyword, List<Reports> report, List<Reports> allReport)
			throws NumberFormatException {
		if (keyword.equals(AssessmentConstants.RANGE0)) {
			for (Reports reports : allReport) {
				if (Integer.parseInt(reports.getPercentage().split(" ")[0]) >= 0
						&& Integer.parseInt(reports.getPercentage().split(" ")[0]) <= 25) {
					report.add(reports);
				}
			}
		} else if (keyword.equals(AssessmentConstants.RANGE1)) {
			for (Reports reports : allReport) {
				if (Integer.parseInt(reports.getPercentage().split(" ")[0]) >= 26
						&& Integer.parseInt(reports.getPercentage().split(" ")[0]) <= 50) {
					report.add(reports);
				}
			}
		} else if (keyword.equals(AssessmentConstants.RANGE2)) {
			for (Reports reports : allReport) {
				if (Integer.parseInt(reports.getPercentage().split(" ")[0]) >= 51
						&& Integer.parseInt(reports.getPercentage().split(" ")[0]) <= 75) {
					report.add(reports);
				}
			}
		} else if (keyword.equals(AssessmentConstants.RANGE3)) {
			for (Reports reports : allReport) {
				if (Integer.parseInt(reports.getPercentage().split(" ")[0]) >= 76
						&& Integer.parseInt(reports.getPercentage().split(" ")[0]) <= 100) {
					report.add(reports);
				}
			}
		}
		return report;
	}

	@Override
	public byte[] downloadReports(List<ReportsDto> reportsDtoList, String lang) throws IOException {

		File file = resourceLoader.getResource("classpath:excel-templates/Assessment_Report.xlsx").getFile();
		try (Workbook resourceAssessmentWB = new XSSFWorkbook(file)) {

			System.out.println("Size" + reportsDtoList.size());
			Sheet sheet = resourceAssessmentWB.getSheetAt(0);

			AssessmentDefaultMethods.cleanSheet(sheet);
			int rowNum = 2;
			for (ReportsDto reportsDto : reportsDtoList) {

				Row dataRow = sheet.createRow(rowNum);

				Cell slNo = dataRow.createCell(0);
				slNo.setCellValue(rowNum - 1);

				dataRow.createCell(1).setCellValue(reportsDto.getUserName());
				if (reportsDto.getUserId() != null) {
					User user = userRepository.findById(reportsDto.getUserId());
					Team team = teamRepository.findById(user.getTeamId());
					if (team != null) {
						dataRow.createCell(2).setCellValue(team.getTeam());
					}
				}

				if (reportsDto.getUserId() != null) {
					User user = userRepository.findById(reportsDto.getUserId());
					if (user != null) {
						dataRow.createCell(3).setCellValue(user.getManager());
					}
				}

				dataRow.createCell(4).setCellValue(reportsDto.getPercentage());
				dataRow.createCell(5).setCellValue(reportsDto.getStatus());
				dataRow.createCell(6).setCellValue(reportsDto.getAttempts());
				dataRow.createCell(7).setCellValue(
						reportsDto.getReportedOn() == null ? "-" : reportsDto.getReportedOn().toLocalDate().toString());
				dataRow.createCell(8).setCellValue("");

				rowNum++;
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			resourceAssessmentWB.write(outputStream);

			resourceAssessmentWB.close();

			return outputStream.toByteArray();

		} catch (Exception ex) {
			logger.error("Error during Customer Details download file", ex);
			return null;
		}

	}

	@Override
	public Map<String, Object> getAllReportsPage(Pageable paging) throws IOException {
		paging.getSort();
		Page<Reports> reportPage = reportsRepository.findAll(paging);
		List<ReportsDto> resultDto = new ArrayList<>();
		List<Reports> ReportsList = reportPage.getContent();
		for (Reports Reports : ReportsList) {
			User user = userRepository.findById(Reports.getUserId());
			Reports.setFirstName(user.getFirstName());
			Reports.setLastName(user.getLastName());
			resultDto.add(this.getReportsDto(Reports));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("reports", ReportsList);
		response.put("currentPage", reportPage.getNumber());
		response.put("totalItems", reportPage.getTotalElements());
		response.put("totalPages", reportPage.getTotalPages());
		return response;
	}

	@Override
	public Map<String, Object> searchReportPage(Pageable paging, String type, String keyword) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
