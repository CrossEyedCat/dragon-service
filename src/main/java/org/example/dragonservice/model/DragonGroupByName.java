package org.example.dragonservice.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "group")
public class DragonGroupByName {
    private String name;
    private Integer count;

    // Геттеры и сеттеры

    @XmlElement
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlElement
    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
}
