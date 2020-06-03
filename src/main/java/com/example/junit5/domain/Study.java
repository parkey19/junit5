package com.example.junit5.domain;

public class Study {
    private StudyStatus status = StudyStatus.DRAFT;
    private int limitCount;
    private String name;

    public Study(StudyStatus status, int limitCount) {
        this.status = status;
        this.limitCount = limitCount;
    }

    public Study(int limitCount) {
        this.limitCount = limitCount;
    }

    public Study(StudyStatus status, int limitCount, String name) {
        this.status = status;
        this.limitCount = limitCount;
        this.name = name;
    }

    public Study(String name) {
        this.name = name;
    }

    public Study(int limitCount, String name) {
        this.limitCount = limitCount;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limitCount=" + limitCount +
                ", name='" + name + '\'' +
                '}';
    }

    public StudyStatus getStatus() {
        return status;
    }

    public void setStatus(StudyStatus status) {
        this.status = status;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }
}
