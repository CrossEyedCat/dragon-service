package org.example.dragonservice.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "coordinates")
public class Coordinates {
    private Float x;
    private Float y;

    @XmlElement
    public Float getX() { return x; }
    public void setX(Float x) { this.x = x; }

    @XmlElement
    public Float getY() { return y; }
    public void setY(Float y) { this.y = y; }
}