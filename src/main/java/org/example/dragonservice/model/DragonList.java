package org.example.dragonservice.model;


import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlRootElement(name = "dragons")
public class DragonList {
    private List<Dragon> dragons;

    @XmlElementWrapper(name = "dragons")
    @XmlElement(name = "dragon")
    public List<Dragon> getDragons() { return dragons; }
    public void setDragons(List<Dragon> dragons) { this.dragons = dragons; }
}