package WhereWear.server.wherewear.log.domain;

import WhereWear.server.wherewear.base.BaseEntity;
import WhereWear.server.wherewear.fashion.fashionItem.domain.FashionItem;
import WhereWear.server.wherewear.logFashion.domain.LogFashion;
import WhereWear.server.wherewear.likedLog.domain.LikedLog;
import WhereWear.server.wherewear.logImage.domain.LogImage;
import WhereWear.server.wherewear.savedLog.SavedLog;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.tag.Tag;
import WhereWear.server.wherewear.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "log")
public class Log extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", updatable = false)
    private Long id;

    @Column(name = "text")
    private String text;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.PERSIST)//User를 저장할 때 관련된 RefreshToken도 함께 저장
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "is_show")
    private Boolean isShow;

    @JsonIgnore
    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LogFashion> logFashions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikedLog> likedLogs = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedLog> savedLogs = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LogImage> logImages = new ArrayList<>();

    public static Log of(@NotNull User user, @NotNull List<FashionItem> fashionItems) {
        Log log = Log.builder()
                .user(user)
                .build();

        log.logFashions = fashionItems.stream()
                .map(fashionItem -> LogFashion.of(log, fashionItem))
                .collect(Collectors.toList());

        return log;
    }

    public void updateText(String text) {
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
        this.tags.add(tag);
        tag.setLog(this);
    }

    public void setLikedLogs(LikedLog likedLog) {
        this.likedLogs.add(likedLog);
    }

    public void removeLikedLog(LikedLog likedLog) {
        if (this.likedLogs != null && likedLog != null) {
            this.likedLogs.remove(likedLog);
        }
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }
}
