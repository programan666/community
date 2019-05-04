package com.programan.cm.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LatestTrend {

    private String visitDate;

    private String visitArea;

    private String visitIP;

    private String os;

    private String visitorType;

    private String resolution;

    private String browser;

    private String visitTime;

}
