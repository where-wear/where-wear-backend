package WhereWear.server.wherewear.log.tag;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.tag.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "log_tag")
public class LogTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_tag_id", updatable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="log_id")
    private Log log;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="tag_id")
    private Tag tag;

    //==연관관계 메서드==//
    public void setLog(Log log){
        this.log = log;
        this.log.getLogTags().add(this);
    }

    public void setTag(Tag tag){
        this.tag = tag;
        this.tag.getLogTags().add(this);
    }
}
