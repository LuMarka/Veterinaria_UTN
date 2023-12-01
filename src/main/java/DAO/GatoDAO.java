
package DAO;

import Entities.Gato;
import java.util.List;

public interface GatoDAO {
    /*boolean esCazador(int idGato);
    int cantidadHorasDescanso(int idGato);
    boolean necesitaCajaArena(int idGato);
    boolean juegaConOtrosGatos(int idGato);
    String patronPelaje(int idGato);
    List<Gato> obtenerGatosPorRaza(String raza);*/ 
    void insert(Gato gato);
    void update(Gato gato);
    void delete(int id);
    Gato selectById(int id);
    List<Gato> selectAll();
}
