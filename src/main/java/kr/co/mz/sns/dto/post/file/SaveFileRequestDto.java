package kr.co.mz.sns.dto.post.file;

import kr.co.mz.sns.dto.post.PostWithOneFileDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveFileRequestDto {

    private List<String> byteFileList;
    private PostWithOneFileDto genericPostDto;

}
