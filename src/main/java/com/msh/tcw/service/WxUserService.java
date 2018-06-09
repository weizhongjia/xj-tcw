package com.msh.tcw.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.msh.tcw.core.Service;
import com.msh.tcw.domain.WxUser;

public interface WxUserService extends Service<WxUser> {
    Page<WxUser> findUserPage(Page<WxUser> page);
}
