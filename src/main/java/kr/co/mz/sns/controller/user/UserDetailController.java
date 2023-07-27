package kr.co.mz.sns.controller.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.user.InsertUserDetailDto;
import kr.co.mz.sns.dto.user.UpdateUserDetailDto;
import kr.co.mz.sns.dto.user.UserFindByEmailDto;
import kr.co.mz.sns.service.UserDetailService;
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

    @GetMapping
    public ResponseEntity<InsertUserDetailDto> findByEmail(@Valid @RequestBody UserFindByEmailDto userFindByEmailDto) {
        return ResponseEntity
            .ok(
                userDetailService.findByEmail(userFindByEmailDto.getEmail())
            );
    }

    @PostMapping
    public ResponseEntity<InsertUserDetailDto> write(@RequestBody InsertUserDetailDto insertUserDetailDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                userDetailService.saveOne(insertUserDetailDto)
            );
    }

    @PutMapping
    public ResponseEntity<InsertUserDetailDto> update(@RequestBody UpdateUserDetailDto
        updateUserDetailDto) {
        return ResponseEntity
            .ok(
                userDetailService.updateByUserSeq(updateUserDetailDto)
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