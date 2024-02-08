package br.edu.ifsp.servicos;

import br.edu.ifsp.dao.DAO;
import br.edu.ifsp.model.Itemvenda;
import br.edu.ifsp.utility.VendaException;
import java.io.Serializable;
import javax.inject.Inject;

public class ItemvendaServicos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DAO<Itemvenda> dao;

    public void salvar(Itemvenda c) throws VendaException {
        System.out.println("passando pelo salvar");
        dao.salvar(c);
    }

}
