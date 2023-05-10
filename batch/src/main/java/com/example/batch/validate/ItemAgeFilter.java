package com.example.batch.validate;

import com.example.batch.entity.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author chenzhiqin
 * @date 9/5/2023 18:11
 * @info XX
 */
@Component
public class ItemAgeFilter implements ItemProcessor<User, User> {
    @Override
    public User process(User item) throws Exception {
        if (Integer.valueOf(item.getAge()) < 10) {
            return null;
        } else {
            return item;
        }

    }
}
