package WhereWear.server.wherewear.fashion.category.service;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.fashion.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Category searchById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected category"));
    }

    public List<Category> searchByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected category"));
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
}
