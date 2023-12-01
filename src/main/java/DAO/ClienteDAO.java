
package DAO;

import Entities.Cliente;
import java.util.List;

public interface ClienteDAO {
    void insert(Cliente cliente);
    void update(Cliente cliente);
    void delete(int id);
    Cliente selectById(int id);
    List<Cliente> selectAll();
}
