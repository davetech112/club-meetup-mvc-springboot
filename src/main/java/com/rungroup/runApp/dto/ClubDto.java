package com.rungroup.runApp.dto;

import com.rungroup.runApp.models.UserEntity;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

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
    private UserEntity createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    List<EventDto> events;
}
