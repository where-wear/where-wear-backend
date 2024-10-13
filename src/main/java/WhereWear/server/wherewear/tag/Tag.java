package WhereWear.server.wherewear.tag;

import WhereWear.server.wherewear.log.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", updatable = false)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="log_id")
    private Log log;

    @Builder
    public Tag(String tagName) {
        this.tagName = tagName;
    }


}
