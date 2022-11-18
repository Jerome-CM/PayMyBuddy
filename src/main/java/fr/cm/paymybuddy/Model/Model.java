package fr.cm.paymybuddy.Model;


import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public abstract class Model {

    @Column(name="date_creation")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateCreation = new Date();
    
    @Column(name="date_modification")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateModification = new Date();

}
