package WhereWear.server.wherewear.log.explore;

import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.place.PlaceService;
import WhereWear.server.wherewear.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/explore")
@Tag(name = "둘러보기", description = "둘러보기 API")
public class ExploreController {
    private final PlaceService placeService;
    @Operation(summary = "태그 top 플레이스 조회", description = "일주일 간 로그에 많이 태그된 장소 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "장소 로그 수 조회 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "요청에 대한 응답을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping
    public ResponseEntity<?> getTopTaggedPlaces(@RequestParam String category){
        List<Place> places = placeService.getTopTaggedPlaces(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(places));
    }
}
