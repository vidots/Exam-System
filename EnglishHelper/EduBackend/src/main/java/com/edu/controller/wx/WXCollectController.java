package com.edu.controller.wx;

import com.edu.base.RestResponse;
import com.edu.base.WxContext;
import com.edu.model.Collect;
import com.edu.model.Question;
import com.edu.service.CollectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wx")
public class WXCollectController {

    private final CollectService collectService;
    private final WxContext wxContext;


    public WXCollectController(CollectService collectService, WxContext wxContext) {
        this.collectService = collectService;
        this.wxContext = wxContext;
    }

    @PostMapping("/collect/list")
    public RestResponse listCollects() {
        List<Question> questionList = collectService.listCollects(wxContext.getCurrentAuth().getUserId());
        return RestResponse.ok(questionList);
    }

    @PostMapping("/collect/add")
    public RestResponse addCollect(Integer questionId) {
        Collect collect = collectService.addCollect(questionId, wxContext.getCurrentAuth().getUserId());
        return RestResponse.ok(collect);
    }

    @PostMapping("/collect/cancel")
    public RestResponse cancelCollect(Integer questionId) {
        collectService.cancelCollect(questionId, wxContext.getCurrentAuth().getUserId());
        return RestResponse.ok();
    }

    @PostMapping("/collect/is")
    public RestResponse isCollect(Integer questionId) {
        Boolean is = collectService.isCollected(questionId, wxContext.getCurrentAuth().getUserId());
        return RestResponse.ok(is ? "true" : "false");
    }
}
