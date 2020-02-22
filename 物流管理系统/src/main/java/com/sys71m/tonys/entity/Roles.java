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
public class Roles {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;


    @Column
    private String roleName;

    @Column
    private String roleDescribe;



    @Column
    private String userController;

    @Column
    private String wuliuController;

    @Column
    private String roleController;




}
