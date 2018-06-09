package com.msh.tcw.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.domain.WxUser;
import com.msh.tcw.dto.EncryptedData;
import com.msh.tcw.dto.WxUserDTO;
import com.msh.tcw.security.WxSessionToken;
import com.msh.tcw.service.WxUserService;
import com.msh.tcw.utils.WxDecryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/wx/user")
@Slf4j
public class WxUserController {
    @Resource
    private WxUserService wxUserService;

    @PostMapping
    public Result updateUser(@RequestBody EncryptedData data) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        WxSessionToken token = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        String decryptedData = WxDecryptUtils.decrypt(data.getEncryptedData(), (String)token.getPrincipal(), data.getIv());
        log.debug(decryptedData);
        WxUserDTO userDTO = JSONObject.parseObject(decryptedData, WxUserDTO.class);
        WxUser user = wxUserService.findBy("openid", userDTO.getOpenId());
        WxUser userinfo = userDTO.instance();
        if (user == null) {
           wxUserService.save(userinfo);
        } else {
            wxUserService.update(userinfo);
        }
        return ResultGenerator.genSuccessResult(userDTO);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        wxUserService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

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
