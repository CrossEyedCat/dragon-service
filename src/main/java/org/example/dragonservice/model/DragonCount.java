package org.example.dragonservice.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "dragonCount")
public class DragonCount {
    private Integer count;

    // Геттеры и сеттеры

    @XmlElement
    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
}