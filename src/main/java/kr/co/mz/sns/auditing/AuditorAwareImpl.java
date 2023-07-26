package kr.co.mz.sns.auditing;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import kr.co.mz.sns.config.security.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public @NotNull Optional<Long> getCurrentAuditor() {
        var customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        return Optional.of(customUserDetail.getUserDto().getSeq());
    }
}
