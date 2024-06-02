package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItemDto;
import WhereWear.server.wherewear.log.place.PlaceDto;
import WhereWear.server.wherewear.log.tag.LogTag;
import WhereWear.server.wherewear.log.tag.LogTagDto;
import WhereWear.server.wherewear.user.UserDto;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class LogResponse {
    private Long id;
    private String status;
    private String text;
    private UserDto user;  // 새로운 UserResponse DTO 사용
    private FashionItemDto fashionItem;
    private PlaceDto place;
    private List<LogTagDto> tags = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LogResponse(Log log) {
        this.id = log.getId();
        this.status = log.getStatus();
        this.text = log.getText();
        this.user = new UserDto(log.getUser());  // User 엔티티를 UserResponse DTO로 변환
        if (log.getFashionItem() != null) {
            this.fashionItem = new FashionItemDto(log.getFashionItem());
        }
        if (log.getPlace() != null) {
            this.place = new PlaceDto(log.getPlace());
        }
        if (log.getLogTags() != null){
            for(LogTag logTag:log.getLogTags()) {
                this.tags.add(new LogTagDto(logTag));
            }
        }
        this.createdAt = log.getCreatedAt();
        this.updatedAt = log.getUpdatedAt();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("status", status)
                .append("text", text)
                .append("user", user)
                .append("place", place)
                .append("tags",tags)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .toString();
    }
}
