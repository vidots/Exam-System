package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.PageResponse;
import com.edu.mapper.ResourceMapper;
import com.edu.mapper.ResourceTypeMapper;
import com.edu.model.Resource;
import com.edu.model.ResourceType;
import com.edu.service.ResourceService;
import com.edu.vo.req.ResourceQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceTypeMapper resourceTypeMapper;
    private final ResourceMapper resourceMapper;

    public ResourceServiceImpl(ResourceTypeMapper resourceTypeMapper, ResourceMapper resourceMapper) {
        this.resourceTypeMapper = resourceTypeMapper;
        this.resourceMapper = resourceMapper;
    }

    @Override
    public ResourceType addResourceType(ResourceType resourceType) {
        resourceType.setId(null);
        resourceTypeMapper.insert(resourceType);
        return resourceType;
    }

    @Override
    public ResourceType editResourceType(ResourceType resourceType) {
        resourceTypeMapper.updateById(resourceType);
        return resourceType;
    }

    @Override
    public int deleteResourceType(Integer id) {
        return resourceTypeMapper.deleteById(id);
    }

    @Override
    public List<ResourceType> listResourceTypes() {
        return resourceTypeMapper.selectList(null);
    }

    @Override
    public Resource addResource(Resource resource) {
        resource.setId(null);
        resourceMapper.insert(resource);
        return resource;
    }

    @Override
    public Resource editResource(Resource resource, Integer id) {
        resourceMapper.updateById(resource);
        return resource;
    }

    @Override
    public int deleteResource(Integer id) {
        return resourceMapper.deleteById(id);
    }

    @Override
    public Resource getResource(Integer id) {
        return resourceMapper.selectById(id);
    }

    @Override
    public List<Resource> listAllResources(Integer typeId) {
        QueryWrapper<Resource> wrapper = new QueryWrapper();
        wrapper.eq("type_id", typeId);
        return resourceMapper.selectList(wrapper);
    }

    @Override
    public PageResponse<Resource> listResource(Integer current, Integer size, ResourceQuery resourceQuery) {
        IPage<Resource> page = new Page<>(current, size);
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        if (resourceQuery.getResourceType() != null) {
            wrapper.eq("type_id", resourceQuery.getResourceType());
        }
        if (resourceQuery.getQuery() != null) {
            wrapper.like("title", resourceQuery.getQuery());
        }
        resourceMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }
}
