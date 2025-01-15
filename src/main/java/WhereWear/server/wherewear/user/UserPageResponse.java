package WhereWear.server.wherewear.user;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
public class UserPageResponse {
    private Long id;
    private String nickname;
    private String image;
    private String introduction;

    public UserPageResponse(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.introduction = user.getIntroduction();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("nickname", nickname)
                .append("image", image)
                .append("introduction", introduction)
                .toString();
    }
}
