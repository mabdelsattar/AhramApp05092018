package com.alahram.alahramapp.model;

public class Status {
    private String Id,Name;

    @Override
    public String toString() {
        return "Status{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }

    public Status(String id, String name) {
        Id = id;
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
