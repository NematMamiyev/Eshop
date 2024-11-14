/*
package az.orient.eshop.securitytoken;


import az.orient.eshop.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth")
    public Response<RespUser> auth(@RequestBody ReqUser reqUser){
        return userService.auth(reqUser);
    }

    @PostMapping("/logout")
    public Response logout(@RequestHeader String token){
        return userService.logout(token);
    }
}
*/
