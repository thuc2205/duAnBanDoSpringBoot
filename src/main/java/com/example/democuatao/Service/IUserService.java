package com.example.democuatao.Service;

import com.example.democuatao.dtos.UserDTO;
import com.example.democuatao.model.User;

import com.shopcuatao.bangiay.exeption.DataNotFound;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFound;

    String login(String phoneNumber,String password) throws Exception;
}
