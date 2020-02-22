/**
 * Created by sys71m
 * Date: 2019/9/17
 * Time: 16:32
 **/
package com.sys71m.tonys.entity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<BaseUsers, Integer> {
    BaseUsers getById(Integer id);
    BaseUsers findByUsername(String name);
    boolean existsByUsername(String name);
}
