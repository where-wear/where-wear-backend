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
    private String imageName;
    private String imageData;

    public LogImageDto(LogImage logImage) {
        this.id = logImage.getId();
        this.imageName = logImage.getImageName();
        this.imageData = logImage.getImageData();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("imageName", imageName)
                .append("imageData", imageData)
                .toString();
    }
}
