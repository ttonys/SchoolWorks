/**
 * Created by sys71m
 * Date: 2019/10/15
 * Time: 20:47
 **/
package com.sys71m.tonys.service;

import com.sys71m.tonys.entity.WuLiu;
import com.sys71m.tonys.entity.WuliuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WuLiuImpl implements WuLiuService {

    private final WuliuRepository wuliuRepository;

    @Autowired
    public WuLiuImpl(WuliuRepository wuliuRepository){
        this.wuliuRepository = wuliuRepository;
    }

    @Override
    public void add(WuLiu wuliu) {
        wuliuRepository.save(wuliu);
    }

    @Override
    public void delete(Integer id) {
        wuliuRepository.deleteById(id);
    }

    @Override
    public List<WuLiu> getAllOrder() {
        return wuliuRepository.findAll();
    }
}
