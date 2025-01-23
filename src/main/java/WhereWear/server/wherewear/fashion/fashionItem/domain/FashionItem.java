package WhereWear.server.wherewear.fashion.fashionItem.domain;

import WhereWear.server.wherewear.base.BaseEntity;
import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.logFashion.domain.LogFashion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "fashion_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FashionItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fashion_item_id", updatable = false)
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "fashionItem")
    private List<LogFashion> logFashions = new ArrayList<>();

    public static FashionItem of(@NotNull String itemName, @NotNull Category category) {
        return FashionItem.builder()
                .itemName(itemName)
                .category(category)
                .build();
    }
}
