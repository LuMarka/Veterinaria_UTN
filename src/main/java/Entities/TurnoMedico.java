
package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Turno")
public class TurnoMedico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Temporal(TemporalType.DATE)
    @Column( name ="Fecha")
    private Date fecha;
    
    @Column( name ="aprobado")
    private boolean aprobado;
     
    @Column( name ="motivoConsulta")
    private String motivoConsulta;
    
    
  @ManyToOne
    private MedicoVeterinario medicoAsignado;

    @ManyToOne
    private Mascota mascota;

    public void setAceptado(boolean aceptado) {
        this.aprobado = aceptado;
    }

    public String getNombreMascota() {
        return mascota != null ? mascota.getNombre() : "Mascota no especificada";
    }

    
}
