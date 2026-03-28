package com.ems.posts.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOutput {

    private UUID postId;
    private String title;
    private String body;
    private String author;
    private int wordCount;
    private double calculatedValue;
}
