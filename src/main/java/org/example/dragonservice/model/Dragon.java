package org.example.dragonservice.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlRootElement(name = "dragon")
public class Dragon {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private Integer age;
    private Double wingspan;
    private Boolean speaking;
    private DragonType type;
    private Person killer;

    @XmlElement
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @XmlElement
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlElement
    public Coordinates getCoordinates() { return coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

    @XmlElement
    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.time.LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @XmlElement
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    @XmlElement
    public Double getWingspan() { return wingspan; }
    public void setWingspan(Double wingspan) { this.wingspan = wingspan; }

    @XmlElement
    public Boolean getSpeaking() { return speaking; }
    public void setSpeaking(Boolean speaking) { this.speaking = speaking; }

    @XmlElement
    public DragonType getType() { return type; }
    public void setType(DragonType type) { this.type = type; }

    @XmlElement
    public Person getKiller() { return killer; }
    public void setKiller(Person killer) { this.killer = killer; }
}

