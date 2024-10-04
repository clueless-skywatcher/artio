package io.duskmare.artio.dtos.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribePostsResponse extends BaseArtioResponse {
    private List<DescribePostResponse> posts;
}
