package com.springboot.blog.springbootblogrestapi.service;

import java.util.List;

import com.springboot.blog.springbootblogrestapi.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentByPostId(long postId);

	CommentDto getCommentById(Long postId, Long commentId);

	CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest); 

	void deleteComment(Long postId, Long commentId);
}
