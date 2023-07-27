package kr.co.mz.sns.controller.user;

import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.user.FindUserDetailDto;
import kr.co.mz.sns.dto.user.GenericUserDetailFileDto;
import kr.co.mz.sns.dto.user.InsertUserDetailDto;
import kr.co.mz.sns.dto.user.UpdateUserDetailDto;
import kr.co.mz.sns.file.FileStorageService;
import kr.co.mz.sns.service.FileService;
import kr.co.mz.sns.service.user.UserDetailService;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/user_detail")
public class UserDetailController {

    private final UserDetailService userDetailService;
    private final CurrentUserInfo currentUserInfo;
    private final FileService fileService;

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
                userDetailService.insert(insertUserDetailDto)

            );
    }

//  @PostMapping("/upload")
//  public ResponseEntity<List<GenericUserDetailFileDto>> uploadFile(
//      @RequestParam InsertUserProfileDtos insertUserProfileDtos
//  ){
//
//    var fileDtos =
//  }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("fileName") String fileName) {
        var inputStream = FileStorageService.downloadFile(
            fileService.findByName(new GenericUserDetailFileDto(fileName))
        );
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(new InputStreamResource(inputStream));
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
