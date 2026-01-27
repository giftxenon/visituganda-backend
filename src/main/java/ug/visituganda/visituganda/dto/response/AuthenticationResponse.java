package ug.visituganda.visituganda.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder                 // ← This gives you .builder()
@NoArgsConstructor       // ← Needed for JSON deserialization
@AllArgsConstructor
public class AuthenticationResponse {
    String token;
    String userType;
    String redirectUrl;
    Long userId;     // new
    String username; // new
    String email;    // new
    String msisdn;   // new
}

