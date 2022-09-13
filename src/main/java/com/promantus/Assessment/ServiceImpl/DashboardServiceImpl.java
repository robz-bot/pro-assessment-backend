package com.promantus.Assessment.ServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.Dto.WidgetDto;
import com.promantus.Assessment.Entity.Reports;
import com.promantus.Assessment.Repository.GeneralQuestionRepository;
import com.promantus.Assessment.Repository.ReportsRepository;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Repository.TechQuestionRepository;
import com.promantus.Assessment.Repository.UserRepository;
import com.promantus.Assessment.Service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	TeamRepository teamRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	GeneralQuestionRepository genRepo;

	@Autowired
	TechQuestionRepository techRepo;

	@Autowired
	ReportsRepository reportRepo;

	@Override
	public List<WidgetDto> widgetData() throws Exception {

		List<WidgetDto> resultDto = new ArrayList<>();

		WidgetDto attempt = new WidgetDto();
		attempt.setTitle("Today Attempts");
		List<Reports> reportList = reportRepo.findAll();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Long count = 0L;

		for (Reports reports : reportList) {
			if (reports.getReportedOn().toString().split("T")[0].equals(dateFormat.format(date))) {
				count++;
			}
		}
		attempt.setCount(count.toString());
		attempt.setStyleClass(AssessmentConstants.ATTEMPT_ICON_STYLE);

		WidgetDto user = new WidgetDto();
		user.setTitle("User(s)");
		user.setCount("" + userRepo.findAll().size() + "");
		user.setStyleClass(AssessmentConstants.USER_ICON_STYLE);

		WidgetDto pass = new WidgetDto();
		pass.setTitle("Pass");
		int passCount = 0;
		int failCount = 0;
		for (Reports reports : reportList) {
			if (reports.getStatus().equals(AssessmentConstants.PASS)) {
				passCount++;
			} else {
				failCount++;
			}
		}
		pass.setCount("" + passCount + "");
		pass.setStyleClass(AssessmentConstants.PASS_ICON_STYLE);

		WidgetDto fail = new WidgetDto();
		fail.setTitle("Fail");
		fail.setCount("" + failCount + "");
		fail.setStyleClass(AssessmentConstants.FAIL_ICON_STYLE);

		WidgetDto team = new WidgetDto();
		team.setTitle("Team(s)");
		team.setCount("" + teamRepo.findAll().size() + "");
		team.setStyleClass(AssessmentConstants.TEAM_ICON_STYLE);

		WidgetDto question = new WidgetDto();
		question.setTitle("General | Technical");
		question.setCount("" + genRepo.findAll().size() + " | " + techRepo.findAll().size());
		question.setStyleClass(AssessmentConstants.QUES_ICON_STYLE);

		resultDto.add(attempt);
		resultDto.add(user);
		resultDto.add(pass);
		resultDto.add(fail);
		resultDto.add(team);
		resultDto.add(question);

		return resultDto;
	}

}
