package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.fashion.LogFashion;
import WhereWear.server.wherewear.log.likedLog.LikedLog;
import WhereWear.server.wherewear.log.logImage.LogImage;
import WhereWear.server.wherewear.log.savedLog.SavedLog;
import WhereWear.server.wherewear.log.tag.LogTag;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.tag.Tag;
import WhereWear.server.wherewear.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", updatable = false)
    private Long id;
    @Column(name = "text")
    private String text;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne(cascade = CascadeType.PERSIST)//User를 저장할 때 관련된 RefreshToken도 함께 저장
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "is_show")
    private Boolean isShow;

    @JsonIgnore
    @OneToMany(mappedBy = "log")
    private List<LogFashion> logFashions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "log")
    private List<Tag> Tags = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "log")
    private List<LikedLog> likedLogs = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "log")
    private List<SavedLog> savedLogs = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "log")
    private List<LogImage> logImages = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Log(User user) {
        this.user = user;
    }

    public void updateText(String text){
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
        user.getLogs().add(this);
    }

    public void setPlace(Place place) {
        this.place = place;
        place.setLog(this);
    }

    public void setTags(Tag tag) {
        this.getTags().add(tag);
        tag.setLog(this);
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }
}
