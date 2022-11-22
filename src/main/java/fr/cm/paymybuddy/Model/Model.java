package fr.cm.paymybuddy.Model;


import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public abstract class Model {

    @Column(name="date_creation", nullable = false, updatable = false)
    private Date dateCreation = new Date();
    
    @Column(name="date_modification")
    private Date dateModification = new Date();

}
