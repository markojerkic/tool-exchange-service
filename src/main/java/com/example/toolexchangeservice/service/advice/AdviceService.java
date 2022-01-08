package com.example.toolexchangeservice.service.advice;

import com.example.toolexchangeservice.config.exception.AdviceNotFound;
import com.example.toolexchangeservice.model.dto.AdviceDTO;
import com.example.toolexchangeservice.model.dto.AdviceLike;
import com.example.toolexchangeservice.model.entity.Advice;
import com.example.toolexchangeservice.repository.AdviceRepository;
import com.example.toolexchangeservice.service.AuthService;
import com.example.toolexchangeservice.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class AdviceService {
    private final AdviceRepository adviceRepository;
    private final MailService mailService;
    private final AuthService authService;

    public Advice addAdviceToThread(Advice advice) {
        advice.setCreator(this.authService.getLoggedInUser());
        advice.setLastModified(new Date());
        Advice savedAdvice = this.adviceRepository.save(advice);

        this.mailService.sendAdviceAnsweredMail(advice.getCreator().getEmail(),
                this.authService.getLoggedInUser().getUsername(), savedAdvice.getParentThread().getTitle(),
                savedAdvice.getMessage());

        return savedAdvice;
    }

    public void likeAdvice(AdviceLike adviceLike) {
        Advice advice = this.getAdviceById(adviceLike.getAdviceId());
        advice.setIsLiked(adviceLike.getLike());
        this.adviceRepository.save(advice);
        if (adviceLike.getLike()) {
            // TODO send mail
        }
    }

    public Page<AdviceDTO> getAdvicePage(Pageable pageable, Long adviceThreadId) {
        return this.adviceRepository.findAllByParentThread_Id(pageable, adviceThreadId).map(this::mapToDTO);
    }

    private AdviceDTO mapToDTO(Advice advice) {
        AdviceDTO dto = new AdviceDTO();
        dto.setId(advice.getId());
        dto.setMessage(advice.getMessage());
        dto.setLastModified(advice.getLastModified());
        dto.setUserCreated(advice.getCreator().getUsername());
        dto.setIsLiked(advice.getIsLiked());
        return dto;
    }

    public Advice getAdviceById(Long id) {
        return this.adviceRepository.findById(id).orElseThrow(() ->
                new AdviceNotFound("Advice " + id + " not found"));
    }
}
