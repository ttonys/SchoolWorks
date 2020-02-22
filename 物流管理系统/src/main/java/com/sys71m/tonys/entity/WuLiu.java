/**
 * Created by sys71m
 * Date: 2019/10/15
 * Time: 20:34
 **/
package com.sys71m.tonys.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "wuliu")
@Table(name="wuliu")
public class WuLiu {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column
    private String orderID;

    @Column
    private String revName;

    @Column
    private String phone;

    @Column
    private String revAddress;

    @Column
    private String payMethod;

    @Column
    private String sendMode;


    @Column
    private String money;

    @Column
    private String orderState;



}
