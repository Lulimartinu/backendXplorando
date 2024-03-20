package com.dh.Xplorando.service;

import com.dh.Xplorando.entity.User;

import java.util.List;


public interface IUserService {
    User registerUser(User user);

    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}