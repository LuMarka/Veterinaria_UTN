
package DAO;

import Entities.Perro;
import Persistence.PerroJpaController;
import Persistence.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PerroRepository implements PerroDAO  {
        public PerroJpaController controllerPerroJpa;

    
    public PerroRepository() {
        this.controllerPerroJpa = new PerroJpaController();
    }


        @Override
    public void insert(Perro perro) {
        controllerPerroJpa.create(perro);
    }

        @Override
    public void update(Perro perro) {
        try {
            controllerPerroJpa.edit(perro);
        } catch (Exception e) {
        }
    }
    
        @Override
    public void delete(int id) {
            try {
                controllerPerroJpa.destroy(id);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(PerroRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    }
    
        @Override
    public Perro selectById(int id) {
        return controllerPerroJpa.findPerro( id);
    }
   
        @Override
    public List<Perro> selectAll() {
        return controllerPerroJpa.findPerroEntities();
    } 
}
