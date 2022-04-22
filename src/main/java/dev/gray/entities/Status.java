package dev.gray.entities;
/* Author: Grayson Howard
 * Modified: 04/22/2022
 * Enum to limit the values that status can hold
 */

public enum Status {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DENIED("DENIED");

    private final String stat;

    Status(String stat){this.stat=stat;}
    public String getStatus(){return stat;}
}