package WhereWear.server.wherewear.user.account.controller;

import WhereWear.server.wherewear.error.NotFoundException;
import WhereWear.server.wherewear.user.account.dto.NicknameCheckResponse;
import WhereWear.server.wherewear.user.account.dto.SignupRequest;
import WhereWear.server.wherewear.error.UnauthorizedException;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import WhereWear.server.wherewear.user.account.dto.UserInfoResponse;
import WhereWear.server.wherewear.user.account.service.AccountService;
import WhereWear.server.wherewear.util.ApiUtils;
import static WhereWear.server.wherewear.util.ApiUtils.success;
import static WhereWear.server.wherewear.util.ApiUtils.error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
    public ApiUtils.ApiResult<NicknameCheckResponse> nicknameCheck(@RequestHeader("Authorization") String token, @RequestParam String nickname) throws IOException {

        String checkedNickname = accountService.existNickname(nickname);
        return success(new NicknameCheckResponse(token, checkedNickname));
    }

    @PostMapping("/signUp")
    public ApiUtils.ApiResult<UserInfoResponse> signUp(
            @RequestHeader("Authorization") String token, @RequestBody SignupRequest signupRequest) throws IOException {

        User user = userService.findByAccessToken(token);
        User updatedUser = accountService.signUp(user,signupRequest);
        return success(new UserInfoResponse(token, updatedUser));

    }
}
