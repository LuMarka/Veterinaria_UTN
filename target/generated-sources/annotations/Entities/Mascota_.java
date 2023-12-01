package Entities;

import Entities.Cliente;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-11-30T23:02:03", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Mascota.class)
public class Mascota_ { 

    public static volatile SingularAttribute<Mascota, String> especie;
    public static volatile SingularAttribute<Mascota, String> temperamento;
    public static volatile SingularAttribute<Mascota, String> estadoSalud;
    public static volatile SingularAttribute<Mascota, String> raza;
    public static volatile SingularAttribute<Mascota, String> historiaClinica;
    public static volatile SingularAttribute<Mascota, Cliente> idCliente;
    public static volatile SingularAttribute<Mascota, Double> peso;
    public static volatile SingularAttribute<Mascota, String> colorPelo;
    public static volatile SingularAttribute<Mascota, String> sexo;
    public static volatile SingularAttribute<Mascota, Integer> idMascota;
    public static volatile SingularAttribute<Mascota, String> nombre;
    public static volatile SingularAttribute<Mascota, Double> edad;

}