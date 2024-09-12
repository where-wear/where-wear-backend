package WhereWear.server.wherewear.user.account.controller;

import WhereWear.server.wherewear.user.account.dto.NicknameCheckResponse;
import WhereWear.server.wherewear.user.account.dto.SignupRequest;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import WhereWear.server.wherewear.user.account.dto.UpdateRequest;
import WhereWear.server.wherewear.user.account.dto.UserInfoResponse;
import WhereWear.server.wherewear.user.account.service.AccountService;
import WhereWear.server.wherewear.util.ApiUtils;
import static WhereWear.server.wherewear.util.ApiUtils.success;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/accounts")
@Tag(name = "계정", description = "계정 관리 API")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 여부를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "닉네임 사용 가능",
                    content = @Content(schema = @Schema(implementation = NicknameCheckResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })

    @PostMapping("/nicknameCheck")
    public ResponseEntity<?> nicknameCheck(
            @Parameter(description = "유효한 인증 토큰") @RequestHeader("Authorization") String token,
            @Parameter(description = "확인할 닉네임") @RequestParam("nickname") String nickname) throws IOException {

        String checkedNickname = accountService.existNickname(nickname);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new NicknameCheckResponse(checkedNickname)));
    }

    @Operation(summary = "회원 가입", description = "새로운 사용자를 회원 가입합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원 가입 성공",
                    content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PostMapping(value = "/signUp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> signUp(
            @Parameter(description = "유효한 인증 토큰") @RequestHeader("Authorization") String token,
            @RequestParam("nickname") String nickname,  // 닉네임
            @RequestParam("height") int height,         // 키
            @RequestParam("weight") int weight,         // 몸무게
            @RequestParam("footSize") int footSize,     // 발사이즈
            @RequestParam("job") String job,            // 직업
            @RequestParam("introduction") String introduction,  // JSON 데이터를 받음
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {

        User user = userService.findByAccessToken(token);
        User updatedUser = accountService.signUp(user, nickname, height, weight, footSize, job, introduction, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new UserInfoResponse(updatedUser)));
    }

    @Operation(summary = "회원 정보 수정", description = "사용자 정보를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PostMapping("/update")
    public ResponseEntity<?> update(
            @Parameter(description = "유효한 인증 토큰") @RequestHeader("Authorization") String token,
            @RequestBody UpdateRequest updateRequest) throws IOException {

        User user = userService.findByAccessToken(token);
        User updatedUser = accountService.updateUserInfo(user, updateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new UserInfoResponse(updatedUser)));
    }

    @Operation(summary = "회원 정보 조회", description = "사용자의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping("/userDetail")
    public ResponseEntity<?> myInfo(
            @Parameter(description = "유효한 인증 토큰") @RequestHeader("Authorization") String token) throws IOException {

        User user = userService.findByAccessToken(token);
        User userInfo = accountService.getMyInfo(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new UserInfoResponse(userInfo)));
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "탈퇴 성공",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultSuccess.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @DeleteMapping("/dropUser")
    public ResponseEntity<?> dropUser(@RequestHeader("Authorization") String token){
        User user = userService.findByAccessToken(token);
        accountService.withdraw(user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(success(null));
    }
}
