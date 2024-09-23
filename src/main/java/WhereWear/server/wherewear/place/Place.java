package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id", updatable = false)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    @Column(name="place_name")
    private String placeName;

    @JsonIgnore
    @OneToMany(mappedBy = "place")
    private List<Log> logs = new ArrayList<>();

    @Builder
    public Place(String address, Double x, Double y, String placeName) {
        this.address = address;
        this.x = x;
        this.y = y;
        this.placeName = placeName;
    }

}
