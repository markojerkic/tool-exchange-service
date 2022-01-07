package com.example.toolexchangeservice.model.dto;

import com.example.toolexchangeservice.model.constants.OfferAcceptanceType;
import lombok.Data;

@Data
public class OfferAcceptance {
    private Long id;
    private OfferAcceptanceType type;
}
