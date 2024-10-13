package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id", updatable = false)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "address")
    private String address;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    @Column(name="place_name")
    private String placeName;

    @OneToOne(mappedBy = "place", fetch = FetchType.LAZY)
    private Log log;

    @Builder
    public Place(String address, String category, Double x, Double y, String placeName) {
        this.address = address;
        this.category = category;
        this.x = x;
        this.y = y;
        this.placeName = placeName;
    }
    @Builder
    public Place(Long id, String address, String category, Double x, Double y, String placeName) {
        this.id = id;
        this.address = address;
        this.category = category;
        this.x = x;
        this.y = y;
        this.placeName = placeName;
    }

}
