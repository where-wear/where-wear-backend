package WhereWear.server.wherewear.log.tag;

import WhereWear.server.wherewear.tag.Tag;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@Getter
@Setter
public class LogTagDto {
    private Long id;
    private String tagName;

    public LogTagDto(Tag tag) {
        this.id = tag.getId();
        this.tagName = tag.getTagName();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("tagName", tagName)
                .toString();
    }
}