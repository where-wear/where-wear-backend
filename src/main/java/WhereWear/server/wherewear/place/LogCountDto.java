package WhereWear.server.wherewear.place;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogCountDto {
    private double x;
    private double y;
    private long count;

    public LogCountDto(double x, double y, long count) {
        this.x = x;
        this.y = y;
        this.count = count;
    }
}
