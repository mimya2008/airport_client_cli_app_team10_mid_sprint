package com.keyin.domain;


public class Aircraft {
    private String id;
    private String model;

    public String getId() {return id; }
    public void setId(String id) { this. id = id; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    @Override
    public String toString() {
        return "Aircraft{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
