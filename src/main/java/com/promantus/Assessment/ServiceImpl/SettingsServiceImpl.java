/**
 * 
 */
package com.promantus.Assessment.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.promantus.Assessment.Dto.SettingsDto;
import com.promantus.Assessment.Entity.Settings;
import com.promantus.Assessment.Repository.SettingsRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.SettingsService;

/**
 * @author Promantus
 *
 */
public class SettingsServiceImpl implements SettingsService {

	@Autowired
	CommonService commonService;

	@Autowired
	SettingsRepository settingsRepository;

	@Override
	public SettingsDto addSettings(SettingsDto settingsDto, String lang) throws Exception {

		SettingsDto resultDto = new SettingsDto();

		Settings setting = new Settings();
		Long num = commonService.nextSequenceNumber();
		setting.setId(num);
		setting.setGenQns(5);
		setting.setGenQns(25);

		settingsRepository.save(setting);

		resultDto.setMessage("Team added successfully");
		return resultDto;
	}

}
