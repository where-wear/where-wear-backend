package WhereWear.server.wherewear.log.dto;

import WhereWear.server.wherewear.fashion.fashionItem.dto.FashionItemDto;
import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.fashion.fashionItem.domain.LogFashion;
import WhereWear.server.wherewear.likedLog.domain.LikedLog;
import WhereWear.server.wherewear.likedLog.dto.LikedLogDto;
import WhereWear.server.wherewear.logImage.domain.LogImage;
import WhereWear.server.wherewear.logImage.dto.LogImageDto;
import WhereWear.server.wherewear.place.dto.PlaceDto;
import WhereWear.server.wherewear.tag.dto.LogTagDto;
import WhereWear.server.wherewear.tag.domain.Tag;
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
    private List<LikedLogDto> liked = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean isMyLog = false;
    private boolean isLike = false;

    public LogResponse(Log log) {
        this.id = log.getId();
        this.isShow = log.getIsShow();
        this.text = log.getText();
        this.user = new UserDto(log.getUser());  // User 엔티티를 UserResponse DTO로 변환
        if (log.getPlace() != null) {
            this.place = new PlaceDto(log.getPlace());
        }
        if (log.getLogFashions() != null) {
            for (LogFashion logFashion : log.getLogFashions()) {
                this.fashionItems.add(new FashionItemDto(logFashion));
            }
        }
        if (log.getTags() != null) {
            for (Tag tag : log.getTags()) {
                this.tags.add(new LogTagDto(tag));
            }
        }
        if (log.getLogImages() != null) {
            for (LogImage logImage : log.getLogImages()) {
                this.logImages.add(new LogImageDto(logImage));
            }
        }
        if (log.getLikedLogs() != null) {
            for (LikedLog likedLog : log.getLikedLogs()) {
                this.liked.add(new LikedLogDto(likedLog));
            }
        }
    }

    public void updateIsMyLog(boolean flag) {
        this.isMyLog = flag;
    }

    public void updateIsLike(boolean flag) {
        this.isLike = flag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("text", text)
                .append("imageUrls", logImages)
                .append("items", fashionItems)
                .append("isShow", isShow)
                .append("user", user)
                .append("place", place)
                .append("tag", tags)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .toString();
    }
}
