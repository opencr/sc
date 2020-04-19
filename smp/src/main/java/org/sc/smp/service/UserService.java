package org.sc.smp.service;

import org.sc.smp.entity.UserEntity;
import org.sc.smp.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserEntityMapper userEntityMapper;

    public List<UserEntity> selectList(){
        return userEntityMapper.selectList(null);
    }

    public List<UserEntity> findList(){
        return userEntityMapper.findList();
    }
}
