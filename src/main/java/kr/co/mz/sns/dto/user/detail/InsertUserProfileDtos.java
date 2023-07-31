package kr.co.mz.sns.dto.user.detail;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InsertUserProfileDtos {

  @NotNull
  @NotEmpty
  @Size(max = 10)
  private List<MultipartFile> files;
}
