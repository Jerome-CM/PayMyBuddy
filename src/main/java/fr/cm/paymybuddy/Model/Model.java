package fr.cm.paymybuddy.Model;


import javax.persistence.Column;
import java.util.Date;


public abstract class Model {

    @Column(name="date_creation")
    private Date dateCreation;
    @Column(name="date_modification")
    private Date dateModification;

}
