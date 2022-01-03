
package com.example.toolexchangeservice.model.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "results",
    "status"
})
@Data
public class LocationSearchResult {
    @JsonProperty("results")
    public List<Result> results = new ArrayList<>();
    @JsonProperty("status")
    public String status;

}
