package kr.co.mz.sns.controller.user;

import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.user.friend.AFriendDto;
import kr.co.mz.sns.dto.user.friend.AcceptFriendRelationshipDto;
import kr.co.mz.sns.dto.user.friend.InsertFriendRelationshipDto;
import kr.co.mz.sns.service.user.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/users/friends")
public class FriendController {

  private final FriendService friendService;

  @PostMapping("/request")
  public ResponseEntity<InsertFriendRelationshipDto> request(
      @NotNull @RequestBody InsertFriendRelationshipDto insertFriendRelationshipDto) {
    return ResponseEntity.ok(
        friendService.request(insertFriendRelationshipDto)
    );
  }

  @PutMapping("/request/accept")
  public ResponseEntity<InsertFriendRelationshipDto> putRelationship(
      AcceptFriendRelationshipDto acceptFriendRelationshipDto) {
    return ResponseEntity.ok(
        friendService.putRelationship(acceptFriendRelationshipDto)
    );
  }

  //todo pageable 바로 받아서 구현하기.
  @GetMapping("/list/{userSeq}")
  public Page<AFriendDto> findAllFriends(
      @PathVariable Long userSeq,
      @PageableDefault(sort = {"modifiedAt", "status"}, direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return friendService.findAllFriendsAsPage(userSeq, pageable);
  }

//  @GetMapping("/search")
//  public List<FriendDetailDto> findBy(@RequestParam String friendName) {
//    return friendService.findByFriendName(friendName);
//  }
//  @GetMapping("/list")
//  public ResponseEntity<List<ListFriendDto>> showAll() {
//    return ResponseEntity.ok(friendService.findAll(currentUserInfo.getSeq()));
//  }

}
