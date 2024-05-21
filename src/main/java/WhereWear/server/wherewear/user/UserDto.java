package WhereWear.server.wherewear.user;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String nickname;
    private String email;
    private String image;
    private int height;
    private int weight;
    private int footSize;
    private String job;
    private String introduction;

    //private int loginCount;

    //private LocalDateTime lastLoginAt;

    //private LocalDateTime createAt;

    public UserDto(User source) {
        copyProperties(source, this);

        //this.lastLoginAt = source.getLastLoginAt().orElse(null);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("nickname", nickname)
                .append("email", email)
                .append("image", image)
                .append("height", height)
                .append("weight", weight)
                .append("footSize", footSize)
                .append("job", job)
                .append("introduction", introduction)
                //.append("loginCount", loginCount)
                //.append("lastLoginAt", lastLoginAt)
                //.append("createAt", createAt)
                .toString();
    }

}