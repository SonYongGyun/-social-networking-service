package kr.co.mz.sns.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexPostFileDto {

    private Long fileSeq;

    private String fileName;

    private String filePath;

    private Long fileSize;

    private String fileExtension;
}
