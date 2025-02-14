package org.example.dragonservice.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "groups")
public class DragonGroupByNameList {
    private List<DragonGroupByName> groups;

    @XmlElement(name = "group")
    public List<DragonGroupByName> getGroups() {
        return groups;
    }

    public void setGroups(List<DragonGroupByName> groups) {
        this.groups = groups;
    }
}