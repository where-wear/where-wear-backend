package WhereWear.server.wherewear.log.dto;

import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.logImage.dto.LogImageDto;
import WhereWear.server.wherewear.place.dto.MyPagePlaceResponse;
import WhereWear.server.wherewear.user.UserPageResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyPageLogResponse {
    private Long id;
    private UserPageResponse user;
    private List<LogImageDto> logImages = new ArrayList<>();
    private MyPagePlaceResponse place;

    public MyPageLogResponse(Log log) {
        this.id = log.getId();
        this.user = new UserPageResponse(log.getUser());
        this.logImages.add(new LogImageDto(log.getLogImages().get(0)));
        this.place = new MyPagePlaceResponse(log.getPlace());
    }
}
