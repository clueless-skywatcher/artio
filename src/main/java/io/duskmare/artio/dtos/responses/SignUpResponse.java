package io.duskmare.artio.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse extends BaseArtioResponse {
    private String username;
    private String role;
}
