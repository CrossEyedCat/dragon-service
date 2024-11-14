package org.example.dragonservice.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(name = "person")
public class Person {
    private String name;
    private LocalDate birthday;
    private Color hairColor;
    private Country nationality;
    private Location location;

    @XmlElement
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlElement
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    @XmlElement
    public Color getHairColor() { return hairColor; }
    public void setHairColor(Color hairColor) { this.hairColor = hairColor; }

    @XmlElement
    public Country getNationality() { return nationality; }
    public void setNationality(Country nationality) { this.nationality = nationality; }

    @XmlElement
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
}