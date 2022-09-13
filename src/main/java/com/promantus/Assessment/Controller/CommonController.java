package com.promantus.Assessment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.promantus.Assessment.ServiceImpl.CommonServiceImpl;

@RestController
public class CommonController {

	@Autowired
	private CommonServiceImpl commonService;

	/**
	 * @param messageKey
	 * @param language
	 * @return
	 * @throws Exception
	 */
	public String getMessage(String messageKey, String[] params, String language) throws Exception {

		return commonService.getMessage(messageKey, params, language);
	}
}
