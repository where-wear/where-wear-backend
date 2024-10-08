package WhereWear.server.wherewear.fashion.fashionItem;

import WhereWear.server.wherewear.fashion.category.dto.CategoryDto;
import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.log.fashion.LogFashion;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
public class FashionItemDto {
    private Long id;
    private String itemName;
    private CategoryDto category;

    public FashionItemDto(LogFashion logFashion) {
        this.id = logFashion.getFashionItem().getId();
        this.itemName = logFashion.getFashionItem().getItemName();
        this.category = new CategoryDto(logFashion.getFashionItem().getCategory());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("itemName", itemName)
                .append("category", category)
                .toString();
    }
}
