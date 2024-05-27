package WhereWear.server.wherewear.fashion.category.controller;

import WhereWear.server.wherewear.fashion.category.dto.CategoryResponse;
import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.fashion.category.service.CategoryService;
import WhereWear.server.wherewear.user.UserService;
import WhereWear.server.wherewear.user.account.dto.NicknameCheckResponse;
import WhereWear.server.wherewear.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/log")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/{logId}/fashionItem/search/id")
    public ResponseEntity<ApiUtils.ApiResult<CategoryResponse>> getCategoryById(@PathVariable("logId") Long logId,
                                                                @RequestParam Long categoryId){

        Category category = categoryService.searchById(categoryId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new CategoryResponse(category)));
    }

    @GetMapping("/{logId}/fashionItem/search/name")
    public ResponseEntity<ApiUtils.ApiResult<List<CategoryResponse>>> getCategoryByName(@PathVariable("logId") Long logId,
                                                                                @RequestParam String categoryName){

        List<Category> categories = categoryService.searchByName(categoryName);

        List<CategoryResponse> categoryResponses = categories.stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(categoryResponses));

    }

}
