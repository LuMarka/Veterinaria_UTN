
package Entities;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Mascota")
public class Mascota implements Serializable {
       
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "IdMascota")
    private int idMascota;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente idCliente;
    
    @Column(name  = "nombre")
    private String nombre;
    
    @Column(name  = "sexo")
    private String sexo;
    
    @Column(name  = "especie")
    private String especie;
    
    @Column(name  = "raza")
    private String raza;
    
    @Column(name  = "colorPelo")
    private String colorPelo;
    
    @Column(name  = "historiaClinica")
    private String historiaClinica;
    
    @Column(name  = "temperamento")
    private String temperamento;
    
    @Column(name  = "estadoSalud")
    private String estadoSalud;
    
    @Column(name  = "peso")
    private double peso;
    
    @Column(name  = "edad")
    private double edad;

    
}
