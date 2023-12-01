
package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table( name = "Cliente")
public class Cliente implements Serializable {
    
     @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int idCliente;
     
     @Column( name  = "nombre")
    private String nombreCompleto;
     
     @Column( name  = "direccion")
    private String direccion;
     
     @Column( name  = "telefono")
    private String telefono;

    @OneToMany(mappedBy = "idCliente")
    private List<Mascota> mascotas = new ArrayList<>();

    public Cliente(int idCliente, String nombreCompleto, String direccion, String telefono) {
        this.idCliente = idCliente;
        this.nombreCompleto = nombreCompleto;
        this.direccion = direccion;
        this.telefono = telefono;
    }

 
}

