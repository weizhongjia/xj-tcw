package com.msh.tcw.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.model.WxUser;
import com.msh.tcw.service.WxUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
        PageHelper.startPage(page, size);
        List<WxUser> list = wxUserService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
