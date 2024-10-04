package io.duskmare.artio.dtos.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseArtioResponse {
    private List<String> errors;
    private boolean success;
}
