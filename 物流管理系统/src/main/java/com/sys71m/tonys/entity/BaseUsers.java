/**
 * Created by sys71m
 * Date: 2019/9/17
 * Time: 16:20
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
@Entity(name = "users")
@Table(name="users")
public class BaseUsers {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String roleID;


    @Column
    private String createTime;





}
