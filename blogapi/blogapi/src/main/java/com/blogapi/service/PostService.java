package com.blogapi.service;

import com.blogapi.payload.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto PostDto);

    PostDto getPostById(long id);


    void deletepost(long id);

    PostDto updatePost(long id,PostDto postDto);//but this should return back PostDto not void ...whatever information i want that update info..in response section on postman

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);
}
