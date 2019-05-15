package com.programan.cm.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_name")
    private String userName;

    @Column(name="pwd")
    private String pwd;

    @Column(name="role_name")
    private String roleName;

    @Column(name="real_name")
    private String realName;

    @Column(name="sex")
    private String sex;

    @Column(name="birthday")
    private Date birthday;

    @Column(name="phone")
    private String phone;

    @Column(name="area")
    private String area;

    @ManyToOne
    @JoinColumn(name="industry_id")
    private Industry industry;

    @Column(name="job_name")
    private String jobName;

    @Column(name="introduction")
    private String introduction;

    @Column(name="head_img_url")
    private String headImgUrl;

    @Column(name="p_num")
    private Long pnum;

    @Column(name="create_date")
    private Date createDate;

}
