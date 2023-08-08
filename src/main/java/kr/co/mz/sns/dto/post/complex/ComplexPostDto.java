package kr.co.mz.sns.dto.post.complex;

import kr.co.mz.sns.dto.post.PostAndPostFileAndCommentDto;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexPostDto {

    private Long postSeq;
    private String postContent;
    private Integer postLikes;
    private Long postCreateBy;
    private LocalDateTime postCreatedAt;
    private LocalDateTime postModifiedAt;

    private List<ComplexCommentDto> commentDtoList;
    private List<ComplexPostFileDto> postFileDtoList;

    public static List<ComplexPostDto> of(List<PostAndPostFileAndCommentDto> dtos) {
        var complexDtos = new ArrayList<ComplexPostDto>();
        var dtoMap = dtos.stream().collect(Collectors.groupingBy(PostAndPostFileAndCommentDto::getPostSeq));
        var mapper = new ModelMapper();

        for (var seq : dtoMap.keySet()) {
            var flatPostDto = dtoMap.get(seq).get(0);

            var complexCommentDtos = dtoMap.get(seq).stream()
                    .map(flatPost -> mapper.map(flatPost, ComplexCommentDto.class))
                    .toList();
            var complexPostFileDtos = dtoMap.get(seq).stream()
                    .map(flatPost -> mapper.map(flatPost, ComplexPostFileDto.class))
                    .toList();

            var complexPostDto = mapper.map(flatPostDto, ComplexPostDto.class);
            complexPostDto.setCommentDtoList(complexCommentDtos);
            complexPostDto.setPostFileDtoList(complexPostFileDtos);

            complexDtos.add(complexPostDto);
        }
        return complexDtos.stream().sorted(Comparator.comparingLong(ComplexPostDto::getPostSeq)).toList();
    }
}
