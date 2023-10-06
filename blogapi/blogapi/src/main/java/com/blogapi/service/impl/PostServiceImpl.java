package com.blogapi.service.impl;

import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.PostDto;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    //@Autowired
    private PostRepository PostRepo;//IN THE RECENT version of spring boot instead @autowired for dependency injection

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {//instead @autowired add this constructor there 2 ways we can inject dependency injection

        this.PostRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    //we can use constructore based dependency
    @Override
    public PostDto createPost(PostDto PostDto) {//now here we have to convert dto to entity because you cannot save into db
        //Post post = new Post();//it shuld save into db but it cannot save dto into db we create entity object
        // post.setTitle(PostDto.getTitle());
        // post.setDescription(PostDto.getDescription());//now we content dto to entity class..now entity object can go to db
        //post.setContent(PostDto.getContent());
        Post post = mapToEntity(PostDto);
        Post savedpost = PostRepo.save(post);//after saving the post i want to know what is saved show we have to do introduce new local veriable saved post
        //after it saved the post it will give a new object with a content telling what did i saved in the db now savepost has the content that has gone to db take that and convert into dto

        PostDto dto = mapToDto(savedpost);//call map tp dto here maptodto taking entity object entity object go to mapdtp method return dto back here and will take it in a local variable

        //PostDto dto = new PostDto();
        //dto.setId(savedpost.getId());
        // dto.setTitle(savedpost.getTitle());
        // dto.setDescription(savedpost.getDescription());
        // dto.setContent(savedpost.getContent());
        return dto;//and that dto we are returning back
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = PostRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)); //lambdas expression used if the record found half part of code will run if not half part of code going to run
        //get() will convert optional object to post object ..now you cannot return post we have to convert post to dto
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir) {//this method cannot work with string we have to use parse show we can int to string we can use
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);//use parse here
        Page<Post> posts = PostRepo.findAll(pageable);//you cannot return back post entity list you have to return back dto ..stream api
        List<Post> content = posts.getContent();
        return content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public void deletepost(long id) {//before deleting post check whether post exits or not
        Post post = PostRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        PostRepo.deleteById(id);

    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = PostRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));

        Post updatedpost = mapToEntity(postDto);//because what ebtity is returning back doesnot have id that's why we have to do this
        Post updatedPostInfo = PostRepo.save(updatedpost);//before sasving
        return mapToDto(updatedPostInfo);//and you supply dto

    }



    PostDto mapToDto(Post post){//whenever you want to convert entity to dto call this method just supply the entity object this will convert into dto object
       //PostDto dto = new PostDto();
      PostDto dto = modelMapper.map(post,PostDto.class);
       //dto.setId(post.getId());
       //dto.setTitle(post.getTitle());
       //dto.setDescription(post.getDescription());
       //dto.setContent(post.getContent());
       return dto;
   }
    Post mapToEntity(PostDto postDto){
       // Post post = new Post();
        Post post = modelMapper.map(postDto, Post.class);
        //post.setTitle(postDto.getTitle());
       // post.setDescription(postDto.getDescription());
       // post.setContent(postDto.getContent());
        return post;
    }

}
