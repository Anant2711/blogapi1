package com.blogapi.controller;

import com.blogapi.entity.Post;
import com.blogapi.payload.PostDto;
import com.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
   // @Autowired
    private PostService postService;

    public PostController(PostService postService) {//we can use @autowired or we can generate construtor based dependency injection
        this.postService = postService;
    }

    //http://localhost:8080/api/posts1
    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping
    //enter this url post mapping will call postdto (postman select post enter url) after post method give jason object
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto PostDto, BindingResult result) {//why response entity it will return response back to postman if you want response back to postman we have to use reponse entity
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto savedDto = postService.createPost(PostDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);//WHENEVER YOU'RE CREATING A RECORD STATUS CODE SHOULD BE ..201
    }//@REQUESTBODY -WHICH WILL TAKE TO CONTENT JASON TO PostDto  //now after submitting jason content via url because post  will go to controller (postdto) now take this dto and give it service layer

    //http://localhost:8080/api/posts/1  //this is now path parameter not ?id=1 (query parameter)
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {//when we are using queryparam=we were using @requestparam(supply id)longid) but here we are supply value with path param here we have to use path variable
        PostDto dto = postService.getPostById(id);  //STATUS CODE FOR FETCHING RECORD FROM DB ...RECORD ID FOUND 200
        return new ResponseEntity<>(dto, HttpStatus.OK);//OK MEANS 200

    }
    //http://localhost:8080/api/posts?pageNo=1&pageSize=3&sortBy=title&sortDir=asc
    @GetMapping()
    public List<PostDto> getAllPosts(
        @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,  //pagination ...
        @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
        @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
        @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){

        return postService.getAllPosts(pageNo ,pageSize,sortBy,sortDir);

    }
    //http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable("id") long id) {
        postService.deletepost(id);
        return new ResponseEntity<>("post is deleted!!", HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/{id}")
    public ResponseEntity<PostDto>updatePost(@PathVariable("id") long id,@RequestBody PostDto postdto){
        PostDto dto = postService.updatePost(id, postdto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}