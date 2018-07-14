package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.dto.VideoDTO;
import com.msh.tcw.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wx/video")
public class VideoController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public Result<List<VideoDTO>> getAllVideos(@RequestParam int orderId, @RequestParam int size) {
        return ResultGenerator.genSuccessResult(orderService.getHistoryVideos(orderId, size));
    }
    
}
