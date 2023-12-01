
package DAO;

import Entities.TurnoMedico;
import Persistence.TurnoMedicoJpaController;
import Persistence.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TurnoMedicoRepository implements TurnoMedicoDAO  {
        public TurnoMedicoJpaController controllerTurnoMedicoJpa;

    
    public TurnoMedicoRepository() {
        this.controllerTurnoMedicoJpa = new TurnoMedicoJpaController();
    }


        @Override
    public void insert(TurnoMedico turnoMedico) {
        controllerTurnoMedicoJpa.create(turnoMedico);
    }

        @Override
    public void update(TurnoMedico turnoMedico) {
        try {
            controllerTurnoMedicoJpa.edit(turnoMedico);
        } catch (Exception e) {
        }
    }
    
        @Override
    public void delete(int id) {
            try {
                controllerTurnoMedicoJpa.destroy(id);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(TurnoMedicoRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    }
    
    
        @Override
    public TurnoMedico selectById(int id) {
        return controllerTurnoMedicoJpa.findTurnoMedico( id);
    }
   
        @Override
    public List<TurnoMedico> selectAll() {
        return controllerTurnoMedicoJpa.findTurnoMedicoEntities();
    }
}