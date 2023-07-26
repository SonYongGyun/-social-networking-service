package kr.co.mz.sns.auditing;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Optional;
import kr.co.mz.sns.config.security.CustomUserDetails;
import kr.co.mz.sns.entity.PostEntity;
import kr.co.mz.sns.entity.UserEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomAuditingEntityListener extends AuditingEntityListener {

    @PrePersist
    public void prePersist(Object target) {
        if (target instanceof PostEntity postEntity) {
            if (getCurrentUserId().isPresent()) {
                var userEntity = new UserEntity();
                userEntity.setSeq(getCurrentUserId().get());
                postEntity.setUserEntity(userEntity);
            }
        }
    }

    @PreUpdate
    public void preUpdate(Object target) {
        if (target instanceof PostEntity postEntity) {
            if (getCurrentUserId().isPresent()) {
                var userEntity = new UserEntity();
                userEntity.setSeq(getCurrentUserId().get());
                postEntity.setUserEntity(userEntity);
            }
        }
    }

    // 사용자의 식별자를 가져오는 메서드 (이 메서드는 필요에 따라 구현 가능)
    private Optional<Long> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        var customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return Optional.of(customUserDetails.getUserDto().getSeq());
    }
}
