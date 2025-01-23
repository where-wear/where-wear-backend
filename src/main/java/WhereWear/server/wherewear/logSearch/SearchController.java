package WhereWear.server.wherewear.logSearch;

import WhereWear.server.wherewear.place.dto.PlaceDocumentDto;
import WhereWear.server.wherewear.place.service.PlaceService;
import WhereWear.server.wherewear.user.UserDto;
import WhereWear.server.wherewear.user.UserService;
import WhereWear.server.wherewear.util.ApiUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/search")
@Tag(name = "검색", description = "검색 관리 API")
public class SearchController {
    private final PlaceService placeService;
    private final UserService userService;

    @GetMapping("/place")
    public ResponseEntity<?> searchPlace(@RequestParam("name") String placeName) {
        List<PlaceDocumentDto> places = placeService.searchPlaceByName(placeName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(places));
    }

    @GetMapping("/user")
    public ResponseEntity<?> searchUser(@RequestParam("name") String userName) {
        List<UserDto> users = userService.searchUserByName(userName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(users));
    }
}
