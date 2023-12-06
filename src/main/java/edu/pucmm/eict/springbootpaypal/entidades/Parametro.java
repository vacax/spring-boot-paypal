package edu.pucmm.eict.springbootpaypal.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parametro implements Serializable {
    @Id
    Integer codigo;
    String nombre;
    String valor;
}
