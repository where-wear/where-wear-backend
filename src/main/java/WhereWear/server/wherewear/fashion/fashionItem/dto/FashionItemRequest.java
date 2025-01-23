package WhereWear.server.wherewear.fashion.fashionItem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FashionItemRequest {
    private Long categoryId;
    private String itemName;
}
