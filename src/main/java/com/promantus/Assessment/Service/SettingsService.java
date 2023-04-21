package com.promantus.Assessment.Service;

import java.util.List;

import com.promantus.Assessment.Dto.SettingsDto;


public interface SettingsService {

	SettingsDto addSettings(SettingsDto settingsDto, String lang) throws Exception;
	
	List<SettingsDto> getAllSettings() throws Exception;

	SettingsDto updateSettings(SettingsDto settingsDto, String lang);

}
