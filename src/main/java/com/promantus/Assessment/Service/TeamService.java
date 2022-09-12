package com.promantus.Assessment.Service;

import java.util.List;

import com.promantus.Assessment.Dto.TeamDto;

public interface TeamService {

	TeamDto addTeam(TeamDto teamDto, String lang) throws Exception;

	List<TeamDto> getAllTeams() throws Exception;

	TeamDto getTeamById(String teamId) throws Exception;

	TeamDto updateTeam(TeamDto teamDto, String lang) throws Exception;

	TeamDto deleteTeamById(String teamId) throws Exception;

	Boolean checkTeamName(TeamDto teamDto) throws Exception;

	TeamDto searchByTeamId(Long id) throws Exception;

}
