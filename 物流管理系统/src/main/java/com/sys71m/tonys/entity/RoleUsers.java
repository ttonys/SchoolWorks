/**
 * Created by sys71m
 * Date: 2019/10/10
 * Time: 20:23
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
@Entity(name = "role")
@Table(name="role")
public class RoleUsers {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;


    @Column
    private String role_name;

    @Column
    private String role_describe;

    @Column
    private boolean user_controller;

    @Column
    private boolean wuliu_controller;




}
