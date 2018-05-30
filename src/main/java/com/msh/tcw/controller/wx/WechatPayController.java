package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.dto.PresentGiftDTO;
import com.msh.tcw.dto.SendRedpackDTO;
import com.msh.tcw.service.OrderService;
import com.msh.tcw.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import weixin.popular.bean.paymch.UnifiedorderResult;

import javax.servlet.ServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/wx/pay")
public class WechatPayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private WechatService wechatService;

    @PostMapping("/unified/order")
    public Result payUnifiedorder(@RequestBody PresentGiftDTO pay, ServletRequest request){
        UnifiedorderResult result = orderService.createGiftOrder(pay.getGiftId(), pay.getNumber(), "10.254.86.200", pay.getRoomId());
        return ResultGenerator.genSuccessResult(wechatService.genWxPaymentDTO(result));
    }

    @PostMapping("/redpack/order")
    public Result redpackOrder(@RequestBody SendRedpackDTO redpackDTO) {
        orderService.createRedpackOrder(redpackDTO.getMoney(), redpackDTO.getNumber(), "10.254.86.200", redpackDTO.getRoomId());
        return null;
    }

    @GetMapping("/repack/{redpackId}/position")
    public Result redpackPosition(@PathVariable int redpackId){
        return ResultGenerator.genSuccessResult(orderService.getRedpackOrder(redpackId));
    }

    @PostMapping
    public Result paymentNotify() {
        log.info("wechat pay notify callback comming");
        return ResultGenerator.genSuccessResult();
    }
}
