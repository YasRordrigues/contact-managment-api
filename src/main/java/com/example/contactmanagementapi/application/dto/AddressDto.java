package com.example.contactmanagementapi.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private Long id;
    @NotBlank(message = "Street is required.")
    private String street;

    @NotBlank(message = "Number is required.")
    private String number;

    @NotBlank(message = "Postal code is required.")
    @JsonProperty("postalCode")
    @Pattern(regexp = "\\d{5}-\\d{4}", message = "Postal code must be in the format XXXXX-XXXX.")
    private String postalCode;
}
