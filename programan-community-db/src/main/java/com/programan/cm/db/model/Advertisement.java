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
@Table(name = "advertisement")
public class Advertisement {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "url")
    private String url;

    @Column(name = "slide_text")
    private String slideText;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "orders")
    private Integer order;

}
