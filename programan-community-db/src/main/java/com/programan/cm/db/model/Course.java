package com.programan.cm.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="teacher_name")
    private String teacherName;

    @Column(name="teacher_job")
    private String teacherJob;

    @Column(name="introduction")
    private String introduction;

    @Column(name="price")
    private Long price;

    @Column(name="img_url")
    private String imgUrl;

    @Column(name="video_url")
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name="topic_id")
    private Topic topic;

}
