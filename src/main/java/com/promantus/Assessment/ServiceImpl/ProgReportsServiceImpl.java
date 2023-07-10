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
import com.promantus.Assessment.Dto.ProgReportsDto;
import com.promantus.Assessment.Dto.ReportsDto;
import com.promantus.Assessment.Dto.UserDto;
import com.promantus.Assessment.Entity.ProgReports;
import com.promantus.Assessment.Entity.Reports;
import com.promantus.Assessment.Entity.Settings;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.User;
import com.promantus.Assessment.Repository.ProgReportsRepository;
import com.promantus.Assessment.Repository.ReportsRepository;
import com.promantus.Assessment.Repository.SettingsRepository;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Repository.UserRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.ProgReportsService;
import com.promantus.Assessment.Service.ReportsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class ProgReportsServiceImpl implements ProgReportsService {

	private static final Logger logger = LoggerFactory.getLogger(ProgReportsServiceImpl.class);

	@Autowired
	ProgReportsRepository progReportsRepository;

	@Autowired
	CommonService commonService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SettingsRepository settingsRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	SmtpMailSender smtpMailSender;

	@Autowired
	ResourceLoader resourceLoader;

	@Override
	public ProgReportsDto addProgReports(List<ProgReportsDto> reportsDto, String lang) throws Exception {
		ProgReportsDto resultDto = new ProgReportsDto();
		for (ProgReportsDto progReportsDto : reportsDto) {
			User repUser = userRepository.findById(progReportsDto.getUserId());

			ProgReports reports = new ProgReports();
			reports.setId(commonService.nextSequenceNumber());

			reports.setQuestion(progReportsDto.getQuestion());
			reports.setAnswer(progReportsDto.getAnswer());
			reports.setLevel(progReportsDto.getLevel());
			reports.setTeamId(progReportsDto.getTeamId());
			reports.setUserId(progReportsDto.getUserId());
			reports.setStatus(progReportsDto.getStatus());
			reports.setReportedOn(LocalDateTime.now().toString().split("T")[0]);
			reports.setAttempts(repUser.getProgAttempts() + 1);
			userRepository.save(repUser);
			progReportsRepository.save(reports);
		}
		
		resultDto.setMessage("Program Reports added successfully");

		return resultDto;
	}

	@Override
	public List<ProgReportsDto> getAllProgReports() throws Exception {
		List<ProgReports> ReportsList = progReportsRepository.findAll();

		List<ProgReportsDto> ReportsDtoList = new ArrayList<ProgReportsDto>();
		for (ProgReports Reports : ReportsList) {
			ReportsDtoList.add(this.getProgReportsDto(Reports));
		}

		return ReportsDtoList;
	}

	private ProgReportsDto getProgReportsDto(ProgReports reports) {
		ProgReportsDto reportsDto = new ProgReportsDto();

		reportsDto.setId(reports.getId());
		reportsDto.setUserId(reports.getUserId());
		reportsDto.setTeamId(reports.getTeamId());
		reportsDto.setPercentage(reports.getPercentage());
		
		reportsDto.setQuestion(reports.getQuestion());
		reportsDto.setAnswer(reports.getAnswer());
		
		Settings settings =  settingsRepository.findAll().get(0);
		
		if(reports.getLevel().equals("B")) {
			reportsDto.setLevel("Beginner");
			reportsDto.setTotalMark(String.valueOf(settings.getTotalBeginnerMarks()));
		}
		if(reports.getLevel().equals("I")) {
			reportsDto.setLevel("Intermediate");
			reportsDto.setTotalMark(String.valueOf(settings.getTotalIntermediateMarks()));
		}
		if(reports.getLevel().equals("A")) {
			reportsDto.setLevel("Advanced");
			reportsDto.setTotalMark(String.valueOf(settings.getTotalAdvancedMarks()));
		}		
		
		
		reportsDto.setStatus(reports.getStatus());
		reportsDto.setScoredMark(reports.getScoredMark());
		reportsDto.setRemarks(reports.getRemarks());
		reportsDto.setReportedOn(reports.getReportedOn());
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

	public ProgReportsDto updateProgReports(ProgReportsDto progReportsDto, String lang) throws Exception {

		ProgReportsDto resultDto = new ProgReportsDto();
		User repUser = userRepository.findById(progReportsDto.getUserId());

		System.out.println(progReportsDto.getId());
		ProgReports reports = progReportsRepository.findById(progReportsDto.getId());
		if (reports == null) {
			resultDto.setMessage("ProgReports does not exist");
			return resultDto;
		}
		reports.setUserId(progReportsDto.getUserId());
		reports.setScoredMark(progReportsDto.getScoredMark());
		reports.setRemarks(progReportsDto.getRemarks());
		reports.setAttempts(repUser.getProgAttempts() + 1);
		userRepository.save(repUser);
		progReportsRepository.save(reports);
		resultDto.setMessage("Program Reports Updated Successfully");
		return resultDto;

	}

	@Override
	public ProgReportsDto getProgReportsById(String id) throws Exception {
		ProgReports reports = progReportsRepository.findById(Long.parseLong(id));
		return reports != null ? this.getProgReportsDto(reports) : new ProgReportsDto();
	}

	@Override
	public List<ProgReportsDto> searchByStatus(String status) throws Exception {

		List<ProgReportsDto> ProgReportsDtoList = new ArrayList<ProgReportsDto>();
		List<ProgReports> ProgReportsList = progReportsRepository.findByStatus(status);

		for (ProgReports ProgReports : ProgReportsList) {
			ProgReportsDtoList.add(this.getProgReportsDto(ProgReports));
		}
		return ProgReportsDtoList;
	}

	@Override
	public List<ProgReportsDto> searchByPercentage(String percentage) throws Exception {

		List<ProgReportsDto> ProgReportsDtoList = new ArrayList<ProgReportsDto>();
		List<ProgReports> ProgReportsList = progReportsRepository.findByPercentage(percentage);

		for (ProgReports ProgReports : ProgReportsList) {
			ProgReportsDtoList.add(this.getProgReportsDto(ProgReports));
		}
		return ProgReportsDtoList;
	}

	@Override
	public List<ProgReportsDto> searchProg(String type, String keyword) throws Exception {
		List<ProgReportsDto> resultDto = new ArrayList<>();
		List<ProgReports> report = new ArrayList<>();

		if (type.equals(AssessmentConstants.TYPE5)) {

			List<User> userList = userRepository.findByFirstNameLastNameRegex(keyword);
			List<ProgReports> allReport = progReportsRepository.findAll();

			for (ProgReports reports : allReport) {
				for (User user : userList) {
					if (reports.getUserId().equals(user.getId())) {
						report.add(reports);
					}
				}
			}
		}

		if (type.equals(AssessmentConstants.TYPE4)) {
			report = progReportsRepository.findByTeamId(keyword);
		}

		if (type.equals(AssessmentConstants.TYPE6)) {
			report = progReportsRepository.findByStatusRegex(keyword);
		}

		if (type.equals(AssessmentConstants.TYPE7)) {
			List<ProgReports> allReport = progReportsRepository.findAll();
			if (allReport.size() > 0) {
				report = getProgPercentageRange(keyword, report, allReport);
			}
		}

		if (type.equals(AssessmentConstants.TYPE8)) {
			List<ProgReports> allReport = progReportsRepository.findAll();

			if (allReport.size() > 0) {
				for (ProgReports reports : allReport) {
					if (keyword.equals(reports.getReportedOn().toString().split("T")[0])) {
						report.add(reports);
					}
				}

			}
		}

		if (type.equals(AssessmentConstants.TYPE9)) {
			List<User> userList = userRepository.findAllByAttempts(Integer.parseInt(keyword));
			List<ProgReports> allReport = progReportsRepository.findAll();

			if (allReport.size() > 0 && userList.size() > 0) {
				for (ProgReports reports : allReport) {
					for (User user : userList) {
						if (reports.getUserId().equals(user.getId())) {
							report.add(reports);
						}
					}
				}
			}
		}

		for (ProgReports report1 : report) {
			resultDto.add(getProgReportsDto(report1));
		}

		return resultDto;
	}

	/*
	 * @param keyword
	 * 
	 * @param report
	 * 
	 * @param allReport
	 * 
	 * @throws NumberFormatException
	 */
	private List<ProgReports> getProgPercentageRange(String keyword, List<ProgReports> report,
			List<ProgReports> allReport) throws NumberFormatException {
		if (keyword.equals(AssessmentConstants.RANGE0)) {
			for (ProgReports reports : allReport) {
				if (Integer.parseInt(reports.getPercentage().split(" ")[0]) >= 0
						&& Integer.parseInt(reports.getPercentage().split(" ")[0]) <= 25) {
					report.add(reports);
				}
			}
		} else if (keyword.equals(AssessmentConstants.RANGE1)) {
			for (ProgReports reports : allReport) {
				if (Integer.parseInt(reports.getPercentage().split(" ")[0]) >= 26
						&& Integer.parseInt(reports.getPercentage().split(" ")[0]) <= 50) {
					report.add(reports);
				}
			}
		} else if (keyword.equals(AssessmentConstants.RANGE2)) {
			for (ProgReports reports : allReport) {
				if (Integer.parseInt(reports.getPercentage().split(" ")[0]) >= 51
						&& Integer.parseInt(reports.getPercentage().split(" ")[0]) <= 75) {
					report.add(reports);
				}
			}
		} else if (keyword.equals(AssessmentConstants.RANGE3)) {
			for (ProgReports reports : allReport) {
				if (Integer.parseInt(reports.getPercentage().split(" ")[0]) >= 76
						&& Integer.parseInt(reports.getPercentage().split(" ")[0]) <= 100) {
					report.add(reports);
				}
			}
		}
		return report;
	}

	@Override
	public byte[] downloadProgReports(List<ProgReportsDto> progReportsDtoList, String lang) throws IOException {

		File file = resourceLoader.getResource("classpath:excel-templates/Program_Assessment_Report.xlsx").getFile();
		try (Workbook resourceAssessmentWB = new XSSFWorkbook(file)) {

			System.out.println("Size" + progReportsDtoList.size());
			Sheet sheet = resourceAssessmentWB.getSheetAt(0);

			AssessmentDefaultMethods.cleanSheet(sheet);
			int rowNum = 2;
			for (ProgReportsDto reportsDto : progReportsDtoList) {

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

				dataRow.createCell(4).setCellValue(reportsDto.getScoredMark());
				dataRow.createCell(5).setCellValue(reportsDto.getTotalMark());
				dataRow.createCell(6).setCellValue(reportsDto.getPercentage());
				dataRow.createCell(7).setCellValue(reportsDto.getStatus());
				dataRow.createCell(8).setCellValue(reportsDto.getAttempts());
				dataRow.createCell(9)
						.setCellValue(reportsDto.getReportedOn() == null ? "-" : reportsDto.getReportedOn());
				dataRow.createCell(10).setCellValue("");

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
	public Map<String, Object> getAllProgReportsPage(Pageable paging) throws IOException {
		paging.getSort();
		Page<ProgReports> progReportPage = progReportsRepository.findAll(paging);
		List<ProgReportsDto> resultDto = new ArrayList<>();
		List<ProgReports> reportsList = progReportPage.getContent();
		for (ProgReports reports : reportsList) {
			User user = userRepository.findById(reports.getUserId());
			reports.setFirstName(user.getFirstName());
			reports.setLastName(user.getLastName());
			resultDto.add(this.getProgReportsDto(reports));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("reports", reportsList);
		response.put("currentPage", progReportPage.getNumber());
		response.put("totalItems", progReportPage.getTotalElements());
		response.put("totalPages", progReportPage.getTotalPages());
		return response;
	}

	@Override
	public Map<String, Object> searchProgReportPage(Pageable paging, String type, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProgReportsDto> getProgByReportedOn(ProgReportsDto progReportsDto, String type) throws Exception {
		List<ProgReportsDto> resultDto = new ArrayList<>();
		List<ProgReports> reports = new ArrayList<>();
		if (type.equals("reportedOn")) {
			reports = progReportsRepository.findByReportedOnRegex(progReportsDto.getReportedOn());
		} else if (type.equals("userIdAndReportedOn")) {
			reports = progReportsRepository.findByUserIdAndReportedOnRegex(progReportsDto.getUserId(),
					progReportsDto.getReportedOn());
		}

		for (ProgReports reports2 : reports) {
			resultDto.add(getProgReportsDto(reports2));
		}
		return resultDto;

	}

}
