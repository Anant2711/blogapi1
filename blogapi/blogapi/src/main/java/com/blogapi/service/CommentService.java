package com.blogapi.service;

import com.blogapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);


    List<CommentDto> findCommentByPostId(long postId);

    void deleteCommentByPostId(long postId, long id);

    CommentDto getCommentById(long postId, long id);
    CommentDto updateComment(Long postId,Long commentId,CommentDto commentDto);
}
