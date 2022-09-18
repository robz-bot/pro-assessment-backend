package com.promantus.Assessment.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.SmtpMailSender;
import com.promantus.Assessment.Dto.TeamDto;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	CommonService commonService;
	
	@Autowired
	SmtpMailSender smtpMailSender;

	@Override
	public Boolean checkTeamName(TeamDto teamDto) throws Exception {

		Team team = teamRepository.getTeamByTeamIgnoreCase(teamDto.getTeam());
		if (team != null) {
			return true;
		}
		return false;
	}

	@Override
	public TeamDto addTeam(final TeamDto teamDto, String lang) throws Exception {

		TeamDto resultDto = new TeamDto();
		if (this.checkTeamName(teamDto)) {
			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage("Team '" + teamDto.getTeam() + "' already exists");
			return resultDto;
		}
		if (teamRepository.findById(teamDto.getId()) == null) {
			Team team = new Team();
			team.setId(commonService.nextSequenceNumber());
			team.setTeam(teamDto.getTeam());
			team.setCreatedOn(LocalDateTime.now());
			team.setisActive(true);

			teamRepository.save(team);

//			smtpMailSender.sendMail("robinrajesh@promantus.com", "Sample Mail", team.getTeam().toString());
		}
		resultDto.setMessage("Team added successfully");
		return resultDto;
	}

	@Override
	public List<TeamDto> getAllTeams() {
		List<Team> TeamsList = teamRepository.findAllByIsActive(true, AssessmentUtil.orderByUpdatedOnDesc());

		List<TeamDto> TeamDtoList = new ArrayList<TeamDto>();
		for (Team Team : TeamsList) {
			TeamDtoList.add(this.getTeamDto(Team));
		}

		return TeamDtoList;
	}

	private TeamDto getTeamDto(Team team) {
		TeamDto teamDto = new TeamDto();

		teamDto.setId(team.getId());
		teamDto.setTeam(team.getTeam());
		teamDto.setCreatedOn(team.getCreatedOn());
		teamDto.setisActive(team.getisActive());
		teamDto.setUpdatedOn(team.getUpdatedOn());
		return teamDto;

	}

	@Override
	public TeamDto updateTeam(TeamDto teamDto, String lang) {

		TeamDto resultDto = new TeamDto();
		System.out.println(teamDto.getId());
		Team team = teamRepository.findById(teamDto.getId());

		if (team == null) {

			resultDto.setMessage("Team does not exist");
			return resultDto;
		}

		team.setTeam(teamDto.getTeam());
		team.setCreatedOn(team.getCreatedOn());
		team.setUpdatedOn(LocalDateTime.now());
		team.setUpdatedBy(teamDto.getUpdatedBy());
		team.setisActive(true);

		teamRepository.save(team);
		resultDto.setMessage("Record Updated successfully");
		return resultDto;

	}

	@Override
	public TeamDto deleteTeamById(String teamId) {
		TeamDto resultDto = new TeamDto();
		Team team = teamRepository.findById(Long.parseLong(teamId));
		if (team == null) {

			resultDto.setMessage("data does not exist");
			return resultDto;
		}

		teamRepository.delete(team);
		resultDto.setMessage("Record Deleted successfully");
		return resultDto;
	}

	@Override
	public TeamDto getTeamById(String teamId) throws Exception {

		Team team = teamRepository.findById(Long.parseLong(teamId));

		return team != null ? this.getTeamDto(team) : new TeamDto();

	}

	@Override
	public List<TeamDto> searchByTeam(String keyword) throws Exception {

		List<TeamDto> resultDto = new ArrayList<>();
		List<Team> team = teamRepository.findByTeamRegex(keyword);
		for (Team team2 : team) {
			resultDto.add(getTeamDto(team2));
		}
		return resultDto;
	}

	@Override
	@Cacheable(value = "cacheTeamList")
	public Map<String, Object> getAllTeamsPage(Pageable paging) throws Exception {
		Page<Team> teamPage = teamRepository.findAll(paging);
		List<TeamDto> resultDto = new ArrayList<>();
		List<Team> TeamsList = teamPage.getContent();
		for (Team Team : TeamsList) {
			resultDto.add(this.getTeamDto(Team));
		}
		Map<String, Object> response = new HashMap<>();
		response.put("teams", TeamsList);
		response.put("currentPage", teamPage.getNumber());
		response.put("totalItems", teamPage.getTotalElements());
		response.put("totalPages", teamPage.getTotalPages());
		return response;
	}

}
