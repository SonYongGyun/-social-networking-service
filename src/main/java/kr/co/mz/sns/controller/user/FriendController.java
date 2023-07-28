package kr.co.mz.sns.controller.user;

import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.FR_WAITING_PERMIT_REQUEST;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.co.mz.sns.dto.user.FriendDetailDto;
import kr.co.mz.sns.dto.user.InsertFriendRelationshipDto;
import kr.co.mz.sns.dto.user.ListFriendDto;
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

  @PostMapping("/request")
  public ResponseEntity<InsertFriendRelationshipDto> request(
      @NotNull @RequestBody InsertFriendRelationshipDto insertFriendRelationshipDto) {
    insertFriendRelationshipDto.setUserSeq(currentUserInfo.getSeq());
    insertFriendRelationshipDto.setStatus(FR_WAITING_PERMIT_REQUEST);
    return ResponseEntity.ok(friendService.request(insertFriendRelationshipDto));
  }

  @GetMapping("/{friendSep}")
  public ResponseEntity<FriendDetailDto> aFriend(@NotNull @PathVariable Long friendSep) {
    return ResponseEntity.ok(friendService.find(friendSep));
  }

  @GetMapping("/list")
  public ResponseEntity<List<ListFriendDto>> allFriends() {
    return ResponseEntity.ok(friendService.findAll(currentUserInfo.getSeq()));
  }

}
