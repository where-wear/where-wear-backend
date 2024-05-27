package WhereWear.server.wherewear.fashion.category.dto;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {
    private Long id;
    private String categoryName;
    private Integer categoryLevel;
    private String categoryImage;

    private CategoryDto parentCategory;

    private List<CategoryDto> subCategories;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.categoryLevel = category.getCategoryLevel();
        this.categoryImage = category.getCategoryImage();

        if (category.getParentCategory() != null) {
            this.parentCategory = new CategoryDto(category.getParentCategory());
        }

        if (category.getSubCategories() != null) {
            this.subCategories = category.getSubCategories().stream()
                    .map(CategoryDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("categoryName", categoryName)
                .append("categoryLevel", categoryLevel)
                .append("categoryImage", categoryImage)
                .append("parentCategory", parentCategory)
                .append("subCategories", subCategories)
                .toString();
    }
}
