package WhereWear.server.wherewear.log.fashion;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.tag.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "log_fashion")
public class LogFashion {
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
