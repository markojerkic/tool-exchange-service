package com.example.toolexchangeservice.service.advice;

import com.example.toolexchangeservice.config.exception.ThreadNotFound;
import com.example.toolexchangeservice.model.dto.AdviceThreadDTO;
import com.example.toolexchangeservice.model.entity.AdviceThread;
import com.example.toolexchangeservice.model.entity.Image;
import com.example.toolexchangeservice.repository.AdviceThreadRepository;
import com.example.toolexchangeservice.service.AuthService;
import com.example.toolexchangeservice.service.image.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdviceThreadService {
    private final AdviceThreadRepository adviceThreadRepository;
    private final AuthService authService;
    private final ImageService imageService;

    public AdviceThread addNewThread(AdviceThread thread) {
        thread.setCreator(this.authService.getLoggedInUser());
        thread.setLastModified(new Date());

        AdviceThread savedThread = this.adviceThreadRepository.save(thread);
        thread.getImages().stream().map(image -> this.addImageToThread(image, savedThread))
                .forEach(this.imageService::saveImage);

        return savedThread;
    }

    private Image addImageToThread(Image image, AdviceThread thread) {
        image.setAdviceThread(thread);
        return image;
    }

    public AdviceThread getThreadById(Long id) {
        return this.adviceThreadRepository.findById(id).orElseThrow(() -> new
                ThreadNotFound("Thread savjeta s id-om " + id + " nije pronađen"));
    }

    public Page<AdviceThreadDTO> getPagedThreads(Pageable pageable) {
        return this.adviceThreadRepository.findAll(pageable).map(this::mapToThreadDto);
    }

    public AdviceThreadDTO mapToThreadDto(AdviceThread thread) {
        AdviceThreadDTO previewDTO = new AdviceThreadDTO();
        previewDTO.setId(thread.getId());
        previewDTO.setTitle(thread.getTitle());
        previewDTO.setDetails(thread.getDetails());
        previewDTO.setUserCreated(thread.getCreator().getUsername());
		previewDTO.setLastModified(thread.getLastModified());
        previewDTO.setThumbnailImageUuid(this.getThumbnailImage(thread));
        previewDTO.setNumComments(thread.getNumAdvices());
        previewDTO.setIsBest(thread.getCreator().getIsBestHandyman());

        return previewDTO;
    }

    private UUID getThumbnailImage(AdviceThread thread) {
        return this.imageService.getImagesByThreadId(thread.getId()).stream().map(Image::getUuid)
                .findFirst().orElse(null);
    }

    public void updateNumComments(Long id) {
        AdviceThread thread = this.getThreadById(id);
        this.adviceThreadRepository.updateNumComments(thread.getNumAdvices() + 1, id);
    }
}
