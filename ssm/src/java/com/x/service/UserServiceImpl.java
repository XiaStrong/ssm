package com.x.service;

import com.x.api.UserService;
import com.x.mapper.UsersMapper;
import com.x.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Cacheable(value = "common" ,key = "'id_'+#userId")
    public Users findById(Integer userId) {
        Users users = this.usersMapper.selectByPrimaryKey(userId);
        return users;
    }

    @Override
    public void insert(Users users) {
        this.usersMapper.insert(users);
    }

    //此注解会删除缓存中的就数据，并存入新的数据
    @Override
    @CacheEvict(value = "common" ,key = "'id_'+#users.getUserId()")
    public void update(Users users) {
        this.usersMapper.updateByPrimaryKey(users);
    }


//    //这个注解会把redis缓存中的数据给删除，但是不会更新数据，不知道什么原因，所以尽量还是用上面两种方法，此种方法以后看能否解决
//    @Override
//    @CachePut(value = "common" ,key = "'id_'+#users.getUserId()")
//    public void update(Users users) {
//        this.usersMapper.updateByPrimaryKey(users);
//    }


}
