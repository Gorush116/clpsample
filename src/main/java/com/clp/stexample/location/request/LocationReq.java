package com.clp.stexample.location.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
public class LocationReq {

    @NotBlank
    @Size(min = 1, max = 100)
    @Schema(description = "The name of the location.", example = "Central Park")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    @Schema(description = "The country code in ISO 3166-1 alpha-3 format.", example = "USA")
    private String countryCode;

    @DecimalMin(value = "-90.0", inclusive = true)
    @DecimalMax(value = "90.0", inclusive = true)
    @Schema(description = "The latitude of the location, ranging from -90.0 to 90.0.", example = "40.7128")
    private Double latitude;

    @DecimalMin(value = "-180.0", inclusive = true)
    @DecimalMax(value = "180.0", inclusive = true)
    @Schema(description = "The longitude of the location, ranging from -180.0 to 180.0.", example = "-74.0060")
    private Double longitude;

    @Min(20)
    @Max(500000)
    @Schema(description = "The radius of the region in meters.", example = "1000")
    private Integer regionRadius;

    @Pattern(regexp = "^(F|C)$")
    @Schema(description = "The temperature scale, either 'F' for Fahrenheit or 'C' for Celsius.", example = "C")
    private String temperatureScale;

    @Schema(description = "The locale for the location, e.g., en-US.")
    private String locale;

    @Schema(description = "Additional properties related to the location.")
    private Map<String, Object> additionalProperties;

    @Schema(description = "The parent location object.")
    private LocationParent parent;

    @Getter
    @Setter
    @Schema(description = "Parent location information.")
    public static class LocationParent {

        @NotBlank
        @Size(min = 7, max = 14)
        @Schema(description = "The type of the parent location.", example = "region")
        private String type;

        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
        @Schema(description = "The unique identifier of the parent location in UUID format.", example = "123e4567-e89b-12d3-a456-426614174000")
        private String id;
    }
}
