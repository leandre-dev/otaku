package fr.projet.jee.Objets;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Brand implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    
    /*
    @OneToMany(mappedBy = "Brand_id", cascade = CascadeType.ALL)
    private Set<Manga> employees = new HashSet<Manga>(0);
    */
    public Brand() {}

    public Brand(String nom) {
        this.name = nom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
    public Set<Manga> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Manga> employees) {
        this.employees = employees;
    }
*/
}