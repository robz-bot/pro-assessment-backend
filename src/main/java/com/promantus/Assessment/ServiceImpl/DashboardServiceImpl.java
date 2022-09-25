package com.promantus.Assessment.ServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.Dto.ActiveInactiveDto;
import com.promantus.Assessment.Dto.WidgetDto;
import com.promantus.Assessment.Entity.GeneralQuestion;
import com.promantus.Assessment.Entity.Reports;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.TechQuestion;
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

		WidgetDto genQuestion = new WidgetDto();
		genQuestion.setTitle("General");
		genQuestion.setCount("" + genRepo.findAll().size());
		genQuestion.setStyleClass(AssessmentConstants.QUES_ICON_STYLE);

		WidgetDto techQuestion = new WidgetDto();
		techQuestion.setTitle("Technical");
		techQuestion.setCount("" + techRepo.findAll().size());
		techQuestion.setStyleClass(AssessmentConstants.QUES_ICON_STYLE);

		resultDto.add(attempt);
		resultDto.add(user);
		resultDto.add(pass);
		resultDto.add(fail);
		resultDto.add(team);
		resultDto.add(genQuestion);
		resultDto.add(techQuestion);

		return resultDto;
	}

	@Override
	public Map<Object, Object> userAttemptsChart() throws Exception {

		List<Reports> reportList = reportRepo.findAll();

		ArrayList<String> getRecentDates = new ArrayList<>();
		ArrayList<Integer> getRecentDatesCount = new ArrayList<>();
		getRecentDates = DashboardServiceImpl.getRecentDates();
		Map<Object, Object> map = new HashMap<>();
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		for (int i = 0; i < reportList.size(); i++) {
			if (getRecentDates.get(0).equals(reportList.get(i).getReportedOn().toString().split("T")[0].toString())) {
				count1++;
			}
			if (getRecentDates.get(1).equals(reportList.get(i).getReportedOn().toString().split("T")[0].toString())) {
				count2++;
			}
			if (getRecentDates.get(2).equals(reportList.get(i).getReportedOn().toString().split("T")[0].toString())) {
				count3++;
			}
			if (getRecentDates.get(3).equals(reportList.get(i).getReportedOn().toString().split("T")[0].toString())) {
				count4++;
			}
			if (getRecentDates.get(4).equals(reportList.get(i).getReportedOn().toString().split("T")[0].toString())) {
				count5++;
			}
		}
		getRecentDatesCount.add(count1);
		getRecentDatesCount.add(count2);
		getRecentDatesCount.add(count3);
		getRecentDatesCount.add(count4);
		getRecentDatesCount.add(count5);
		map.put("dates", getRecentDates);
		map.put("count", getRecentDatesCount);

		return map;
	}

	private static ArrayList<String> getRecentDates() {
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		int day = cal.get(GregorianCalendar.DAY_OF_MONTH);

		ArrayList<String> dates = new ArrayList<String>();
		for (int i = day; i > (day - 5); i--) {
			cal.set(GregorianCalendar.DAY_OF_MONTH, i);

			Date date = cal.getTime();
			dates.add(sdf.format(date));
		}

		return dates;

	}

	@Override
	public Map<Object, Object> datewisePassFail(String date) throws Exception {
		List<Reports> reportList = reportRepo.findAll();
		int passCount = 0;
		int failCount = 0;

		for (Reports reports : reportList) {
			if (reports.getReportedOn().toString().split("T")[0].equals(date)
					&& reports.getStatus().equals(AssessmentConstants.PASS)) {
				passCount++;
			}
			if (reports.getReportedOn().toString().split("T")[0].equals(date)
					&& reports.getStatus().equals(AssessmentConstants.FAIL)) {
				failCount++;
			}
		}

		Map<Object, Object> map = new HashMap<>();
		map.put("pass", passCount);
		map.put("fail", failCount);

		return map;
	}

	@Override
	public Map<Object, Object> teamExamReadiness() throws Exception {
		List<Team> teamList = teamRepo.findAll();
		List<GeneralQuestion> genQns = genRepo.findAllByIsActive(true);

		int genCount = genQns.size();
		Map<Object, Object> teamMap = new HashMap<>();
		List<String> liveList = new ArrayList<>();
		List<String> errList = new ArrayList<>();
		for (Team team : teamList) {
			List<TechQuestion> techList = techRepo.findAllByTeamIdAndIsActive(team.getId(), true);
			int count = techList.size();
			teamMap.put(team.getTeam(), count);
			if (count < 25) {
				int temp = 25 - count;

				errList.add("Oops! To begin the assessment, " + temp
						+ " more technical questions are required for the team - " + team.getTeam());
			} else if (genCount >= 5 && count >= 25) {
				liveList.add("Assessment is Live for the team - " + team.getTeam());
			}

			// if(count)
		}
		teamMap.put("liveList", liveList);
		teamMap.put("errList", errList);
		teamMap.put("genQnsCount", genQns.size());

		if (genCount < 6) {
			int temp = 25 - genCount;
			teamMap.put("genQnInfo",
					"Oops! To begin the assessment, " + temp + " more technical questions are required.");
		}

		return teamMap;
	}

	@Override
	public Map<Object, Object> questionsActiveInactive() throws Exception {

		List<Team> teamList = teamRepo.findAll();
		List<TechQuestion> techList = techRepo.findAll();
		List<GeneralQuestion> genList = genRepo.findAll();

		int isActiveCount = 0;
		int isNotActiveCount = 0;

		List<ActiveInactiveDto> list = new ArrayList<>();
		Map<Object, Object> response = new HashMap<Object, Object>();

		for (Team team : teamList) {
			ActiveInactiveDto dto = new ActiveInactiveDto();
			isActiveCount = 0;
			isNotActiveCount = 0;
			dto.setTeam(team.getTeam() + "");
			for (TechQuestion techQuestion : techList) {
				if (team.getId().equals(techQuestion.getTeamId()) && techQuestion.getisActive() == true) {
					isActiveCount++;
				} else if (team.getId().equals(techQuestion.getTeamId()) && techQuestion.getisActive() == false) {
					isNotActiveCount++;
				}
				dto.setIsActiveCount(isActiveCount + "");
				dto.setIsInActiveCount(isNotActiveCount + "");
				list.add(dto);
			}
		}

		isActiveCount = 0;
		isNotActiveCount = 0;
		for (GeneralQuestion genQuestion : genList) {
			if (genQuestion.getisActive() == true) {
				isActiveCount++;
			} else if (genQuestion.getisActive() == false) {
				isNotActiveCount++;
			}
		}

		response.put("genListActive", isActiveCount);
		response.put("genListInActive", isNotActiveCount);

		isActiveCount = 0;
		isNotActiveCount = 0;
		for (TechQuestion techQuestion : techList) {
			if (techQuestion.getisActive() == true) {
				isActiveCount++;
			} else if (techQuestion.getisActive() == false) {
				isNotActiveCount++;
			}
		}
		Set<ActiveInactiveDto> set = new LinkedHashSet<>();
		set.addAll(list);

		response.put("techList", set);
		response.put("techListActive", isActiveCount);
		response.put("techListInActive", isNotActiveCount);

		return response;
	}

}
