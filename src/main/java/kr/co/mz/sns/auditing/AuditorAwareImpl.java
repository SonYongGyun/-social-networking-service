package kr.co.mz.sns.auditing;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import kr.co.mz.sns.config.security.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public @NotNull Optional<Long> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (
            authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")
        ) {
            return Optional.empty();
        }
        var customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return Optional.of(customUserDetails.getUserDto().getSeq());
    }
}
