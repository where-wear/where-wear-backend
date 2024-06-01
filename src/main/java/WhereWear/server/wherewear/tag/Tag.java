package WhereWear.server.wherewear.tag;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.tag.LogTag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
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
    @OneToMany(mappedBy = "tag")
    private List<LogTag> logTags = new ArrayList<>();
}
