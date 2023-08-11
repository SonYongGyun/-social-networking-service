package kr.co.mz.sns.controller.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.user.friend.*;
import kr.co.mz.sns.service.user.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/request/response")
    public ResponseEntity<ResponseRelationshipDto> putRelationship(
            @RequestBody RequestedRelationshipDto requestedRelationshipDto) {
        return ResponseEntity.ok(
                friendService.putRelationship(requestedRelationshipDto)
        );
    }

    @GetMapping("/list")
    public Page<AFriendDto> findAllFriends(
            @PageableDefault(sort = {"modifiedAt", "status"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return friendService.findAllFriendsAsPage(pageable);
    }

    @GetMapping("/search/{friendName}")
    public List<FriendDetailDto> findByName(@PathVariable String friendName) {
        return friendService.findByFriendName(friendName);
    }

    @GetMapping("/request/block/{friendSeq}")
    public ResponseEntity<?> blockFriend(@Valid @PathVariable Long friendSeq) {
        return ResponseEntity.ok().body(friendService.blockFriend(friendSeq));
    }

}
