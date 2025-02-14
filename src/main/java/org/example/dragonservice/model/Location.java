package org.example.dragonservice.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "location")
public class Location {
    private Float x;
    private Float y;
    private Float z;

    @XmlElement
    public Float getX() { return x; }
    public void setX(Float x) { this.x = x; }

    @XmlElement
    public Float getY() { return y; }
    public void setY(Float y) { this.y = y; }

    @XmlElement
    public Float getZ() { return z; }
    public void setZ(Float z) { this.z = z; }
}
