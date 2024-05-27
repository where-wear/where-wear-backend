package WhereWear.server.wherewear.user;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.refreshToken.RefreshToken;
import WhereWear.server.wherewear.user.account.dto.SignupRequest;
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

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Log> logs = new ArrayList<>();

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
    public User(String email, String nickname, String password, String username) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.username = username;
    }

    @Builder
    public User(String nickname, String email, String password, String image, Integer height, Integer weight, Integer footSize, String job, String introduction) {
        this.nickname = nickname;
        this.email = email;
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

    public void addLog(Log log){
        logs.add(log);
        log.setUser(this);
    }

    public User updateProfile(SignupRequest signupRequest) {
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
