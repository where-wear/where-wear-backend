package WhereWear.server.wherewear.log.logImage;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@Getter
@Setter
public class LogImageDto {
    private Long id;
    private String publicUrl;

    public LogImageDto(LogImage logImage) {
        this.id = logImage.getId();
        this.publicUrl = logImage.getPublicUrl();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("publicUrl", publicUrl)
                .toString();
    }
}
