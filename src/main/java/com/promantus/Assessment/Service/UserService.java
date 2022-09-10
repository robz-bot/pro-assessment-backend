package com.promantus.Assessment.Service;

import java.util.List;

import com.promantus.Assessment.Dto.UserDto;


public interface UserService {
	
//	UserDto addUser(final UserDto userDto, String lang) throws Exception;

	List<UserDto> getAllUsers() throws Exception;

	UserDto getUserById(String userId) throws Exception;
	
	UserDto updateUser(final UserDto userDto, final String lang) throws Exception;

	UserDto deleteUserById(String userId) throws Exception;

	Boolean checkUserName(String userName) throws Exception;

	UserDto addUser(UserDto userDto, String lang) throws Exception;

	UserDto getUserByEmail(String email)throws Exception;




}
