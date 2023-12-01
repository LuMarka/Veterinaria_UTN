package Entities;

import Entities.Mascota;
import Entities.MedicoVeterinario;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-11-30T23:02:03", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(TurnoMedico.class)
public class TurnoMedico_ { 

    public static volatile SingularAttribute<TurnoMedico, Date> fecha;
    public static volatile SingularAttribute<TurnoMedico, String> motivoConsulta;
    public static volatile SingularAttribute<TurnoMedico, Boolean> aprobado;
    public static volatile SingularAttribute<TurnoMedico, Mascota> mascota;
    public static volatile SingularAttribute<TurnoMedico, Integer> id;
    public static volatile SingularAttribute<TurnoMedico, MedicoVeterinario> medicoAsignado;

}