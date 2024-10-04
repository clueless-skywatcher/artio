package io.duskmare.artio.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribePostResponse extends BaseArtioResponse {
    private Long postId;
    private String title;
    private String text;
    private Integer likes;
    private String author;
    private Integer commentCount;
    private String duration;
}
