package com.rungroup.runApp.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Data
@Builder
public class ClubDto {
    private Long id;
    @NotEmpty(message= "Title should not be empty")
    private String title;
    @NotEmpty(message= "Photo link should not be empty")
    private String photoUrl;
    @NotEmpty(message= "Content should not be empty")
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
