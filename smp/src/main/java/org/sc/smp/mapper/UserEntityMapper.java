package org.sc.smp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sc.smp.entity.UserEntity;

import java.util.List;

@Mapper
public interface UserEntityMapper extends BaseMapper<UserEntity> {
    public List<UserEntity> findList();
}
