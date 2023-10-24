package com.user.service;

import java.math.BigDecimal;

/**
 * @author chenzhiqin
 * @date 1/9/2023 14:25
 * @info
 */
public interface UserService {

    /**
     * 扣款
     *
     * @param id
     * @param amt
     * @return
     */
    boolean deduction(Long id, BigDecimal amt);
}
