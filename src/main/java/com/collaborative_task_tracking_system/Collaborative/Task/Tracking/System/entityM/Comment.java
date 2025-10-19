package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;  
    private String userId; // user who wrote the comment
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
