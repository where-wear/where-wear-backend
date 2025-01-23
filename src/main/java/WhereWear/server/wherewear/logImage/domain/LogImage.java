package WhereWear.server.wherewear.logImage.domain;

import WhereWear.server.wherewear.base.BaseEntity;
import WhereWear.server.wherewear.fashion.fashionItem.domain.FashionItem;
import WhereWear.server.wherewear.fashion.fashionItem.domain.LogFashion;
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
public class LogImage extends BaseEntity {
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

    public static LogImage of(@NotNull Log log, @NotNull String publicUrl) {
        return LogImage.builder()
                .log(log)
                .publicUrl(publicUrl)
                .build();
    }

    public void removeImageFromLog(Log log) {
        if (this.log != null && this.log.equals(log)) {
            log.getLogImages().remove(this);
            this.log = null;
        }
    }
}
