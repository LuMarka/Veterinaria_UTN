
package DAO;

import Entities.MedicoVeterinario;
import java.util.List;


public interface MedicoVeterinarioDAO {
    void insert(MedicoVeterinario medico);
    void update(MedicoVeterinario medico);
    void delete(int id);
    MedicoVeterinario selectById(int id);
    List<MedicoVeterinario> selectAll();
}
