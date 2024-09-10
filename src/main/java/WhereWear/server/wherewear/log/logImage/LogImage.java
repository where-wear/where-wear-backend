package WhereWear.server.wherewear.log.logImage;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.place.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "public_url")
    private String publicUrl;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="log_id")
    private Log log;

    @Builder
    public LogImage(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public void removeImageFromLog(Log log) {
        if (this.log != null && this.log.equals(log)) {
            log.getLogImages().remove(this);
            this.log = null;
        }
    }

    public void setLog(Log log){
        this.log = log;
        this.log.getLogImages().add(this);
    }
}
