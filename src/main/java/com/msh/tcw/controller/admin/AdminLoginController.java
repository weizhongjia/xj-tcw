package com.msh.tcw.controller.admin;


import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.dto.JwtAuthenticationRequest;
import com.msh.tcw.security.TokenAuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class AdminLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/_login")
    public Result login(@RequestBody JwtAuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authToken);
        final String token = TokenAuthenticationUtils.generateToken(authToken);
        return ResultGenerator.genSuccessResult(token);
    }

    @GetMapping
    public Result get(@RequestHeader(name = "wx-group-token") String token) {
        return ResultGenerator.genSuccessResult("ok");
    }
}
