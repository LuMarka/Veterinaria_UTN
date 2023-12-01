package Entities;

import Entities.TurnoMedico;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-11-30T23:02:03", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(MedicoVeterinario.class)
public class MedicoVeterinario_ { 

    public static volatile ListAttribute<MedicoVeterinario, TurnoMedico> turnosAsignados;
    public static volatile SingularAttribute<MedicoVeterinario, Integer> idMedico;
    public static volatile SingularAttribute<MedicoVeterinario, String> telefono;
    public static volatile SingularAttribute<MedicoVeterinario, String> nombre;
    public static volatile SingularAttribute<MedicoVeterinario, String> especialidad;

}