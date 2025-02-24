package com.optimal.api.services.external.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomUserResponse {
    private List<Result> results;
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        private Name name;
        private String email;
        private String gender;

        @JsonProperty("picture")
        private Picture picture;

        @JsonProperty("login")
        private Login login;

        @JsonProperty("location")
        private Location location;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        private String first;
        private String last;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Picture {
        private String large;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Login {
        private String username;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        private String country;
        private String state;
        private String city;
    }
}




