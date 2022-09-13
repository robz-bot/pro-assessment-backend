package com.promantus.Assessment.Controller;

import java.util.ArrayList;
import java.util.List;

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

import com.promantus.Assessment.Dto.CategoryDto;
import com.promantus.Assessment.Service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/addCategory")
	public CategoryDto addCategory(@RequestBody CategoryDto categoryDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		CategoryDto resultDto = new CategoryDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

			if (categoryDto.getCategory() == null || categoryDto.getCategory().isEmpty()) {
				errorParam.append("Category");
			}
			resultDto = categoryService.addCategory(categoryDto, lang);

		} catch (final Exception e) {
			resultDto.setMessage(e.getMessage());

		}
		return resultDto;
	}

	@GetMapping("/getAllCategory")
	public List<CategoryDto> getAllCategory(@RequestHeader(name = "lang", required = false) String lang) {

		try {

			return categoryService.getAllCategory();
		} catch (final Exception e) {
		}
		return new ArrayList<CategoryDto>();
	}

	@GetMapping("/getCategoryById/{id}")
	public CategoryDto getCategoryById(@PathVariable Long id,
			@RequestHeader(name = "lang", required = false) String lang) {

		CategoryDto CategoryDto = new CategoryDto();
		try {
			CategoryDto = categoryService.getCategoryById(id);
		} catch (final Exception e) {

		}

		return CategoryDto;
	}

	@PutMapping("/updateCategory")
	public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		CategoryDto resultDto = new CategoryDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();

			if (categoryDto.getCategory() == null || categoryDto.getCategory().isEmpty()) {
				errorParam.append("Category");
			}
			resultDto = categoryService.updateCategory(categoryDto, lang);

		} catch (final Exception e) {

			resultDto.setMessage(e.getMessage());
		}
		return resultDto;

	}

	@DeleteMapping("/deleteCategoryById/{id}")
	public CategoryDto deleteCategoryById(@PathVariable Long id,
			@RequestHeader(name = "lang", required = false) String lang) {
		try {

			return categoryService.deleteCategoryById(id);

		} catch (final Exception e) {

			CategoryDto resultDto = new CategoryDto();

			resultDto.setMessage(e.getMessage());

			return resultDto;
		}
	}
}
