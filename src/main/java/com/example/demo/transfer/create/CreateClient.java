package com.example.demo.transfer.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateClient {
    @NotBlank(message = "Username required")
    private String username;
    @NotNull(message = "Mobile number required")
    private Long mobile;
}
