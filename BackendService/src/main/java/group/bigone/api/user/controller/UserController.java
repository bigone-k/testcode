package group.bigone.api.user.controller;

import group.bigone.api.common.Service.ResponseService;
import group.bigone.api.user.Service.UserService;
import group.bigone.api.user.domain.User;
import group.bigone.api.common.domain.CommonResult;
import group.bigone.api.common.domain.ListResult;
import group.bigone.api.common.domain.SingleResult;
import io.swagger.annotations.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"User"})
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    public UserController(UserService userService, ResponseService responseService) {
        this.userService = userService;
        this.responseService = responseService;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "AccessToken", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "Get User", notes = "Select a User by AccessToken")
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return responseService.getSingleResult(userService.selectUser(userId).getData());
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "AccessToken", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "Update User name", notes = "Update User name Information")
    @PutMapping(value = "/user")
    public CommonResult modify(
            @ApiParam(value = "userid", required = true) @RequestParam String userid,
            @ApiParam(value = "name", required = true) @RequestParam String username) {

        User user = userService.selectUser(userid).getData();

        user.setName(username);

        userService.updateUser(user);

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "AccessToken", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "Delete User", notes = "Delete User by userNo")
    @DeleteMapping(value = "/user/{userno}")
    public CommonResult delete(
            @ApiParam(value = "userid", required = true) @PathVariable String userid) {

        userService.deleteUser(userid);

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "AccessToken", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "Get Users", notes = "Select a Users by AccessToken")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUsers() {
        return responseService.getListResult(userService.selectUsers().getData());
    }
}
