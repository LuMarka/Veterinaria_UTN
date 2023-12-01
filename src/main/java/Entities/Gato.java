
package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Gato")
public class Gato extends Mascota {
    
    @Column(name  = "esCazador")
    private boolean esCazador;
    
    @Column(name  = "Descanso")
    private int cantidadHorasDescanso;
    
    @Column(name  = "CajaArena")
    private boolean necesitaCajaArena;
    
    @Column(name  = "Juega")
    private boolean juegaConOtrosGatos;
    
    @Column(name  = "pelaje")
    private String patronPelaje;
}
