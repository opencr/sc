package org.sc.smp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sc.smp.mq.CancelOrderSender;
import org.sc.smp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuhenghuo
 */
@Api(value = "用户接口")
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private CancelOrderSender cancelOrderSender;

    @ApiOperation(value = "查找全部用户" ,  notes="查找全部用户")
    @GetMapping("/selectList")
    public Object selectList(){
        return userService.selectList();
    }

    @ApiOperation(value = "查找用户" ,  notes="查找用户")
    @GetMapping("/findList")
    public Object findList(){
        return userService.findList();
    }

    @GetMapping("/testMQ")
    public void testMQ(){
        cancelOrderSender.sendMessage(System.currentTimeMillis()+"", null);
    }
}
