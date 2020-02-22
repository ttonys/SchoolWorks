/**
 * Created by sys71m
 * Date: 2019/10/10
 * Time: 20:56
 **/
package com.sys71m.tonys.service;

import com.sys71m.tonys.entity.RoleRepository;
import com.sys71m.tonys.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleImpl implements  RoleService{

    private final RoleRepository roleRepository;

    @Autowired
    public RoleImpl(RoleRepository roleRepository){
        this.roleRepository= roleRepository;
    }

    @Override
    public void add(Roles role) {
        this.roleRepository.save(role);
    }

    @Override
    public void deleteByID(Integer id) {
        this.roleRepository.deleteById(id);
    }



    @Override
    public void update(Roles role) {
        this.roleRepository.save(role);
    }

    @Override
    public String getName(Integer id) {
        return roleRepository.getById(id).getRoleName();
    }

    @Override
    public Roles getRole(String name) {
        return roleRepository.getByRoleName(name);
    }


    @Override
    public List<Roles> getAllRoles() {
        return this.roleRepository.findAll();
    }


}
