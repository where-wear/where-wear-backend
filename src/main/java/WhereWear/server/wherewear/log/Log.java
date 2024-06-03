package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
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
@Getter
@Entity
@Table(name = "log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", updatable = false)
    private Long id;
    @Column(name = "status")
    private String status;
    @Column(name = "text")
    private String text;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fashion_item_id")
    private FashionItem fashionItem;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id")
    private Place place;

    @JsonIgnore
    @OneToMany(mappedBy = "log")
    private List<LogTag> logTags = new ArrayList<>();

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
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Builder
    public Log(String status, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.status = status;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateText(String text){
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
        user.getLogs().add(this);
    }

    public void setFashionItem(FashionItem fashionItem) {
        this.fashionItem = fashionItem;
        fashionItem.getLogs().add(this);
    }

    public void setPlace(Place place) {
        this.place = place;
        place.getLogs().add(this);
    }

    public void removeFashionItem(FashionItem fashionItem) {
        if (this.fashionItem != null && this.fashionItem.equals(fashionItem)) {
            fashionItem.getLogs().remove(this);
            this.fashionItem = null;
        }
    }

    public void removePlace(Place place) {
        if (this.place != null && this.place.equals(place)) {
            place.getLogs().remove(this);
            this.place = null;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
