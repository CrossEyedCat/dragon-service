package org.example.dragonservice.model;



import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlRootElement(name = "dragons")
public class DragonList {

    private List<Dragon> dragons;

    public DragonList() {
    }

    public DragonList(List<Dragon> dragons) {
        this.dragons = dragons;
    }

    @XmlElement(name = "dragon")
    public List<Dragon> getDragons() {
        return dragons;
    }

    public void setDragons(List<Dragon> dragons) {
        this.dragons = dragons;
    }
}