package br.edu.ifsp.servicos;

import br.edu.ifsp.dao.DAO;
import br.edu.ifsp.model.Produto;
import br.edu.ifsp.utility.VendaException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ProdutoServicos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DAO<Produto> dao;

    public void salvar(Produto c) throws VendaException {
        System.out.println("passando pelo salvar");
        dao.salvar(c);
    }

    public void remover(Produto c) throws VendaException {
        dao.remover(Produto.class, c.getId());
    }

    public List<Produto> listarTodos() {
        return dao.buscarTodos("SELECT p FROM Produto p order by p.nome");
    }
}
