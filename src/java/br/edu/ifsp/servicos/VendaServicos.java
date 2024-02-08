package br.edu.ifsp.servicos;

import br.edu.ifsp.dao.DAO;
import br.edu.ifsp.model.Venda;
import br.edu.ifsp.utility.Mensagens;
import br.edu.ifsp.utility.VendaException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class VendaServicos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DAO<Venda> dao;

    public void salvar(Venda c) throws VendaException {
        System.out.println("passando pelo salvar");
        try {
            dao.salvar(c);

        } catch (Exception e) {
            Mensagens.erro(e.getMessage());
        }
    }

    public void remover(Venda c) throws VendaException {
        dao.remover(Venda.class, c.getId());
    }

    public List<Venda> listarTodos() {
        return dao.buscarTodos("SELECT c FROM Venda c order by c.id");
    }
}
