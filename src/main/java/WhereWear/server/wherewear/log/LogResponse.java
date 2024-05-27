package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserDto;
import jakarta.persistence.Column;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

@Getter
public class LogResponse {
    private Long id;
    private String status;
    private String text;
    private UserDto user;  // 새로운 UserResponse DTO 사용
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LogResponse(Log log) {
        this.id = log.getId();
        this.status = log.getStatus();
        this.text = log.getText();
        this.user = new UserDto(log.getUser());  // User 엔티티를 UserResponse DTO로 변환
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
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .toString();
    }
}
