package org.example.dragonservice.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "coordinates")
public class Coordinates {
    private Float x;
    private Integer y;

    @XmlElement
    public Float getX() { return x; }
    public void setX(Float x) { this.x = x; }

    @XmlElement
    public Integer getY() { return y; }
    public void setY(Integer y) { this.y = y; }
}