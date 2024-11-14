/*
package az.orient.eshop.securitytoken;

import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final TokenUtility tokenUtility;

    @Override
    public Response<RespUser> auth(ReqUser reqUser) {
        Response<RespUser> response = new Response<>();
        try {
            String username = reqUser.getUsername();
            String password = reqUser.getPassword();
            if (username == null || password == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "invalid request data");
            }
            User user = userRepository.findUserByUsernameAndPasswordAndActive(username, password, EnumAvailableStatus.ACTIVE.getValue());
            if (user == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "username or password incorrect");
            }
            UserToken userToken = userTokenRepository.findUserTokenByUserAndActive(user, EnumAvailableStatus.ACTIVE.getValue());
            if (userToken != null) {
                throw new EshopException(ExceptionConstants.SESSION_ALREADY_EXIST, "Session already exist");
            }
            String token = UUID.randomUUID().toString();
            userToken = new UserToken();
            userToken.setUser(user);
            userToken.setToken(token);
            userTokenRepository.save(userToken);
            RespUser respUser = new RespUser();
            respUser.setUserId(user.getId());
            respUser.setToken(token);
            respUser.setFullName(user.getFullName());
            response.setT(respUser);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response logout(String token) {
        Response response = new Response<>();
        try {
            UserToken userToken = tokenUtility.checkToken(token);
            userToken.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            userTokenRepository.save(userToken);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }
}
*/
