/**
 * Created by sys71m
 * Date: 2019/10/10
 * Time: 20:42
 **/
package com.sys71m.tonys.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Roles getById(Integer id);
    Roles getByRoleName(String name);
}
