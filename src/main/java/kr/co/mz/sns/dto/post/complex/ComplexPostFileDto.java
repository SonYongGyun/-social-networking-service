package kr.co.mz.sns.dto.post.complex;

import lombok.*;

@Getter
@Setter
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
