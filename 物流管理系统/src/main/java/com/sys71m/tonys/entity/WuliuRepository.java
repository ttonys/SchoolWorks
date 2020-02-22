/**
 * Created by sys71m
 * Date: 2019/10/15
 * Time: 20:42
 **/
package com.sys71m.tonys.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WuliuRepository  extends JpaRepository<WuLiu, Integer> {
    WuLiu getById(Integer id);
}
