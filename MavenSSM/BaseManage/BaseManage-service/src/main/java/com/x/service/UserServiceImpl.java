package com.x.service;

import com.x.api.UserService;
import com.x.mapper.UsersMapper;
import com.x.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users findById(Integer id) {
        Users users = this.usersMapper.selectByPrimaryKey(id);
        return users;
    }
}
