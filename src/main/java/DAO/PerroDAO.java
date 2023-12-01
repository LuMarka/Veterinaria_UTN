
package DAO;

import Entities.Perro;
import java.util.List;

public interface PerroDAO {
    
   /* boolean necesitaPaseoDiario(int idPerro);
    boolean tieneMicroChip(int idPerro);
    List<Perro> obtenerPerrosPorRaza(String raza);*/
   
    void insert(Perro perro);
    void update(Perro perro);
    void delete(int id);
    Perro selectById(int id);
    List<Perro> selectAll();
    
}
