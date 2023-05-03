/**
 * 
 */
package com.promantus.Assessment.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promantus.Assessment.Dto.SettingsDto;
import com.promantus.Assessment.Entity.Settings;
import com.promantus.Assessment.Repository.GeneralQuestionRepository;
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

	@Override
	public SettingsDto addSettings(SettingsDto settingsDto, String lang) throws Exception {

		SettingsDto resultDto = new SettingsDto();

		Settings setting = new Settings();
		Long num = commonService.nextSequenceNumber();
		setting.setId(num);
		setting.setGenQns(5);
		setting.setGenQns(25);
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
		settingsDto.setPassPercentage(settings.getPassPercentage());
		settingsDto.setFailPercentage(settings.getFailPercentage());
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
		
		if(settingsDto.getGenQns() > genQnsCount) {
			resultDto.setMessage("General Questions should not exceed "+ genQnsCount);
			return resultDto;
		}
		
		if(settingsDto.getTechQns() > techQnsCount) {
			resultDto.setMessage("Technical Questions should not exceed "+ techQnsCount);
			return resultDto;
		}

		settings.setId(settingsDto.getId());
		settings.setGenQns(settingsDto.getGenQns());
		settings.setTechQns(settingsDto.getTechQns());
		settings.setPassPercentage(settingsDto.getPassPercentage());
		settings.setFailPercentage(settingsDto.getFailPercentage());

		settingsRepository.save(settings);
		resultDto.setMessage("Record Updated Successfully");
		return resultDto;

	}



	
}
