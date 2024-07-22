package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItemDto;
import WhereWear.server.wherewear.log.fashion.LogFashion;
import WhereWear.server.wherewear.log.logImage.LogImage;
import WhereWear.server.wherewear.log.logImage.LogImageDto;
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
    private Boolean isShow;
    private String text;
    private UserDto user;
    private PlaceDto place;
    private List<FashionItemDto> fashionItems = new ArrayList<>();
    private List<LogTagDto> tags = new ArrayList<>();
    private List<LogImageDto> logImages = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LogResponse(Log log) {
        this.id = log.getId();
        this.isShow = log.getIsShow();
        this.text = log.getText();
        this.user = new UserDto(log.getUser());  // User 엔티티를 UserResponse DTO로 변환
        if (log.getPlace() != null) {
            this.place = new PlaceDto(log.getPlace());
        }
        if (log.getLogFashions() != null) {
            for(LogFashion logFashion : log.getLogFashions()) {
                this.fashionItems.add(new FashionItemDto(logFashion));
            }
        }
        if (log.getLogTags() != null){
            for(LogTag logTag : log.getLogTags()) {
                this.tags.add(new LogTagDto(logTag));
            }
        }
        if (log.getLogImages() != null){
            for(LogImage logImage : log.getLogImages()) {
                this.logImages.add(new LogImageDto(logImage));
            }
        }
        this.createdAt = log.getCreatedAt();
        this.updatedAt = log.getUpdatedAt();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("text", text)
                .append("imageUrls",logImages)
                .append("items",fashionItems)
                .append("isShow",isShow)
                .append("user", user)
                .append("place", place)
                .append("tag",tags)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .toString();
    }
}
