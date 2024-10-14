package com.spring.electronics.user;

public interface UserService {

    /**
     * Update account password
     *
     * @param request request
     * @return UserDto
     * @throws Exception Exception
     */
    UserDto updatePassword(UpdateProfileRequest request) throws Exception;

}
