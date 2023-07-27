package kr.co.mz.sns.controller.user;

import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.user.RequestedFriendDto;
import kr.co.mz.sns.service.user.FriendService;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/users/friends")
public class FriendController {

  private final FriendService friendService;
  private final CurrentUserInfo currentUserInfo;

  @PostMapping("/{userName}")
  public ResponseEntity<RequestedFriendDto> request(@NotNull @RequestBody RequestedFriendDto requestedFriendDto) {

    friendService.friendRequest(currentUserInfo.getSeq(), requestedFriendDto);

  }


}
