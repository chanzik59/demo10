package com.example.batch.entity;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

/**
 * @author chenzhiqin
 * @date 20/4/2023 11:28
 * @info 字段内容对不上映射
 */
@Component
public class UserMapper implements FieldSetMapper<User> {
    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {
        User user = new User();
        user.setId(fieldSet.readLong("id"));
        user.setName(fieldSet.readString("name"));
        user.setAge(fieldSet.readInt("age"));
        String address = fieldSet.readString("province") + fieldSet.readString("city") + fieldSet.readString("street");
        user.setAddress(address);
        return user;
    }
}
