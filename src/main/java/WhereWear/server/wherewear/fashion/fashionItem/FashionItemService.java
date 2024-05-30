package WhereWear.server.wherewear.fashion.fashionItem;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.fashion.category.repository.CategoryRespository;
import WhereWear.server.wherewear.fashion.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FashionItemService {

    private final FashionItemRepository fashionItemRepository;
    private final CategoryService categoryService;

    public FashionItem addItem(Long categoryId, String itemName){
        Category category = categoryService.searchById(categoryId);
        FashionItem fashionItem = new FashionItem();

        fashionItem.updateItemName(itemName);
        fashionItem.setCategory(category);

        categoryService.saveCategory(category);
        return fashionItemRepository.save(fashionItem);
    }

    public void deleteItem(Long itemId){
        fashionItemRepository.delete(itemId);
    }

    public FashionItem saveFashionItem(FashionItem fashionItem) {
        return fashionItemRepository.save(fashionItem);
    }
}
