package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.fashion.category.repository.CategoryRepository;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItemRequest;
import WhereWear.server.wherewear.log.logImage.LogImageRequest;
import WhereWear.server.wherewear.tag.Tag;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.tag.TagRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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