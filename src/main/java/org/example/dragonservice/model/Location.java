package org.example.dragonservice.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "location")
public class Location {
    private Float x;
    private Double y;
    private Integer z;

    @XmlElement
    public Float getX() { return x; }
    public void setX(Float x) { this.x = x; }

    @XmlElement
    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    @XmlElement
    public Integer getZ() { return z; }
    public void setZ(Integer z) { this.z = z; }
}
