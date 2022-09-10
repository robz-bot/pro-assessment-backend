package com.promantus.Assessment.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.Team;

public interface TeamRepository extends MongoRepository<Team, String> {

	Team findById(Long id);

	Team findByTeam(String team);



}
