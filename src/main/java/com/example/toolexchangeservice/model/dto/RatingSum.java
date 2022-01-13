package com.example.toolexchangeservice.model.dto;

import com.example.toolexchangeservice.model.entity.UserDetail;
import lombok.Data;

@Data
public class RatingSum {
    private UserDetail user;
    private Integer sum;

}
