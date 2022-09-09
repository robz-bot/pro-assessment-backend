package com.promantus.Assessment.Service;

import com.promantus.Assessment.Dto.UserDto;

public interface LoginService {

	UserDto login(final String userName, final String password, final String lang) throws Exception;

	UserDto changePassword(final UserDto userDto, final String lang) throws Exception;

	UserDto updateProfile(UserDto userDto, final String lang) throws Exception;
	
	UserDto resetPassword(UserDto userDto, String lang) throws Exception;

}
