package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.domain.Order;
import com.msh.tcw.dto.*;
import com.msh.tcw.service.OrderService;
import com.msh.tcw.service.WechatService;
import io.swagger.models.auth.In;
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

    @PostMapping("/gift/order")
    public Result payUnifiedorder(@RequestBody PresentGiftDTO pay, ServletRequest request){
        Order giftOrder = orderService.createGiftOrder(pay.getGiftId(), pay.getNumber(), pay.getRoomId(), pay.getBlessing(), pay.getGif(), pay.getAvatar(), pay.getName());
        UnifiedorderResult result = orderService.createWechatUnifiedOrder(giftOrder.getTotalMoney(), "10.254.86.200", giftOrder.getOutTradeNo());
        OrderDTO orderDTO = new OrderDTO(giftOrder, wechatService.genWxPaymentDTO(result));
        return ResultGenerator.genSuccessResult(orderDTO);
    }

    @PostMapping("/redpack/order")
    public Result redpackOrder(@RequestBody SendRedpackDTO redpackDTO) {
        Order redpackDO = orderService.createRedpackOrder(redpackDTO.getMoney(), redpackDTO.getNumber(), redpackDTO.getRoomId(), redpackDTO.getBlessing());
        UnifiedorderResult result = orderService.createWechatUnifiedOrder(redpackDO.getTotalMoney(), "10.254.86.200", redpackDO.getOutTradeNo());
        OrderDTO redpackOrderDTO = new OrderDTO(redpackDO, wechatService.genWxPaymentDTO(result));
        return ResultGenerator.genSuccessResult(redpackOrderDTO);
    }

    @GetMapping("/repack/{redpackId}/position")
    public Result redpackPosition(@PathVariable int redpackId){
        return ResultGenerator.genSuccessResult(orderService.getRedpackPosition(redpackId));
    }

    @GetMapping("/redpack/{redpackId}/open/{position}")
    public Result openRedpack(@PathVariable int redpackId, @PathVariable int position){
        return ResultGenerator.genSuccessResult(orderService.openRedpack(redpackId, position));
    }

    @PostMapping
    public Result paymentNotify() {
        log.info("wechat pay notify callback comming");
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/showtime/order")
    public Result showtimeOrder(@RequestBody ShowtimeDTO showtime) {
        Order showtimeOrder = orderService.createShowtimeOrder(showtime.getPrice(), showtime.getTime(), showtime.getRoomId(), showtime.getBlessing(), showtime.getType(), showtime.getSrc());
        UnifiedorderResult result = orderService.createWechatUnifiedOrder(showtimeOrder.getTotalMoney(), "10.254.86.200", showtimeOrder.getOutTradeNo());
        OrderDTO orderDTO = new OrderDTO(showtimeOrder, wechatService.genWxPaymentDTO(result));
        return ResultGenerator.genSuccessResult(orderDTO);
    }

    @GetMapping("/account")
    public Result<Integer> getUserAccount(){
        return ResultGenerator.genSuccessResult(orderService.getUserAccount());
    }
}
