package DAO;

import Entities.Mascota;
import java.util.List;


public interface MascotaDAO {
    void insert(Mascota mascota);
    void update(Mascota mascota);
    void delete(int id);
    Mascota selectById(int id);
    List<Mascota> selectAll();

}
    /*DAO maneja la interacción con la capa de persistencia para realizar operaciones de 
    almacenamiento y recuperación en los objetos de la aplicación.Acá se definen  las operaciones 
    básicas de almacenamiento y recuperación para las entidades.*/