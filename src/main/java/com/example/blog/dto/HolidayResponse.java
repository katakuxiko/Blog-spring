package com.example.blog.dto;

import java.util.List;

public class HolidayResponse {
    private String id;
    private String startDate;
    private String endDate;
    private String type;
    private List<LocalizedText> name;
    private String regionalScope;
    private String temporalScope;
    private boolean nationwide;
    private List<Subdivision> subdivisions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LocalizedText> getName() {
        return name;
    }

    public void setName(List<LocalizedText> name) {
        this.name = name;
    }

    public String getRegionalScope() {
        return regionalScope;
    }

    public void setRegionalScope(String regionalScope) {
        this.regionalScope = regionalScope;
    }

    public String getTemporalScope() {
        return temporalScope;
    }

    public void setTemporalScope(String temporalScope) {
        this.temporalScope = temporalScope;
    }

    public boolean isNationwide() {
        return nationwide;
    }

    public void setNationwide(boolean nationwide) {
        this.nationwide = nationwide;
    }

    public List<Subdivision> getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(List<Subdivision> subdivisions) {
        this.subdivisions = subdivisions;
    }
}
