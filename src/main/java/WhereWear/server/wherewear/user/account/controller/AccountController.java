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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/nicknameCheck")
    public ResponseEntity<ApiUtils.ApiResult<NicknameCheckResponse>> nicknameCheck(@RequestHeader("Authorization") String token, @RequestParam("nickname") String nickname) throws IOException {

        String checkedNickname = accountService.existNickname(nickname);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new NicknameCheckResponse(checkedNickname)));
    }

    @PostMapping("/signUp")
    public ApiUtils.ApiResult<UserInfoResponse> signUp(
            @RequestHeader("Authorization") String token, @RequestBody SignupRequest signupRequest) throws IOException {

        User user = userService.findByAccessToken(token);
        User updatedUser = accountService.signUp(user, signupRequest);
        return success(new UserInfoResponse(token, updatedUser));

    }

    @PostMapping("/update")
    public ApiUtils.ApiResult<UserInfoResponse> update(
            @RequestHeader("Authorization") String token, @RequestBody UpdateRequest updateRequest) throws IOException {

        User user = userService.findByAccessToken(token);
        User updatedUser = accountService.updateUserInfo(user, updateRequest);
        return success(new UserInfoResponse(token, updatedUser));

    }
}
