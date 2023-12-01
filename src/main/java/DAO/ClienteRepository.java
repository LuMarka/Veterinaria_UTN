
package DAO;


import Entities.Cliente;
import Persistence.ClienteJpaController;
import Persistence.exceptions.NonexistentEntityException;
import java.util.List;




public class ClienteRepository implements ClienteDAO{
    
    public ClienteJpaController controllerClienteJpa;

    
    public ClienteRepository() {
        this.controllerClienteJpa = new ClienteJpaController();
    }
    
    @Override
    public Cliente selectById(int id) {
        return controllerClienteJpa.findCliente( id);
    }

    @Override
    public List<Cliente> selectAll() {
        return controllerClienteJpa.findClienteEntities();
    }

    @Override
    public void insert(Cliente cliente) {
        controllerClienteJpa.create(cliente);
    }


    @Override
    public void update(Cliente cliente) {
        try {
            controllerClienteJpa.edit(cliente);
        } catch (Exception e) {
        }
        
    }
    
        @Override
    public void delete(int id) {
        try {
            controllerClienteJpa.destroy(id);
            } catch (NonexistentEntityException e) {
        }
    }


}