package cvut.fel.cz.thesis_helper.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {

        private String token;
        private String email;
        private String role;

        public AuthenticationResponseDto(String token, String email, String role) {
            this.token = token;
            this.email = email;
            this.role = role;
        }
}

