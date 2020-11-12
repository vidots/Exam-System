package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.BusinessException;
import com.edu.base.PageResponse;
import com.edu.base.Role;
import com.edu.base.UsedCode;
import com.edu.mapper.AuthMapper;
import com.edu.mapper.VIPCodeMapper;
import com.edu.model.Auth;
import com.edu.model.VIPCode;
import com.edu.service.VIPCodeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VIPCodeServiceImpl implements VIPCodeService {

    private final VIPCodeMapper vipCodeMapper;
    private final AuthMapper authMapper;

    public VIPCodeServiceImpl(VIPCodeMapper vipCodeMapper, AuthMapper authMapper) {
        this.vipCodeMapper = vipCodeMapper;
        this.authMapper = authMapper;
    }

    @Override
    public VIPCode addVIPCode() {
        VIPCode vipCode = new VIPCode();
        vipCode.setUsed(UsedCode.UNUSED.getVipCode());
        vipCode.setCode(UUID.randomUUID().toString());
        vipCodeMapper.insert(vipCode);
        return vipCode;
    }

    @Override
    public Integer useVIPCode(String code, Integer userId) {
        QueryWrapper<VIPCode> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        wrapper.eq("used", UsedCode.UNUSED.getVipCode());
        List<VIPCode> codeList = vipCodeMapper.selectList(wrapper);
        if (codeList.size() == 0) {
            throw new BusinessException("该激活码无效，请联系客服");
        }
        UpdateWrapper<VIPCode> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("used", UsedCode.USED.getVipCode());
        updateWrapper.eq("code", code);
        vipCodeMapper.update(null, updateWrapper);
        UpdateWrapper<Auth> uWrappter = new UpdateWrapper<>();
        uWrappter.eq("id", userId);
        uWrappter.set("role", Role.VIP.getRole());
        authMapper.update(null, uWrappter);
        return 1;
    }

    @Override
    public String userRole(Integer userId) {
        Auth auth = authMapper.selectById(userId);
        if (auth == null) {
            throw new BusinessException("该用户不存在");
        }
        return auth.getRole();
    }

    @Override
    public PageResponse<VIPCode> listVIPCode(Integer current, Integer size, Integer used) {
        IPage<VIPCode> page = new Page<>(current, size);
        QueryWrapper<VIPCode> wrapper = new QueryWrapper<>();
        if (used == 0 || used == 1) {
            wrapper.eq("used", used);
        }
        wrapper.orderByDesc("id");
        vipCodeMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

}
