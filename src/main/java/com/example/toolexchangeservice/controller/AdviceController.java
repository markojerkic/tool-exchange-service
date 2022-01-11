package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.dto.AdviceDTO;
import com.example.toolexchangeservice.model.dto.AdviceToggle;
import com.example.toolexchangeservice.model.entity.Advice;
import com.example.toolexchangeservice.service.advice.AdviceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/advice")
@AllArgsConstructor
@CrossOrigin("*")
public class AdviceController {
    private AdviceService adviceService;

    @PostMapping
    public Advice createNewAd(@RequestBody @Validated Advice advice){
        return this.adviceService.addAdviceToThread(advice);
    }

    @GetMapping
    public Page<AdviceDTO> getAllAdvicesByThread(@SortDefault(value = "lastModified",
            direction = Sort.Direction.DESC) Pageable pageable, @RequestParam Long threadId) {
        return this.adviceService.getAdvicePage(pageable, threadId);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Integer toggleLike(@RequestBody AdviceToggle adviceToggle) {
        return this.adviceService.toggleLike(adviceToggle);
    }
}
