package com.example.toolexchangeservice.model.dto;

import com.example.toolexchangeservice.model.constants.ToggleAction;
import lombok.Data;

@Data
public class AdviceToggle {
    private Long adviceId;
    private ToggleAction toggleAction;
}
