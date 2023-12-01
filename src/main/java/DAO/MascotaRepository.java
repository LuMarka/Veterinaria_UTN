/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entities.Mascota;
import Persistence.MascotaJpaController;
import Persistence.exceptions.NonexistentEntityException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MascotaRepository implements MascotaDAO{
    
    public MascotaJpaController controllerMascotaJpa;
    
    public MascotaRepository() {
        this.controllerMascotaJpa = new MascotaJpaController();
    }
    
    @Override
    public Mascota selectById(int id) {
        return controllerMascotaJpa.findMascota(id);
    }

    @Override
    public List<Mascota> selectAll() {
        return controllerMascotaJpa.findMascotaEntities();
    }

    @Override
    public void insert(Mascota mascota) {
        controllerMascotaJpa.create(mascota);
    }

    @Override
    public void update(Mascota mascota) {
        try {
            controllerMascotaJpa.edit(mascota);
        } catch (Exception e) {
        }
        
    }

    @Override
    public void delete(int id) {
        try {
            controllerMascotaJpa.destroy(id);
        } catch (NonexistentEntityException e) {
        }
    }
}