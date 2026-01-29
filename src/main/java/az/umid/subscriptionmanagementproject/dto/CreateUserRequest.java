 package az.umid.subscriptionmanagementproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

 public class CreateUserRequest {
    @NotBlank(message = "Full name cannot be empty")
    @Size(min = 3, message = "Full name must be at least 3 characters")
    private String fullName;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
