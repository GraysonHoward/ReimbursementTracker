package dev.gray.entities;

public enum Status {
    PENDING("Pending"),
    APPROVED("Approved"),
    DENIED("Denied");

    private final String stat;

    Status(String stat){this.stat=stat;}
    public String getStatus(){return stat;}
}