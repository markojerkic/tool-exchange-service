package com.example.toolexchangeservice.model.dto;

import com.example.toolexchangeservice.model.constants.ExchangeOfferStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ExchangeOfferPreviewDto {
    private Long id;
    private String advertTitle;
    private Date suggestedTimeframe;
    private String from;
    private ExchangeOfferStatus status;
}
