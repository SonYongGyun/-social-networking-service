package kr.co.mz.sns.controller.user;

import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.user.friend.AFriendDto;
import kr.co.mz.sns.dto.user.friend.AcceptFriendRelationshipDto;
import kr.co.mz.sns.dto.user.friend.InsertFriendRelationshipDto;
import kr.co.mz.sns.service.user.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<AcceptFriendRelationshipDto> accept(AcceptFriendRelationshipDto acceptFriendRelationshipDto) {
    return ResponseEntity.ok(
        friendService.accept(acceptFriendRelationshipDto)
    );
  }

  @GetMapping("/list/{userSeq}")
  public Page<AFriendDto> findAllFriends(
      @PathVariable Long userSeq,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "5") int size
  ) {
    var pageable = PageRequest.of(page, size);
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
