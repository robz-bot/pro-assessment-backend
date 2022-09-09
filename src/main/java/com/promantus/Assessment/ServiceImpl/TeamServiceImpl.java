package com.promantus.Assessment.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.Dto.TeamDto;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.TeamService;
	
	@Service
	public class TeamServiceImpl implements TeamService {

		private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

		@Autowired
		TeamRepository teamRepository;
		
		@Autowired
		CommonService commonService;
		
		@Override
		public Boolean checkTeamName(String teamName) throws Exception{

			if (teamRepository.findById(teamName) != null) {
				return true;
			}

			return false;
		}

		@Override
		public TeamDto addTeam(final TeamDto teamDto, String lang) throws Exception {

			TeamDto resultDto = new TeamDto();
			if (teamRepository.findById(teamDto.getId()) == null) 
			{
				Team team = new Team();
				team.setId(commonService.nextSequenceNumber());
				team.setTeam(teamDto.getTeam());
				team.setCreatedOn(LocalDateTime.now());
				
				teamRepository.save(team);
			}	
			resultDto.setMessage("Team added successfully");
	        return resultDto;
		}

			
		@Override
		public List<TeamDto> getAllTeams() {
			List<Team> TeamsList = teamRepository.findAll();

			List<TeamDto> TeamDtoList = new ArrayList<TeamDto>();
			for (Team Team : TeamsList) {
				TeamDtoList.add(this.getTeamDto(Team));
			}

			return TeamDtoList;
		}
		
		
		private TeamDto getTeamDto(Team team) {
			TeamDto teamDto=new TeamDto();

			teamDto.setId(team.getId());
			teamDto.setTeam(team.getTeam());
			teamDto.setCreatedOn(team.getCreatedOn());
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
		
			teamRepository.save(team);
			resultDto.setMessage("Record Updated successfully");
			return resultDto;
	       
		}



		@Override
		public TeamDto deleteTeamById(String teamId) {
			TeamDto resultDto=new TeamDto();
			Team team=teamRepository.findById(Long.parseLong(teamId));
			if(team==null)
			{
				
				resultDto.setMessage("data does not exist");
				return resultDto;
			}
			
			teamRepository.delete(team);
			resultDto.setMessage("Record Deleted successfully");
			return resultDto;
		}

		@Override
		public TeamDto getTeamById(String teamId) throws Exception{
			
			Team team = teamRepository.findById(Long.parseLong(teamId));
			
			return team != null ? this.getTeamDto(team) : new TeamDto();

		}


		

}
