package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.exception.RequestNotFoundException;
import com.example.toolexchangeservice.model.dto.RequestPreviewDTO;
import com.example.toolexchangeservice.model.entity.Request;
import com.example.toolexchangeservice.repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final AuthService authService;

    public Request saveRequest(Request requestDetail){
        requestDetail.setCreator(this.authService.getLoggedInUser());
        requestDetail.setLastModified(new Date());
        return this.requestRepository.save(requestDetail);
    }

    public Request getRequestById(Long id) {
        return this.requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException("Zahtjev s id-om " + id + " nije pronađen"));
    }

    public Page<RequestPreviewDTO> getPagedRequestPreviews(Pageable pageable) {
        return this.requestRepository.findAll(pageable).map(this::mapPreviewDto);
    }

    private RequestPreviewDTO mapPreviewDto(Request req) {
        RequestPreviewDTO previewDto = new RequestPreviewDTO();
        previewDto.setId(req.getId());
        previewDto.setTitle(req.getTitle());
        previewDto.setDetails(req.getDetails());
        previewDto.setUserCreated(req.getCreator().getUsername());

        return previewDto;
    }
}
