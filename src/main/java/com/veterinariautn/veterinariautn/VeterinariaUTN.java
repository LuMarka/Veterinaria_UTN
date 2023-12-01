
package com.veterinariautn.veterinariautn;

import DAO.ClienteDAO;
import DAO.ClienteRepository;
import DAO.PerroDAO;
import DAO.PerroRepository;
import Entities.Cliente;
import Entities.Perro;




public class VeterinariaUTN {

    public static void main(String[] args) {
            Perro perro = new Perro ("si", "no") ; 
            PerroDAO pdao = new PerroRepository ();
            pdao.insert(perro);
            
            
            Cliente cliente = new Cliente(1, "Mario Perez", "Albertdi 5514, San Juan", "2634448512");
            ClienteDAO cdao = new ClienteRepository();
            cdao.insert(cliente);
            
    }
}
