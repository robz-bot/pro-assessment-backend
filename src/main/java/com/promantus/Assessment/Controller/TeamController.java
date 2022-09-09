package com.promantus.Assessment.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.TeamDto;
import com.promantus.Assessment.Service.TeamService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class TeamController extends CommonController {

	private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

	@Autowired
	private TeamService teamService;


	@PostMapping("/addTeam")
	public TeamDto addTeam(@RequestBody TeamDto teamDto, @RequestHeader(name = "lang", required = false) String lang) {

		TeamDto resultDto = new TeamDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
			
			//Team
			if (teamDto.getTeam() == null ||teamDto.getTeam().isEmpty()){
				errorParam.append("Team");
			}
			
			if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = teamService.addTeam(teamDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@GetMapping("/getAllTeams")
	public List<TeamDto> getAllTeams(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return teamService.getAllTeams();

		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return new ArrayList<TeamDto>();
	}

	 
	@GetMapping("/getTeamById/{teamId}")
	public TeamDto getTeamById(@PathVariable String teamId,
			@RequestHeader(name = "lang", required = false) String lang) {

		TeamDto teamDto = new TeamDto();
		try {
			teamDto = teamService.getTeamById(teamId);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return teamDto;
	}
	
	@PostMapping("/checkTeamName")
	public Boolean checkTeamName(@RequestBody String teamName,
			@RequestHeader(name = "lang", required = false) String lang) {
		try {
			return teamService.checkTeamName(teamName);
		} catch (final Exception e) {
			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return true;
	}

	@PutMapping("/updateTeam")
	public TeamDto updateTeam(@RequestBody TeamDto teamDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		TeamDto resultDto = new TeamDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
	
				//Team
				if (teamDto.getTeam() == null ||teamDto.getTeam().isEmpty()){
					errorParam.append("Team");
				}

				if (errorParam.length() > 0) {
				resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = teamService.updateTeam(teamDto, lang);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));
		}

		return resultDto;
	}

	@DeleteMapping("/deleteTeamById/{teamId}")
	public TeamDto deleteTeamById(@PathVariable String teamId,
			@RequestHeader(name = "lang", required = false) String lang) {

		TeamDto resultDto = new TeamDto();
		try {

			return teamService.deleteTeamById(teamId);

		} catch (final Exception e) {

			resultDto.setStatus(AssessmentConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(AssessmentUtil.getErrorMessage(e));

			return resultDto;
		}
	}

	

}