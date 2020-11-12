package com.edu.controller.wx;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Resource;
import com.edu.model.ResourceType;
import com.edu.service.ResourceService;
import com.edu.vo.req.ResourceQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wx")
public class WXResourceController {
    private final ResourceService resourceService;

    public WXResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/resource-type/list")
    public RestResponse listResourceTypes() {
        List<ResourceType> resourceTypes = resourceService.listResourceTypes();
        return RestResponse.ok(resourceTypes);
    }

    @PostMapping("/resource/list/{typeId}")
    public RestResponse listResource(@PathVariable("typeId") int typeId) {
        List<Resource> resources = resourceService.listAllResources(typeId);
        return RestResponse.ok(resources);
    }
}
