package WhereWear.server.wherewear.log.logImage;

import WhereWear.server.wherewear.log.tag.LogTag;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@Getter
@Setter
public class LogImageDto {
    private Long id;
    private String imageUrl;

    public LogImageDto(LogImage logImage) {
        this.id = logImage.getId();
        this.imageUrl = logImage.getImageUrl();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("imageUrl", imageUrl)
                .toString();
    }
}
