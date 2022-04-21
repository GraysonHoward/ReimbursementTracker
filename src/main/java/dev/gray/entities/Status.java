package dev.gray.entities;

public enum Status {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DENIED("DENIED");

    private final String stat;

    Status(String stat){this.stat=stat;}
    public String getStatus(){return stat;}
}