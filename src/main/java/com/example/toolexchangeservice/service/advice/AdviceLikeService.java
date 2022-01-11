package com.example.toolexchangeservice.service.advice;

import com.example.toolexchangeservice.model.constants.ToggleAction;
import com.example.toolexchangeservice.model.dto.AdviceToggle;
import com.example.toolexchangeservice.model.entity.Advice;
import com.example.toolexchangeservice.model.entity.AdviceIsLiked;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.repository.AdviceLikeRepository;
import com.example.toolexchangeservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdviceLikeService {
    private final AdviceLikeRepository adviceLikeRepository;
    private final AuthService authService;

    public Integer toggleAdvice(AdviceToggle toggle) {
        UserDetail userDetail = this.authService.getLoggedInUser();

        Advice advice = new Advice();
        advice.setId(toggle.getAdviceId());

        AdviceIsLiked like = this.adviceLikeRepository.findByByUser_IdAndAdvice_Id(userDetail.getId(),
                        toggle.getAdviceId())
                .orElseGet(AdviceIsLiked::new);
        if (toggle.getToggleAction().equals(ToggleAction.DISLIKE)) {
            this.adviceLikeRepository.delete(like);
        } else {
            like.setAdvice(advice);
            like.setByUser(userDetail);
            this.adviceLikeRepository.save(like);
        }

        return this.getNumLiked(advice);
    }

    public Integer getNumLiked(Advice advice) {
        return this.adviceLikeRepository.countByAdvice_Id(advice.getId());
    }

    public Boolean getIsLikedByMe(Advice advice) {
        return this.adviceLikeRepository.existsByAdviceAndByUser(advice, this.authService.getLoggedInUser());
    }
}
