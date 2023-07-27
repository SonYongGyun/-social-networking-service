package kr.co.mz.sns.controller.user;

import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.user.FindUserDetailDto;
import kr.co.mz.sns.dto.user.InsertUserDetailDto;
import kr.co.mz.sns.dto.user.UpdateUserDetailDto;
import kr.co.mz.sns.service.UserDetailService;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/user_detail")
public class UserDetailController {

  private final UserDetailService userDetailService;
  private final CurrentUserInfo currentUserInfo;

  @GetMapping
  public ResponseEntity<FindUserDetailDto> findByEmail() {
    return ResponseEntity
        .ok(
            userDetailService.findByEmail(
                currentUserInfo.getEmail()
            )
        );
  }

//    @RestController
//    public class PostController {
//
//        @PostMapping("/create-post")
//        public ResponseEntity<String> createPost(@RequestParam("title") String title,
//            @RequestParam("content") String content,
//            @RequestPart("files") List<MultipartFile> files) {
//            // 게시글에 대한 비즈니스 로직 처리 (title, content)
//            // ...
//
//            // 파일 업로드 처리 (file)
//            // ...
//
//            return ResponseEntity.ok("Post created successfully.");
//        }
//    }

  @PostMapping
  public ResponseEntity<InsertUserDetailDto> insert(@RequestBody InsertUserDetailDto insertUserDetailDto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(
            userDetailService.saveOne(insertUserDetailDto)
        );
  }


  @PutMapping
  public ResponseEntity<InsertUserDetailDto> update(@RequestBody UpdateUserDetailDto
      updateUserDetailDto) {//업데이트할때는 seq 넣어서 보내주는걸로
    return ResponseEntity
        .ok(
            userDetailService.updateByUserSeq(
                updateUserDetailDto
            )
        );
  }

  @DeleteMapping("/{userSeq}")
  public ResponseEntity<String> delete(@NotNull @PathVariable Long userSeq) {
    var result = userDetailService.deleteByUserSeq(userSeq);
    return ResponseEntity
        .ok(
            "Your detail is deleted Successfully for :" + result + " rows."
        );
  }
}
