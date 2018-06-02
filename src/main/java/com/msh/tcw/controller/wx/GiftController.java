package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wx/gift")
public class GiftController {

    @Autowired
    private GiftService giftService;

    @GetMapping
    public Result getAllGift(){
        return ResultGenerator.genSuccessResult(giftService.getAllGift());
    }

}
