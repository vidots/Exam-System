package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Resource;
import com.edu.model.ResourceType;
import com.edu.service.ResourceService;
import com.edu.vo.req.ResourceQuery;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/resource-type/list")
    public RestResponse listResourceTypes() {
        List<ResourceType> resourceTypes = resourceService.listResourceTypes();
        return RestResponse.ok(resourceTypes);
    }

    @PostMapping("/resource-type/add")
    public RestResponse addResourceType(@RequestBody ResourceType resourceType) {
        ResourceType savedRE = resourceService.addResourceType(resourceType);
        return RestResponse.ok(savedRE);
    }

    @PostMapping("/resource-type/edit")
    public RestResponse editResourceType(@RequestBody ResourceType resourceType) {
        ResourceType editedRE = resourceService.editResourceType(resourceType);
        return RestResponse.ok(editedRE);
    }

    @PostMapping("/resource-type/delete/{id}")
    public RestResponse deleteResourceType(@PathVariable Integer id) {
        resourceService.deleteResourceType(id);
        return RestResponse.ok();
    }

    @GetMapping("/resource/list/{current}/{size}")
    public RestResponse listResource(@PathVariable("current") int current, @PathVariable("size") int size, ResourceQuery resourceQuery) {
        PageResponse pageResponse = resourceService.listResource(current, size, resourceQuery);
        return RestResponse.ok(pageResponse);
    }

    @GetMapping("/resource/{id}")
    public RestResponse getResource(@PathVariable("id") Integer id) {
        return RestResponse.ok(resourceService.getResource(id));
    }

    @PostMapping("/resource/add")
    public RestResponse addResourceType(@RequestBody Resource resource) {
        Resource savedR = resourceService.addResource(resource);
        return RestResponse.ok(savedR);
    }

    @PostMapping("/resource/edit/{id}")
    public RestResponse editResource(@PathVariable("id")Integer id, @RequestBody Resource resource) {
        Resource editedR = resourceService.editResource(resource, id);
        return RestResponse.ok(editedR);
    }

    @PostMapping("/resource/delete/{id}")
    public RestResponse deleteResource(@PathVariable Integer id) {
        resourceService.deleteResource(id);
        return RestResponse.ok();
    }

}


















