package WhereWear.server.wherewear.log.dto;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItemRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor//기본생성자
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class LogRequest {
    private String text;

    private List<MultipartFile> imageUrls = new ArrayList<>();

    private List<FashionItemRequest> items = new ArrayList<>();

    private Double x;
    private Double y;
    private String roadAddress;
    private String address;
    private String placeName;
    private Boolean isShow;

    private List<String> tags = new ArrayList<>();

}