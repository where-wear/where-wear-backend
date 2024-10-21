package WhereWear.server.wherewear.log.explore;

import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.log.place.PlaceDto;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.place.PlaceService;
import WhereWear.server.wherewear.tag.TagService;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/explore")
@Tag(name = "둘러보기", description = "둘러보기 API")
public class ExploreController {
    private final PlaceService placeService;
    private final ExploreService exploreService;
    private final UserService userService;
    private final TagService tagService;
    @Operation(summary = "태그 top 플레이스 조회", description = "일주일 간 로그에 많이 태그된 장소 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "장소 로그 수 조회 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "요청에 대한 응답을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping("/topPlace")
    public ResponseEntity<?> getTopTaggedPlaces(@RequestParam("category") String category){
        List<Place> places = placeService.getTopTaggedPlaces(category);
        List<PlaceDto> response = places.stream()
                .map(place -> new PlaceDto(place))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(response));
    }
    @Operation(summary = "핫키워드 조회", description = "일주일 간 인기 있었던 태그를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "태그 조회 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "요청에 대한 응답을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping("/HotKeywords")
    public ResponseEntity<?> getHotKeywords(@RequestParam("category") String category){
        List<String> tags = tagService.getHotKeywords(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(tags));
    }

    @GetMapping
    public ResponseEntity<?> explore(@RequestHeader(value = "Authorization", required = false) String token,
                                     @RequestParam("category") String category){
        ExploreDto response = exploreService.explore(token,category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(response));
    }
}
