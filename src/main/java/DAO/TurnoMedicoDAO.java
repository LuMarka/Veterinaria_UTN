
package DAO;

import Entities.TurnoMedico;
import java.util.List;


public interface TurnoMedicoDAO {
    void insert(TurnoMedico turnoMedico);
    void update(TurnoMedico turnoMedico);
    void delete(int id);
    TurnoMedico selectById(int id);
    List<TurnoMedico> selectAll();
}
