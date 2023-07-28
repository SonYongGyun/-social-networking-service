package kr.co.mz.sns.controller.user;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.co.mz.sns.dto.user.GenericFriendDto;
import kr.co.mz.sns.dto.user.ListFriendDto;
import kr.co.mz.sns.dto.user.RequestedFriendDto;
import kr.co.mz.sns.service.user.FriendService;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @PostMapping()
  public ResponseEntity<RequestedFriendDto> request(@NotNull @RequestBody RequestedFriendDto requestedFriendDto) {

    friendService.friendRequest(currentUserInfo.getSeq(), requestedFriendDto);
    return ResponseEntity.ok(new RequestedFriendDto());
  }

  @GetMapping("/{friendSep}")
  public ResponseEntity<GenericFriendDto> aFriend(@NotNull @PathVariable Long friendSep) {
    return ResponseEntity.ok(friendService.findOne(friendSep));
  }

  @GetMapping("/list")
  public ResponseEntity<List<ListFriendDto>> allFriends() {
    return ResponseEntity.ok(friendService.findAll(currentUserInfo.getSeq()));
  }

}
