package com.msh.tcw.security;


import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/user")
public class WxLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/_login")
    public Result login(@RequestParam String code) {

        final Authentication authentication = authenticationManager.authenticate(
                new WxSessionToken(code)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = TokenAuthenticationUtils.generateToken((WxSessionToken) authentication);
        return ResultGenerator.genSuccessResult(token);
    }
}
