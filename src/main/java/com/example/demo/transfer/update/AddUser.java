package com.example.demo.transfer.update;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddUser {
    @NotNull(message = "Clien required")
    private UUID clientID;
    @NotNull(message = "organization required")
    private UUID orgID;
}
