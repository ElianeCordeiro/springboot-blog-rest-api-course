package com.springboot.blog.springbootblogrestapi.service;

import java.util.List;

import com.springboot.blog.springbootblogrestapi.payload.CategoryDto;

public interface CategoryService {
	CategoryDto addCategory(CategoryDto categoryDto);
	
	CategoryDto getCategoryDto(Long categoryId);
	
	List<CategoryDto> getAllCategories();
	
	CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
	
	void deleteCategory(Long categoryId);
}
