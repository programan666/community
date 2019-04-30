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
@Table(name = "file_download")
public class FileDownload {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "file_type_id")
    private FileType fileType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="upload_date")
    private Date uploadDate;

    @Column(name="price")
    private Long price;

    @Column(name="introduction")
    private String introduction;

    @Column(name="download_num")
    private Long downloadNum;

    @Column(name = "url")
    private String url;

}
