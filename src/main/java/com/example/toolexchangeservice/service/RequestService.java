package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.exception.RequestNotFoundException;
import com.example.toolexchangeservice.model.entity.Request;
import com.example.toolexchangeservice.repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    public Request saveRequest(Request requestDetail){
        return this.requestRepository.save(requestDetail);
    }

    public List<Request> getAllRequests(){
        return this.requestRepository.findAll();
    }

    public Request getRequestById(Long id) {
        return this.requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException("Zahtjev s id-om " + id + " nije pronađen"));
    }
}
