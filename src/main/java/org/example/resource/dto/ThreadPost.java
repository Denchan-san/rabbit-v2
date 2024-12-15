package org.example.resource.dto;

import lombok.Data;

@Data
public class ThreadPost {
    public String title;

    public String description;

    public String image;

    public Long userId;
}
