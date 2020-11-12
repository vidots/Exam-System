package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.VIPCode;

public interface VIPCodeService {
    VIPCode addVIPCode();
    Integer useVIPCode(String code, Integer userId);
    String userRole(Integer userId);
    PageResponse<VIPCode> listVIPCode(Integer current, Integer size, Integer used);

}
