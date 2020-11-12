package com.edu.controller.admin;

import com.edu.base.RestResponse;
import com.edu.service.VIPCodeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class VIPCodeController {

    private final VIPCodeService vipCodeService;

    public VIPCodeController(VIPCodeService vipCodeService) {
        this.vipCodeService = vipCodeService;
    }

    @PostMapping("/code/generate")
    public RestResponse addVIPCode() {
        return RestResponse.ok(vipCodeService.addVIPCode());
    }

    @PostMapping("/code/list/{current}/{size}")
    public RestResponse listVIPCodes(@PathVariable("current") Integer current, @PathVariable("size") Integer size, Integer used) {
        return RestResponse.ok(vipCodeService.listVIPCode(current, size, used));
    }
}
