package WhereWear.server.wherewear.user.account.dto;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserDto;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@Getter
public class NicknameCheckResponse {
    private final String token;

    private final String nickname;

    public NicknameCheckResponse(String token, String nickname) {
        this.token = token;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("token", token)
                .append("nickname", nickname)
                .toString();
    }
}
