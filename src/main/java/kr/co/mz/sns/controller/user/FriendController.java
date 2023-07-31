package kr.co.mz.sns.controller.user;

import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.user.friend.FriendDetailDto;
import kr.co.mz.sns.dto.user.friend.InsertFriendRelationshipDto;
import kr.co.mz.sns.service.user.FriendService;
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

  @PostMapping("/request")
  public ResponseEntity<InsertFriendRelationshipDto> request(
      @NotNull @RequestBody InsertFriendRelationshipDto insertFriendRelationshipDto) {
    return ResponseEntity.ok(
        friendService.request(insertFriendRelationshipDto)
    );
  }

  @GetMapping("/request/accept")
  public ResponseEntity<String> accept(InsertFriendRelationshipDto insertFriendRelationshipDto) {
    insertFriendRelationshipDto.setUserSeq(currentUserInfo.getSeq());
    friendService.add(insertFriendRelationshipDto);
    return ResponseEntity.ok(":");
  }

  @GetMapping("/{friendSep}")
  public ResponseEntity<FriendDetailDto> showOne(@NotNull @PathVariable Long friendSep) {
    return ResponseEntity.ok(
        friendService.find(friendSep)
    );
  }

//  @GetMapping("/list")
//  public ResponseEntity<List<ListFriendDto>> showAll() {
//    return ResponseEntity.ok(friendService.findAll(currentUserInfo.getSeq()));
//  }

}
