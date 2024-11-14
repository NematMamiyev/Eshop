/*
package az.orient.eshop.securitytoken;

import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenUtility {

    private final UserTokenRepository userTokenRepository;

    public UserToken checkToken(String token){
        if (token == null){
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
        }
        UserToken userToken = userTokenRepository.findUserTokenByTokenAndActive(token, EnumAvailableStatus.ACTIVE.getValue());
        if (userToken == null){
            throw new EshopException(ExceptionConstants.USER_TOKEN_NOT_FOUND,"UserToken not found");
        }
        return userToken;
    }

}
*/
