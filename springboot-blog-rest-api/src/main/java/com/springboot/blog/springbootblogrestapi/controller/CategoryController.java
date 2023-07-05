package com.springboot.blog.springbootblogrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.springbootblogrestapi.payload.CategoryDto;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	//build add category REST API
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto savedCategory = categoryService.addCategory(categoryDto);
		
		return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
	}
	
	//build get category REST API
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id")Long categoryId){
		CategoryDto categoryDto = categoryService.getCategoryDto(categoryId);
		return ResponseEntity.ok(categoryDto);
	}
	
	//build get all categories REST API
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	} 
	
	//build update category REST API
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{id}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
													@PathVariable("id")	Long categoryId){
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
	}
	
	//build delete Category REST API
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id")Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted successfully");
	}
	
}
