package DAO;

import Entities.Gato;
import Persistence.GatoJpaController;
import Persistence.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GatoRepository implements GatoDAO  {
        public GatoJpaController controllerGatoJpa;

    
    public GatoRepository() {
        this.controllerGatoJpa = new GatoJpaController();
    }


        @Override
    public void insert(Gato gato) {
        controllerGatoJpa.create(gato);
    }

        @Override
    public void update(Gato gato) {
        try {
            controllerGatoJpa.edit(gato);
        } catch (Exception e) {
        }
    }
    
        @Override
    public void delete(int id) {
            try {
                controllerGatoJpa.destroy(id);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(GatoRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    }
    
        @Override
    public Gato selectById(int id) {
        return controllerGatoJpa.findGato( id);
    }
   
        @Override
    public List<Gato> selectAll() {
        return controllerGatoJpa.findGatoEntities();
    }
   
    
}
