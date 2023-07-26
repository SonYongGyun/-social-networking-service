//package kr.co.mz.sns.auditing;
//
//import jakarta.validation.constraints.NotNull;
//import java.util.Optional;
//import kr.co.mz.sns.config.security.CustomUserDetails;
//import kr.co.mz.sns.dto.UserDto;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuditorAwareImpl implements AuditorAware<UserDto> {
//
//    @Override
//    public @NotNull Optional<UserDto> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//        var customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        return Optional.of(customUserDetails.getUserDto());
//    }
//}
