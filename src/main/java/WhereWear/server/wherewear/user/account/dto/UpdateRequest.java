package WhereWear.server.wherewear.user.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor//기본생성자
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class UpdateRequest {
    private String image;
    private int height;
    private int weight;
    private int footSize;
    private String job;
    private String introduction;
}
