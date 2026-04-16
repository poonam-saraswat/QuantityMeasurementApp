package com.app.quantitymeasurement.dto.request;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
