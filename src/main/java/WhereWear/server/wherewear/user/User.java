package WhereWear.server.wherewear.user;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.likedLog.LikedLog;
import WhereWear.server.wherewear.log.savedLog.SavedLog;
import WhereWear.server.wherewear.refreshToken.RefreshToken;
import WhereWear.server.wherewear.user.account.dto.SignupRequest;
import WhereWear.server.wherewear.user.account.dto.UpdateRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Log> logs = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<LikedLog> likedLogs = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<SavedLog> savedLogs = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST)//User를 저장할 때 관련된 RefreshToken도 함께 저장
    @JoinColumn(name = "refresh_token_id")
    private RefreshToken refreshToken;

    @Column(name = "image")
    private String image;

    @Column(name = "height")
    private int height;

    @Column(name = "weight")
    private int weight;

    @Column(name = "foot_size")
    private int footSize;

    @Column(name = "job")
    private String job;

    @Column(name = "introduction")
    private String introduction;

    @Builder
    public User(String nickname, String password, String username) {
        this.nickname = nickname;
        this.password = password;
        this.username = username;
    }

    @Builder
    public User(String nickname, String password, String image, Integer height, Integer weight, Integer footSize, String job, String introduction) {
        this.nickname = nickname;
        this.password = password;
        this.image = image;
        this.height = height;
        this.weight = weight;
        this.footSize = footSize;
        this.job = job;
        this.introduction = introduction;
    }

    public User updateNickName(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public User updateProfile(UpdateRequest updateRequest) {
        this.image = updateRequest.getImage();
        this.height = updateRequest.getHeight();
        this.weight = updateRequest.getWeight();
        this.footSize = updateRequest.getFootSize();
        this.job = updateRequest.getJob();
        this.introduction = updateRequest.getIntroduction();
        return this;
    }

    public User signUp(SignupRequest signupRequest) {
        this.nickname = signupRequest.getNickname();
        this.image = signupRequest.getImage();
        this.height = signupRequest.getHeight();
        this.weight = signupRequest.getWeight();
        this.footSize = signupRequest.getFootSize();
        this.job = signupRequest.getJob();
        this.introduction = signupRequest.getIntroduction();
        return this;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
