package com.msh.tcw.controller.admin;

import com.baomidou.mybatisplus.plugins.Page;
import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.model.WxUser;
import com.msh.tcw.service.WxUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* Created by CodeGenerator on 2018/01/03.
*/
@RestController
@RequestMapping("/api/admin/user")
public class UserController {
    @Resource
    private WxUserService wxUserService;

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        WxUser wxUser = wxUserService.findById(id);
        return ResultGenerator.genSuccessResult(wxUser);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        Page<WxUser> list = wxUserService.findUserPage(new Page<>(page, size));
        return ResultGenerator.genSuccessResult(list);
    }
}
