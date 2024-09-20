package com.lycodeing.security.service;

import com.lycodeing.security.entity.SecurityUserDetail;

public interface SecurityUserService {

    SecurityUserDetail loadUserByUsername(String identifier);
}
