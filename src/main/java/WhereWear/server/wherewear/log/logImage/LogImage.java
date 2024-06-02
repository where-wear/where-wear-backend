package WhereWear.server.wherewear.log.logImage;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.place.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@Table(name = "log_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_image_id", updatable = false)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="log_id")
    private Log log;
}
