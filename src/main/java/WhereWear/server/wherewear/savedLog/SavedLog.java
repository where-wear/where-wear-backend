package WhereWear.server.wherewear.savedLog;

import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@Table(name = "saved_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_log_id", updatable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="log_id")
    private Log log;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
