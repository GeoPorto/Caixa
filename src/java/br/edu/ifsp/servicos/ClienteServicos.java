package br.edu.ifsp.servicos;

import br.edu.ifsp.dao.DAO;
import br.edu.ifsp.model.Cliente;
import br.edu.ifsp.utility.VendaException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ClienteServicos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DAO<Cliente> dao;

    public void salvar(Cliente c) throws VendaException {
        System.out.println("passando pelo salvar");
        dao.salvar(c);
    }

    public void remover(Cliente c) throws VendaException {
        dao.remover(Cliente.class, c.getId());
    }

    public List<Cliente> listarTodos() {
        return dao.buscarTodos("SELECT c FROM Cliente c order by c.nome");
    }
}
