package com.programan.cm.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "advertisement_2")
public class Advertisement2 {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "text_h")
    private String textH;

    @Column(name = "text_p")
    private String textP;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "location")
    private String location;

    @Column(name = "url")
    private String url;

}
