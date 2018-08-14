package com.x.api;

import com.x.pojo.Users;

public interface UserService {

    public Users findById(Integer id);

    public void insert (Users users);

    public void update(Users users);

}
