package group.bigone.api.sign.controller;

import group.bigone.api.advice.exception.CEmailSigninFailedException;
import group.bigone.api.advice.exception.CUserExistException;
import group.bigone.api.advice.exception.CUserNotFoundException;
import group.bigone.api.common.Service.ResponseService;
import group.bigone.api.common.domain.CommonResult;
import group.bigone.api.common.domain.SingleResult;
import group.bigone.api.config.security.JwtTokenProvider;
import group.bigone.api.sign.Service.KakaoService;
import group.bigone.api.social.domain.KakaoProfile;
import group.bigone.api.user.Service.UserService;
import group.bigone.api.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Api(tags = {"Sign"})
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final KakaoService kakaoService;

    public SignController(UserService userService, JwtTokenProvider jwtTokenProvider, ResponseService responseService, PasswordEncoder passwordEncoder, KakaoService kakaoService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.responseService = responseService;
        this.passwordEncoder = passwordEncoder;
        this.kakaoService = kakaoService;
    }


    @ApiOperation(value = "SignIn", notes = "Do signIn")
    @GetMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "email", required = true) @RequestParam String userId,
                                       @ApiParam(value = "passWord", required = true) @RequestParam String passWord) {

        User user = userService.selectUser(userId).getData();


        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
    }

    @ApiOperation(value = "SignUp", notes = "Do signUp")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "email", required = true) @RequestParam String id,
                               @ApiParam(value = "passWord", required = true) @RequestParam String password,
                               @ApiParam(value = "name", required = true) @RequestParam String name) {

        userService.insertUser(User.builder()
                .userId(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "SignIn by Provider", notes = "SignIn by Provider")
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "Service provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "access_token", required = true) @RequestParam String accessToken) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        User user = userService.selectProviderUser(String.valueOf(profile.getId()), provider).getData();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUsername()), user.getRoles()));
    }

    @ApiOperation(value = "SignUp by Provider", notes = "SignUp by Provider")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signupProvider(@ApiParam(value = "Service provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                       @ApiParam(value = "access_token", required = true) @RequestParam String accessToken,
                                       @ApiParam(value = "name", required = true) @RequestParam String name) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        User user = userService.selectProviderUser(String.valueOf(profile.getId()), provider).getData();
        if(!user.getUserId().isEmpty())
            throw new CUserExistException();

        userService.insertUser(User.builder()
                .userId(String.valueOf(profile.getId()))
                .provider(provider)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return responseService.getSuccessResult();
    }
}
