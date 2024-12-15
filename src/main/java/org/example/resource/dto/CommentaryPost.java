package org.example.resource.dto;

import lombok.Data;

@Data
public class CommentaryPost {
    public String content;

    public Long postId;

    public Long userId;

    public Long commentaryToId;
}
