package fr.projet.jee.Objets;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Whiskey implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(columnDefinition="integer", nullable = false, name="brand_id")
    private Brand brand;
    @Column(nullable = false)
    private String age;
    @Column
    private String bgImg;
    
    public Whiskey() {}

    public Whiskey(String name, String age, String bgImg) {
        this.name = name;
        this.age = age;
        this.bgImg = bgImg;
    }
    
    public Long getId() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    @Override
    public String toString() {
        return name + " distillé par " + brand.getName() + " à " + age + " an(s)";
    }
}