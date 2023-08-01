package kr.co.mz.sns.dto.post;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveFileRequestDto {

    private List<String> byteFileList;
    private GenericPostDto genericPostDto;

}
