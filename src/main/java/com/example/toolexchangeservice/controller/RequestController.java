package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.entity.Request;
import com.example.toolexchangeservice.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Request> getAllRequests(){
        return this.requestService.getAllRequests();
    }

    @GetMapping("{id}")
    public Request getRequestById(@PathVariable Long id) {
        return this.requestService.getRequestById(id);
    }
}