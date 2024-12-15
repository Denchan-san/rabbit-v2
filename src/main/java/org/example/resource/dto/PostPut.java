package org.example.resource.dto;

import lombok.Data;

@Data
public class PostPut {
    public String title;

    public String description;

    public String image;

    public Long threadId;

    public Long userId;
}
