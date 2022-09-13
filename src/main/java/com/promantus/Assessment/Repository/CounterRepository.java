
package com.promantus.Assessment.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.Counter;

public interface CounterRepository extends MongoRepository<Counter, String> {

}
