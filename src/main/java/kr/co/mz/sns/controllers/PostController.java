package kr.co.mz.sns.controllers;


import java.util.ArrayList;
import java.util.List;
import kr.co.mz.sns.dto.PostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

    @GetMapping("/get")
    public ResponseEntity<List<PostDto>> get(){
        List<PostDto> postList = new ArrayList<>();
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody PostDto postDto) {
        return ResponseEntity.ok().body("Successful Write A Post");
    }

}
