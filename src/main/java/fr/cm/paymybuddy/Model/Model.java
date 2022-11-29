package fr.cm.paymybuddy.Model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class Model {

    @Column(name="date_creation", nullable = false, updatable = false)
    private Date dateCreation = new Date();
    
    @Column(name="date_modification")
    private Date dateModification = new Date();

}
