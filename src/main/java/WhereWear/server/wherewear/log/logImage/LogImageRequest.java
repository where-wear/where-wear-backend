package WhereWear.server.wherewear.log.logImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LogImageRequest {
    private String itemName;
    private String imageData;
}
