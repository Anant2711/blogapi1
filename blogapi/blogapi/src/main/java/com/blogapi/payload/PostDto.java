package com.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data//encapsulation accieve one @data  annotation this will give getter and setter
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min=2,message="Title should be at least 2 character")
    private String title;

    @NotEmpty(message = "description is empty")
    @Size(min=4,max=10,message = "description should be at least 2 character ")
    private String description;

    @NotEmpty(message = "content is empty")
    private String content;
}
