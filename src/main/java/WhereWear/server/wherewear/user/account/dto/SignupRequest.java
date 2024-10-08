package WhereWear.server.wherewear.user.account.dto;

import WhereWear.server.wherewear.user.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor//기본생성자
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Setter
public class SignupRequest {

    private String nickname;
    private int height;
    private int weight;
    private int footSize;
    private String job;
    private String introduction;

}
