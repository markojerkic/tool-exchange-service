
package com.example.toolexchangeservice.model.location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "formatted_address",
    "geometry",
    "icon",
    "icon_background_color",
    "icon_mask_base_uri",
    "name",
    "place_id",
    "plus_code",
    "reference",
    "types"
})
@Generated("jsonschema2pojo")
public class Result {

    @JsonProperty("formatted_address")
    public String formattedAddress;
    @JsonProperty("geometry")
    public Geometry geometry;
    @JsonProperty("icon")
    public String icon;
    @JsonProperty("icon_background_color")
    public String iconBackgroundColor;
    @JsonProperty("icon_mask_base_uri")
    public String iconMaskBaseUri;
    @JsonProperty("name")
    public String name;
    @JsonProperty("place_id")
    public String placeId;
    @JsonProperty("plus_code")
    public PlusCode plusCode;
    @JsonProperty("reference")
    public String reference;
    @JsonProperty("types")
    public List<String> types = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
