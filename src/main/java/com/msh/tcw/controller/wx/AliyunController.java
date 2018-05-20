package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.security.WxSessionToken;
import com.msh.tcw.service.AliyunOSSService;
import com.msh.tcw.service.AliyunSTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wx/aliyun")
public class AliyunController {

    @Autowired
    private AliyunSTSService aliyunSTSService;
    @Autowired
    private AliyunOSSService aliyunOSSService;

    @GetMapping("/token")
    public Result genToken() {
        WxSessionToken token = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        return ResultGenerator.genSuccessResult(aliyunSTSService.genToken(token.getDetails().getOpenid()));
    }

    @GetMapping("/form")
    public Result genFormData() {
        return ResultGenerator.genSuccessResult(aliyunOSSService.genFormData());
    }
}
