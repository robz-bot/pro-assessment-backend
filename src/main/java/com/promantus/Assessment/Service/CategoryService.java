package com.promantus.Assessment.Service;

import java.util.List;

import com.promantus.Assessment.Dto.CategoryDto;

public interface CategoryService {

	CategoryDto addCategory(CategoryDto categoryDto, String lang) throws Exception;

	List<CategoryDto> getAllCategory();

	CategoryDto getCategoryById(Long id);

	CategoryDto updateCategory(CategoryDto categoryDto, String lang);

	CategoryDto deleteCategoryById(Long id);

}
