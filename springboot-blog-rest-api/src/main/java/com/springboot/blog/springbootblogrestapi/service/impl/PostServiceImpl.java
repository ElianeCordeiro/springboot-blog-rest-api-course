package com.springboot.blog.springbootblogrestapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;

@Service
public class PostServiceImpl  implements PostService{

	private PostRepository postRepositoty;

	private ModelMapper mapper;
	
	private CategoryRepository categoryRepository;
	
	public PostServiceImpl(PostRepository postRepositoty, ModelMapper mapper, CategoryRepository categoryRepository) {
		this.postRepositoty = postRepositoty;
		this.mapper = mapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		//convert DTO to entity
		Post post = mapToEntity(postDto);
		post.setCategory(category);
		Post newPost = postRepositoty.save(post);
		
		//convert entity to DTO
		PostDto postResponse = mapToDto(newPost);
		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		//create pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepositoty.findAll(pageable);
		
		//get content for page object
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDto> content = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}
	
	@Override
	public PostDto getPostById(long id) {
		Post post = postRepositoty.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post",  "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		// get post by id from the database
		Post post = postRepositoty.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post",  "id", id));
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		
		Post updatePost = postRepositoty.save(post);
		
		return mapToDto(updatePost);
	}
	
	@Override
	public void deletePostById(long id) {
		// get post by id from the database
		Post post = postRepositoty.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post",  "id", id));
		postRepositoty.delete(post);
		
	}
	
	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		List<Post> posts = postRepositoty.findByCategoryId(categoryId);
		
		return posts.stream().map((post) -> mapToDto(post))
				.collect(Collectors.toList());
	}
	
	
	//convert Entity into Dto
	private PostDto mapToDto(Post post) {
		
		PostDto postDto = mapper.map(post, PostDto.class);
		
//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
//		
		return postDto;
	}

	//convert DTO to entity
	private Post mapToEntity(PostDto postDto) {
		
		Post post = mapper.map(postDto, Post.class);
		
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
//		
		return post;
	}
}
