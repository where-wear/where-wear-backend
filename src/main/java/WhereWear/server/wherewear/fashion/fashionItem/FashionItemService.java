package WhereWear.server.wherewear.fashion.fashionItem;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.fashion.category.service.CategoryService;
import WhereWear.server.wherewear.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FashionItemService {

    private final FashionItemRepository fashionItemRepository;
    private final CategoryService categoryService;

    public List<FashionItem> createFashionItems(List<FashionItemRequest> requests) {
        return requests.stream()
                .map(request -> createItem(request.getCategoryId(), request.getItemName()))
                .collect(Collectors.toList());
    }

    private FashionItem createItem(Long categoryId, String itemName){
        Category category = categoryService.searchById(categoryId);
        return fashionItemRepository.save(FashionItem.of(itemName, category));
    }

    public FashionItem findFashionItemById(Long fashionItemId){
        return fashionItemRepository.findById(fashionItemId).orElseThrow(() -> new IllegalArgumentException("Unexpected fashionItem"));
    }

    public void deleteItem(Long itemId){
        fashionItemRepository.delete(itemId);
    }

    public FashionItem saveFashionItem(FashionItem fashionItem) {
        return fashionItemRepository.save(fashionItem);
    }
}
