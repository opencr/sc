package org.sc.smp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("mto_user")
public class UserEntity {
    private String id;
    private String name;
    private String username;
    private String email;
    private Date created;
    private int status;
    private Date lastLogin;
}
