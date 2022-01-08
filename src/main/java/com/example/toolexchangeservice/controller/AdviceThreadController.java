package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.dto.AdviceThreadDTO;
import com.example.toolexchangeservice.model.entity.AdviceThread;
import com.example.toolexchangeservice.service.advice.AdviceThreadService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/thread")
@AllArgsConstructor
@CrossOrigin("*")
public class AdviceThreadController {
    private AdviceThreadService threadService;

    @PostMapping
    public AdviceThread createNewThread(@RequestBody @Validated AdviceThread thread){
        return this.threadService.addNewThread(thread);
    }

    @GetMapping
    public Page<AdviceThreadDTO> getAllThreads(@SortDefault(value = "lastModified",
            direction = Sort.Direction.DESC) Pageable pageable){
        return this.threadService.getPagedThreads(pageable);
    }

    @GetMapping("{id}")
    public AdviceThread getThreadById(@PathVariable Long id) {
        return this.threadService.getThreadById(id);
    }

}
