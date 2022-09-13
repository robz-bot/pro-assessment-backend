package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.promantus.Assessment.Entity.Team;

public interface TeamRepository extends MongoRepository<Team, String> {

	Team findById(Long id);

	Team findByTeam(String team);

	Team getTeamByTeamIgnoreCase(String team);

	@Query("{'team': {$regex: ?0,$options: \"i\"} }})")
	List<Team> findByTeamRegex(String keyword);

}
