package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.dto.RequestPreviewDTO;
import com.example.toolexchangeservice.model.entity.Request;
import com.example.toolexchangeservice.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/request")
@AllArgsConstructor
@CrossOrigin("*")
public class RequestController {
    private RequestService requestService;

    @PostMapping
    public Request createNewRequest(@RequestBody Request request){
        return this.requestService.saveRequest(request);
    }

    @GetMapping
    public Page<RequestPreviewDTO> getAllRequests(@SortDefault(value = "lastModified",
            direction = Sort.Direction.DESC) Pageable pageable){
        return this.requestService.getPagedRequestPreviews(pageable);
    }

    @DeleteMapping("{id}")
    public void deleteRequest(@PathVariable Long id){
        this.requestService.deleteRequestById(id);
    }

    @GetMapping("{id}")
    public Request getRequestById(@PathVariable Long id) {
        return this.requestService.getRequestById(id);
    }
}
