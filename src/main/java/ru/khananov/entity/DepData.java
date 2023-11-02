package ru.khananov.entity;

import java.util.Objects;

public class DepData {
    Long id;
    String depCode;
    String depJob;
    String description;

    public DepData() {
    }

    public DepData(String depCode, String depJob, String description) {
        this.depCode = depCode;
        this.depJob = depJob;
        this.description = description;
    }

    public DepData(Long id, String depCode, String depJob, String description) {
        this.id = id;
        this.depCode = depCode;
        this.depJob = depJob;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepJob() {
        return depJob;
    }

    public void setDepJob(String depJob) {
        this.depJob = depJob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepData depData = (DepData) o;
        return Objects.equals(depCode, depData.depCode) && Objects.equals(depJob, depData.depJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCode, depJob);
    }
}