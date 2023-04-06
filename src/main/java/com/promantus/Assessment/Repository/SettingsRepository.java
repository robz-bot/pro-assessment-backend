package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.promantus.Assessment.Entity.Settings;
import com.promantus.Assessment.Entity.Team;

public interface SettingsRepository extends MongoRepository<Settings, Long> {


}
