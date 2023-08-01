package kr.co.mz.sns.service.post;

import java.util.List;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.post.SaveFileRequestDto;
import kr.co.mz.sns.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStorageRequestService {

    private final RestTemplate restTemplate;
    private final FileStorageService fileStorageService;
    
    public String save(List<MultipartFile> multipartFiles, GenericPostDto genericPostDto) {
        String url = "http://172.90.4.143:8080/api/unauth/posts"; // 다른 서버의 API URL
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(
                new SaveFileRequestDto(fileStorageService.getByteArrayList(multipartFiles), genericPostDto), headers),
            String.class
        );

        return response.getBody();
//        byte[] imageBytes = response.getBody();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
