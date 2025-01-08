package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.log.domain.Log;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(indexName="place_document")
public class PlaceDocument {
    @Id
    private Long id;

    private String category;

    private String address;

    private double x;

    private double y;

    private String placeName;

    @Builder
    public PlaceDocument() {
    }
}
