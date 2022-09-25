package com.promantus.Assessment.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.Dto.ExamDto;
import com.promantus.Assessment.Entity.GeneralQuestion;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.TechQuestion;
import com.promantus.Assessment.Entity.User;
import com.promantus.Assessment.Repository.GeneralQuestionRepository;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Repository.TechQuestionRepository;
import com.promantus.Assessment.Repository.UserRepository;
import com.promantus.Assessment.Service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	GeneralQuestionRepository genQnRepo;

	@Autowired
	TechQuestionRepository techQnRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	TeamRepository teamRepo;

	@Override
	public List<ExamDto> getExamQns(String teamId, String userId) throws Exception {
		List<ExamDto> resultDto = new ArrayList<ExamDto>();

		List<GeneralQuestion> genQns = new ArrayList<>();
		genQns = genQnRepo.findAllByIsActive(true);
		//List<GeneralQuestion> genQns1 = genQnRepo.findAllQuestionDistinctBy();

		List<TechQuestion> techQns = techQnRepo.findAllByTeamIdAndIsActive(Long.parseLong(teamId),true);
		//List<TechQuestion> techQns1 = techQnRepo.findAllQuestionDistinctBy();
		

		if (genQns.size() > 0 && techQns.size() > 0) {
			// Randomize the Qns
			Collections.shuffle(genQns);
			Collections.shuffle(techQns);

			Integer runningNumber = 0;
			// Pick 5 Qns from genQn and 25 from techQn
			for (int i = 0; i < genQns.size(); i++) {
				// run from index 0 to 4
				if (i <= 4) {
					runningNumber++;
					resultDto.add(this.getGeneralQuestionDto(runningNumber, genQns.get(i)));
				}
			}

			for (int i = 0; i < techQns.size(); i++) {
				// run from index 0 to 24
				if (i <= 24) {
					runningNumber++;
					resultDto.add(this.getTechQuestionDto(runningNumber, techQns.get(i)));
				}
			}

			User getUser = userRepo.findById(Long.parseLong(userId));
			Team getTeam = teamRepo.findById(Long.parseLong(teamId));
			if (getUser != null) {
				ExamDto resDto = resultDto.get(0);
				resDto.setUserId(getUser.getId());
				resDto.setEmail(getUser.getEmail());
				resDto.setEmpCode(getUser.getEmpCode());
				resDto.setFirstName(getUser.getFirstName());
				resDto.setLastName(getUser.getLastName());
				resDto.setManager(getUser.getManager());
				resDto.setTeam(getTeam.getTeam());
				resDto.setTeamId(teamId);

			}

		}
		return resultDto;
	}

	private ExamDto getGeneralQuestionDto(Integer currentNumber, GeneralQuestion generalQuestion) {
		ExamDto resultDto = new ExamDto();

		ArrayList<String> optionList = new ArrayList<String>();
		optionList.add(generalQuestion.getOption1());
		optionList.add(generalQuestion.getOption2());
		optionList.add(generalQuestion.getOption3());
		optionList.add(generalQuestion.getOption4());

		resultDto.setId(currentNumber);
		resultDto.setQuestion(generalQuestion.getQuestion());
		resultDto.setOptions(optionList);
		resultDto.setAnswer(generalQuestion.getAnswer());
		return resultDto;

	}

	private ExamDto getTechQuestionDto(Integer currentNumber, TechQuestion techQuestion) {
		ExamDto resultDto = new ExamDto();

		ArrayList<String> optionList = new ArrayList<String>();
		optionList.add(techQuestion.getOption1());
		optionList.add(techQuestion.getOption2());
		optionList.add(techQuestion.getOption3());
		optionList.add(techQuestion.getOption4());

		resultDto.setId(currentNumber);
		resultDto.setOptions(optionList);
		resultDto.setQuestion(techQuestion.getQuestion());
		resultDto.setAnswer(techQuestion.getAnswer());
		return resultDto;

	}

}
