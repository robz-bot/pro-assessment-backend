package com.promantus.Assessment.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

	Category findByCategory(String category);

	Category findById(long parseLong);

}
