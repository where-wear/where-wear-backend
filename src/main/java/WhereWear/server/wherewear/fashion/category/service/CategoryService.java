package WhereWear.server.wherewear.fashion.category.service;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.fashion.category.repository.CategoryRespository;
import WhereWear.server.wherewear.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRespository categoryRespository;
    public Category searchById(Long categoryId) {
        return categoryRespository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected category"));
    }

    public List<Category> searchByName(String categoryName) {
        return categoryRespository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected category"));
    }
}
