package WhereWear.server.wherewear.fashion.fashionItem;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.fashion.category.repository.CategoryRespository;
import WhereWear.server.wherewear.log.LogRepository;
import WhereWear.server.wherewear.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FashionItemService {
    private final CategoryRespository categoryRespository;
    private final FashionItemRepository fashionItemRepository;
    public FashionItem addItem(Long categoryId, String itemName){
        FashionItem fashionItem = new FashionItem();
        Category category = findById(categoryId);
        fashionItem.updateItemName(itemName);
        fashionItem.setCategory(category);
        category.addFashionItem(fashionItem);
        categoryRespository.save(category);
        return fashionItemRepository.save(fashionItem);
    }

    public FashionItem saveFashionItem(FashionItem fashionItem) {
        return fashionItemRepository.save(fashionItem);
    }

    public Category findById(Long categoryId){
        return categoryRespository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Unexpected log"));
    }
}
