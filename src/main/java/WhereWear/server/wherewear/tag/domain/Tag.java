package WhereWear.server.wherewear.tag.domain;

import WhereWear.server.wherewear.base.BaseEntity;
import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.logImage.domain.LogImage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "tag")
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", updatable = false)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="log_id")
    private Log log;

    public static Tag of(@NotNull Log log, @NotNull String tagName) {
        return Tag.builder()
                .log(log)
                .tagName(tagName)
                .build();
    }
}
