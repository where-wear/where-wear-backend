package WhereWear.server.wherewear.place.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName="place_document")
public class PlaceDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String category;

    @Field(type = FieldType.Text)
    private String address;

    private double x;

    private double y;

    @Field(type = FieldType.Text)
    private String placeName;

    @Builder
    public PlaceDocument() {
    }

    public static PlaceDocument from(Place place) {
        PlaceDocument document = new PlaceDocument();
        document.setId(place.getId());
        document.setPlaceName(place.getPlaceName());
        document.setAddress(place.getAddress());
        document.setCategory(place.getCategory());
        document.setX(place.getX());
        document.setY(place.getY());
        return document;
    }
}
