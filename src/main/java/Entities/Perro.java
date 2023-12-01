
package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "Perro")
public class Perro extends Mascota {
    
    @Column(name = "Paseo")
    private String necesitaPaseoDiario;
    @Column(name = "MicroChip")
    private String tieneMicroChip;
    
}
