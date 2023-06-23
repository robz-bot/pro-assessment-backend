/**
 * 
 */
package com.promantus.Assessment.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promantus.Assessment.Dto.SettingsDto;
import com.promantus.Assessment.Entity.ProgReports;
import com.promantus.Assessment.Entity.Settings;
import com.promantus.Assessment.Repository.GeneralQuestionRepository;
import com.promantus.Assessment.Repository.ProgramQuestionRepository;
import com.promantus.Assessment.Repository.SettingsRepository;
import com.promantus.Assessment.Repository.TechQuestionRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.SettingsService;

/**
 * @author Promantus
 *
 */
@Service
public class SettingsServiceImpl implements SettingsService {

	@Autowired
	CommonService commonService;

	@Autowired
	SettingsRepository settingsRepository;

	@Autowired
	GeneralQuestionRepository generalQuestionRepository;

	@Autowired
	TechQuestionRepository techQuestionRepository;

	@Autowired
	ProgramQuestionRepository progQuestionRepository;

	@Override
	public SettingsDto addSettings(SettingsDto settingsDto, String lang) throws Exception {

		SettingsDto resultDto = new SettingsDto();

		Settings setting = new Settings();
		Long num = commonService.nextSequenceNumber();
		setting.setId(num);
		setting.setGenQns(5);
		setting.setTechQns(25);
		setting.setBeginner(1);
		setting.setIntermediate(1);
		setting.setAdvanced(1);
		setting.setPassPercentage(50);
		setting.setFailPercentage(49);
		setting.setProgPassPercentage(50);
		setting.setProgFailPercentage(49);
		setting.setTotalBeginnerMarks(100);
		setting.setTotalIntermediateMarks(100);
		setting.setTotalAdvancedMarks(100);

		settingsRepository.save(setting);

		resultDto.setMessage("Settings added successfully");
		return resultDto;
	}

	@Override
	public List<SettingsDto> getAllSettings() {
		List<Settings> SettingsList = settingsRepository.findAll();

		List<SettingsDto> SettingsDtoList = new ArrayList<SettingsDto>();
		for (Settings Settings : SettingsList) {
			SettingsDtoList.add(this.getSettingsDto(Settings));
		}

		return SettingsDtoList;
	}

	private SettingsDto getSettingsDto(Settings settings) {
		SettingsDto settingsDto = new SettingsDto();

		settingsDto.setId(settings.getId());
		settingsDto.setGenQns(settings.getGenQns());
		settingsDto.setTechQns(settings.getTechQns());
		settingsDto.setBeginner(settings.getBeginner());
		settingsDto.setIntermediate(settings.getIntermediate());
		settingsDto.setAdvanced(settings.getAdvanced());
		settingsDto.setPassPercentage(settings.getPassPercentage());
		settingsDto.setFailPercentage(settings.getFailPercentage());
		settingsDto.setProgPassPercentage(settings.getProgPassPercentage());
		settingsDto.setProgFailPercentage(settings.getProgFailPercentage());
		settingsDto.setTotalBeginnerMarks(settings.getTotalBeginnerMarks());
		settingsDto.setTotalIntermediateMarks(settings.getTotalIntermediateMarks());
		settingsDto.setTotalAdvancedMarks(settings.getTotalAdvancedMarks());
		return settingsDto;

	}

	@Override
	public SettingsDto updateSettings(SettingsDto settingsDto, String lang) {

		SettingsDto resultDto = new SettingsDto();
		System.out.println(settingsDto.getId());
		Settings settings = settingsRepository.findById(settingsDto.getId()).orElse(null);

		if (settings == null) {

			resultDto.setMessage("Settings does not exist");
			return resultDto;
		}

		int genQnsCount = generalQuestionRepository.findAll().size();
		int techQnsCount = techQuestionRepository.findAll().size();
		List<ProgReports> beginnerList = progQuestionRepository.findAllByProgramLevel("B");
		List<ProgReports> intermediateList = progQuestionRepository.findAllByProgramLevel("I");
		List<ProgReports> advancedList = progQuestionRepository.findAllByProgramLevel("A");

		if (settingsDto.getGenQns() > genQnsCount) {
			resultDto.setMessage("General Questions should not exceed " + genQnsCount);
			resultDto.setStatus(1);
			return resultDto;
		}

		if (settingsDto.getTechQns() > techQnsCount) {
			resultDto.setMessage("Technical Questions should not exceed " + techQnsCount);
			resultDto.setStatus(1);
			return resultDto;
		}
		if (settingsDto.getBeginner() > beginnerList.size()) {
			resultDto.setMessage("Beginner Question Count should not exceed " + beginnerList.size());
			resultDto.setStatus(1);
			return resultDto;
		}
		if (settingsDto.getIntermediate() > intermediateList.size()) {
			resultDto.setMessage("Intermediate Question Count should not exceed " + intermediateList.size());
			resultDto.setStatus(1);
			return resultDto;
		}

		if (settingsDto.getAdvanced() > advancedList.size()) {
			resultDto.setMessage("Advanced Question Count should not exceed " + advancedList.size());
			resultDto.setStatus(1);
			return resultDto;
		}

		settings.setId(settingsDto.getId());
		settings.setGenQns(settingsDto.getGenQns());
		settings.setTechQns(settingsDto.getTechQns());
		settings.setBeginner(settingsDto.getBeginner());
		settings.setIntermediate(settingsDto.getIntermediate());
		settings.setAdvanced(settingsDto.getAdvanced());
		settings.setPassPercentage(settingsDto.getPassPercentage());
		settings.setFailPercentage(settingsDto.getFailPercentage());
		settings.setProgPassPercentage(settingsDto.getProgPassPercentage());
		settings.setProgFailPercentage(settingsDto.getProgFailPercentage());
		settings.setTotalBeginnerMarks(settingsDto.getTotalBeginnerMarks());
		settings.setTotalIntermediateMarks(settingsDto.getTotalIntermediateMarks());
		settings.setTotalAdvancedMarks(settingsDto.getTotalAdvancedMarks());
		resultDto.setStatus(0);

		settingsRepository.save(settings);
		resultDto.setMessage("Record Updated Successfully");
		return resultDto;

	}

}
