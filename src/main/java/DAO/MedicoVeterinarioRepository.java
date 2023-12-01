
package DAO;

import Entities.MedicoVeterinario;
import Persistence.MedicoVeterinarioJpaController;
import Persistence.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MedicoVeterinarioRepository implements MedicoVeterinarioDAO {
    
    public MedicoVeterinarioJpaController controllerMedicoVeterinarioJpa;
    
    public MedicoVeterinarioRepository() {
        this.controllerMedicoVeterinarioJpa = new MedicoVeterinarioJpaController();
    }
    
    @Override
    public MedicoVeterinario selectById(int id) {
        return controllerMedicoVeterinarioJpa.findMedicoVeterinario(id);
    }

    @Override
    public List<MedicoVeterinario> selectAll() {
        return controllerMedicoVeterinarioJpa.findMedicoVeterinarioEntities();
    }

    @Override
    public void insert(MedicoVeterinario medico) {
        controllerMedicoVeterinarioJpa.create(medico);
    }

    @Override
    public void update(MedicoVeterinario medico) {
        try {
            controllerMedicoVeterinarioJpa.edit(medico);
        } catch (Exception e) {
        }
        
    }

    @Override
    public void delete(int id) {
        try {
            controllerMedicoVeterinarioJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MedicoVeterinarioRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
