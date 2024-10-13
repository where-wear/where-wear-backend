package WhereWear.server.wherewear.fashion.fashionItem;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.log.fashion.LogFashion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@Table(name = "fashion_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FashionItem {
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
    @Builder
    public FashionItem(String itemName,Category category){
        this.itemName = itemName;
        this.category = category;
    }

    public FashionItem updateItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getFashionItems().add(this);
    }

}
