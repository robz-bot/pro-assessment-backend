package com.promantus.Assessment.Service;

import com.promantus.Assessment.Dto.SettingsDto;

public interface SettingsService {

	SettingsDto addSettings(SettingsDto settingsDto, String lang) throws Exception;

}
