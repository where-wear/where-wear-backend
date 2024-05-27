package WhereWear.server.wherewear.fashion.category.dto;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.user.User;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;
@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String categoryName;
    private Integer categoryLevel;
    private String categoryImage;

    public CategoryDto(Category source) {
        copyProperties(source, this);

    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("categoryName", categoryName)
                .append("categoryLevel", categoryLevel)
                .append("categoryImage", categoryImage)
                .toString();
    }
}
