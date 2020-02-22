/**
 * Created by sys71m
 * Date: 2019/9/19
 * Time: 10:34
 **/
package com.sys71m.tonys.service;

import com.sys71m.tonys.entity.UserRepository;
import com.sys71m.tonys.entity.BaseUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public BaseUsers add(BaseUsers user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public BaseUsers update(BaseUsers user) {
        return userRepository.save(user);
    }

    @Override
    public List<BaseUsers> selectList() {
        return userRepository.findAll();
    }

    @Override
    public BaseUsers selectById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public BaseUsers selectByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public boolean existsByName(String name) {
        return userRepository.existsByUsername(name);
    }

}
