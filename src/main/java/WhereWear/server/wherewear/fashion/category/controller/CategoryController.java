package WhereWear.server.wherewear.fashion.category.controller;

import WhereWear.server.wherewear.fashion.category.dto.CategoryResponse;
import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.fashion.category.service.CategoryService;
import WhereWear.server.wherewear.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "패션 카테고리", description = "패션 아이템 카테고리 관리 API")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 ID로 검색", description = "카테고리 ID를 사용하여 패션 카테고리를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 검색 성공",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping("/{logId}/fashionItem/search/id")
    public ResponseEntity<?> getCategoryById(
            @Parameter(description = "로그 ID") @PathVariable("logId") Long logId,
            @Parameter(description = "카테고리 ID") @RequestParam Long categoryId) {

        Category category = categoryService.searchById(categoryId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new CategoryResponse(category)));
    }

    @Operation(summary = "카테고리 이름으로 검색", description = "카테고리 이름을 사용하여 패션 카테고리를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 검색 성공",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping("/{logId}/fashionItem/search/name")
    public ResponseEntity<?> getCategoryByName(
            @Parameter(description = "로그 ID") @PathVariable("logId") Long logId,
            @Parameter(description = "카테고리 이름") @RequestParam String categoryName) {

        List<Category> categories = categoryService.searchByName(categoryName);
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(categoryResponses));
    }
}
