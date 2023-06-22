package com.promantus.Assessment.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.Dto.ExamDto;
import com.promantus.Assessment.Entity.GeneralQuestion;
import com.promantus.Assessment.Entity.ProgramQuestion;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.TechQuestion;
import com.promantus.Assessment.Entity.User;
import com.promantus.Assessment.Repository.GeneralQuestionRepository;
import com.promantus.Assessment.Repository.ProgramQuestionRepository;
import com.promantus.Assessment.Repository.SettingsRepository;
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
	ProgramQuestionRepository progQnRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	TeamRepository teamRepo;

	@Autowired
	SettingsRepository settingsRepo;

	@Override
	public List<ExamDto> getExamQns(String teamId, String userId) throws Exception {
		List<ExamDto> resultDto = new ArrayList<ExamDto>();

		List<GeneralQuestion> genQns = new ArrayList<>();
		genQns = genQnRepo.findAllByIsActive(true);
		// List<GeneralQuestion> genQns1 = genQnRepo.findAllQuestionDistinctBy();

		List<TechQuestion> techQns = techQnRepo.findAllByTeamIdAndIsActive(Long.parseLong(teamId), true);
		// List<TechQuestion> techQns1 = techQnRepo.findAllQuestionDistinctBy();

		int genQnsLen = settingsRepo.findAll().get(0).getGenQns();
		int techQnsLen = settingsRepo.findAll().get(0).getTechQns();

		if (genQns.size() > 0 && techQns.size() > 0) {
			// Randomize the Qns
			Collections.shuffle(genQns);
			Collections.shuffle(techQns);

			Integer runningNumber = 0;
			// Pick 5 Qns from genQn and 25 from techQn
			for (int i = 0; i < genQns.size(); i++) {
				// run from index 0 to 4
//				if (i <= 4) {
				if (i < genQnsLen) {
					runningNumber++;
					resultDto.add(this.getGeneralQuestionDto(runningNumber, genQns.get(i)));
				}
			}

			for (int i = 0; i < techQns.size(); i++) {
				// run from index 0 to 24
//				if (i <= 24) {
				if (i < techQnsLen) {
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
				resDto.setTeamId(getTeam.getId());

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

	@Override
	public List<ExamDto> getProgramQns(String teamId, String userId) throws Exception {

		List<ExamDto> resultDto = new ArrayList<ExamDto>();

		List<ProgramQuestion> progQns = progQnRepo.findAllByTeamIdAndIsActive(Long.parseLong(teamId), true);
		progQns = progQnRepo.findAll();

		
		int beginnerLen = settingsRepo.findAll().get(0).getBeginner();
		int intermediateLen = settingsRepo.findAll().get(0).getIntermediate();
		int advancedLen = settingsRepo.findAll().get(0).getAdvanced();
		int progQnsLen = beginnerLen + intermediateLen + advancedLen;		

		if (progQns.size() > 0) {
			// Randomize the Qns
			Collections.shuffle(progQns);

			Integer runningNumber = 0;

			for (int i = 0; i < progQns.size(); i++) {
				if (i < progQnsLen) {
					runningNumber++;
					//progQns.get(i).getProgramLevel()
					resultDto.add(this.getProgramQuestionDto(runningNumber, progQns.get(i),userId));
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
				resDto.setTeamId(getTeam.getId());

			}

		}
		return resultDto;
	}

	private ExamDto getProgramQuestionDto(Integer currentNumber, ProgramQuestion programQuestion, String userId) {
		ExamDto resultDto = new ExamDto();

		resultDto.setId(currentNumber);
		resultDto.setTeamId(Long.parseLong(programQuestion.getTeamId()));
		resultDto.setProgram(programQuestion.getProgram());
		resultDto.setProgramLevel(programQuestion.getProgramLevel());
		Team getTeam = teamRepo.findById(Long.parseLong(programQuestion.getTeamId()));
		resultDto.setTeam(getTeam.getTeam());
		User getUser = userRepo.findById(Long.parseLong(userId));
		
		if (getUser != null) {
			resultDto.setUserId(Long.parseLong(userId));
			resultDto.setEmail(getUser.getEmail());
			resultDto.setEmpCode(getUser.getEmpCode());
			resultDto.setFirstName(getUser.getFirstName());
			resultDto.setLastName(getUser.getLastName());
			resultDto.setManager(getUser.getManager());
			resultDto.setTeam(getTeam.getTeam());
			resultDto.setTeamId(getTeam.getId());

		}
		
		
		return resultDto;

	}

}
