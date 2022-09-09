package com.promantus.Assessment.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.Dto.CategoryDto;
import com.promantus.Assessment.Entity.Category;
import com.promantus.Assessment.Repository.CategoryRepository;
import com.promantus.Assessment.Service.CategoryService;
import com.promantus.Assessment.Service.CommonService;


@Service
public class CategoryServiceImpl implements CategoryService
{
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CommonService commonService;
	
	@Override
	public CategoryDto addCategory(CategoryDto categoryDto,String lang) throws Exception 
	{
		CategoryDto resultDto = new CategoryDto();
		if (categoryRepository.findByCategory(categoryDto.getCategory())== null)
		{
			Category category = new Category();
			category.setId(commonService.nextSequenceNumber());
			category.setCategory(categoryDto.getCategory());		
			categoryRepository.save(category);

		}
			resultDto.setMessage("Record added successfully");
			return resultDto;

	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> CategorysList = categoryRepository.findAll();

		List<CategoryDto> CategoryDtoList = new ArrayList<CategoryDto>();
		for (Category Category : CategorysList) {
			CategoryDtoList.add(this.getCategoryDto(Category));
		}

		return CategoryDtoList;
	}

	private CategoryDto getCategoryDto(Category Category) {
		CategoryDto CategoryDto=new CategoryDto();
		CategoryDto.setId(Category.getId());
		CategoryDto.setCategory(Category.getCategory());
		return CategoryDto;	
		}

	@Override
	public CategoryDto getCategoryById(Long id) 
	{

		Category category = categoryRepository.findById(id);

		return category != null ? this.getCategoryDto(category) : new CategoryDto();
	}

	
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, String lang) {
		CategoryDto resultDto = new CategoryDto();
		Category category = categoryRepository.findById(categoryDto.getId());

		if (category == null) {
		
			resultDto.setMessage("User does not exist");
			return resultDto;
		}

		category.setCategory(categoryDto.getCategory());
		categoryRepository.save(category);
		resultDto.setMessage("Record Updated successfully");
		return resultDto;

		
	}

	@Override
	public CategoryDto deleteCategoryById(Long id) {
		CategoryDto resultDto=new CategoryDto();
		Category user=categoryRepository.findById(id);
		if(user==null)
		{
			
			resultDto.setMessage("data does not exist");
			return resultDto;
		}
		return resultDto;
	}
	
	
	
	
	
}
