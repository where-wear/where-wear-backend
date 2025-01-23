package WhereWear.server.wherewear.logFashion.domain;

import WhereWear.server.wherewear.base.BaseEntity;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.domain.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "log_fashion")
public class LogFashion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_fashion_id", updatable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="log_id")
    private Log log;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fashion_item_id")
    private FashionItem fashionItem;

    public static LogFashion of(@NotNull Log log, @NotNull FashionItem fashionItem) {
        return LogFashion.builder()
                .log(log)
                .fashionItem(fashionItem)
                .build();
    }

    //==연관관계 메서드==//
    public void setLog(Log log){
        this.log = log;
        this.log.getLogFashions().add(this);
    }

    public void setFashionItem(FashionItem fashionItem){
        this.fashionItem = fashionItem;
        this.fashionItem.getLogFashions().add(this);
    }
}
