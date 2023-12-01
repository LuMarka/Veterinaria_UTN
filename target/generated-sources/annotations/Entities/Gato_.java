package Entities;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-11-30T23:02:03", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Gato.class)
public class Gato_ extends Mascota_ {

    public static volatile SingularAttribute<Gato, String> patronPelaje;
    public static volatile SingularAttribute<Gato, Integer> cantidadHorasDescanso;
    public static volatile SingularAttribute<Gato, Boolean> esCazador;
    public static volatile SingularAttribute<Gato, Boolean> necesitaCajaArena;
    public static volatile SingularAttribute<Gato, Boolean> juegaConOtrosGatos;

}