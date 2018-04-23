package com.msh.tcw.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.dto.EncryptedData;
import com.msh.tcw.dto.WxUserDTO;
import com.msh.tcw.model.WxUser;
import com.msh.tcw.security.WxSessionToken;
import com.msh.tcw.service.WxUserService;
import com.msh.tcw.utils.WxDecryptUtils;
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
import java.util.List;

/**
* Created by CodeGenerator on 2018/01/03.
*/
@RestController
@RequestMapping("/wx/user")
public class WxUserController {
    @Resource
    private WxUserService wxUserService;

    @PostMapping
    public Result updateUser(@RequestBody EncryptedData data) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        WxSessionToken token = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        String decryptedData = WxDecryptUtils.decrypt(data.getEncryptedData(), token.getSession_key(), data.getIv());
        WxUserDTO userDTO = JSONObject.parseObject(decryptedData, WxUserDTO.class);
        WxUser user = wxUserService.findBy("openid", userDTO.getOpenId());
        if (user == null) {
           wxUserService.save(userDTO.getInstance());
        } else {
            wxUserService.update(userDTO.getInstance());
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        wxUserService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody WxUser wxUser) {
        wxUserService.update(wxUser);
        return ResultGenerator.genSuccessResult();
    }

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
