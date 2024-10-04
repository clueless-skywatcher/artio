package io.duskmare.artio.dtos;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtioCommentDTO {
    private Long commentId;
    private Long postId;
    private Instant createdAt;

    @NotBlank
    private String text;
    private String author;
}
