package com.dimitarrradev.photoapp.api.users.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequestModel {
        @NotBlank(message = "First name cannot be null")
        @Size(min = 2, message = "First name must not be less than 2 characters")
        private String firstName;
        @NotBlank(message = "Last name cannot be null")
        @Size(min = 2, message = "Last name must not be less than 2 characters")
        private String lastName;
        @NotBlank(message = "Email cannot be null")
        @Email
        private String email;
        @NotBlank(message = "Password cannot be null")
        @Size(min = 8, max = 16, message = "Password must be equal or greater than 8 characters and less than 16 characters")
        private String password;

        public CreateUserRequestModel() {
        }

        public CreateUserRequestModel(String firstName, String lastName, String email, String password) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.password = password;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}
