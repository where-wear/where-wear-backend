package WhereWear.server.wherewear.logImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LogImageRequest {
    private MultipartFile file;
}
