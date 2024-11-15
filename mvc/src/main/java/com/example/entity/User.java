package com.example.entity;

import com.example.ant.Secure;
import lombok.Data;

/**
 * @author czq
 * @date 2023/10/16 11:43
 * @Description:
 */
@Data
@Secure
public class User {

    private String id;

    private String name;
}
