package com.hzsf.chronicanalysis.service;

import com.hzsf.chronicanalysis.user.entity.CustomUser;

public interface ITokenService {

    /**
     * 存储Token
     * @param customUser
     * @return
     */
    Boolean saveToken(CustomUser customUser);

    /**
     *
     * @param token
     * @return
     */
    CustomUser getTokenInfo(String token);

}
