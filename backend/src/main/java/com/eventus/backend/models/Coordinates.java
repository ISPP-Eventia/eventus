package com.eventus.backend.models;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Coordinates {

    @NotBlank
    private Long latitude;

    @NotBlank
    private Long longitude;

    public Coordinates(String latitude, String longitude) {
        this.latitude = Long.valueOf(latitude);
        this.longitude = Long.valueOf(longitude);
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }
}
