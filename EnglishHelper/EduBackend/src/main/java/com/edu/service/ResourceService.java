package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.Resource;
import com.edu.model.ResourceType;
import com.edu.vo.req.ResourceQuery;

import java.util.List;

public interface ResourceService {
    ResourceType addResourceType(ResourceType resourceType);
    ResourceType editResourceType(ResourceType resourceType);
    int deleteResourceType(Integer id);
    List<ResourceType> listResourceTypes();

    Resource addResource(Resource resource);
    Resource editResource(Resource resource, Integer id);
    int deleteResource(Integer id);
    Resource getResource(Integer id);
    List<Resource> listAllResources(Integer typeId);
    PageResponse<Resource> listResource(Integer current, Integer size, ResourceQuery resourceQuery);

}
