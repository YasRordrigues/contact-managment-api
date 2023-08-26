package com.example.contactmanagementapi.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactDto {
    private Long id;

    @NotBlank(message = "Name is required.")
    @NotEmpty(message = "Name is required.")
    private String name;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    @NotEmpty(message = "Email is required.")
    private String email;

    @NotBlank(message = "Phone is required.")
    @NotEmpty(message = "Phone is required.")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits long.")
    private String phone;

    @NotNull(message = "Birthdate is required.")
    @JsonProperty("birthDate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @NotNull(message = "Addresses is required.")
    @Valid
    private List<AddressDto> addresses;
}
