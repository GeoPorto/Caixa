package br.edu.ifsp.dominio.controller;

import br.edu.ifsp.model.Cliente;
import br.edu.ifsp.servicos.ClienteServicos;
import br.edu.ifsp.utility.Mensagens;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import javax.inject.Named;

@Named("clienteBean")
@SessionScoped
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Cliente cliente;

    @Inject
    private ClienteServicos servicos;

    private List<Cliente> listaClientes;

    private boolean editar = false;

    public void carregar() {
        listaClientes = servicos.listarTodos();

    }

    public void setFalse() {
        editar = false;
        listaClientes = servicos.listarTodos();
    }

    public void adicionar() {

        try {
            servicos.salvar(cliente);
            cliente = new Cliente();
            carregar();
            Mensagens.info("Cliente adicionado");
        } catch (Exception e) {
            Mensagens.info(e.getMessage());
        }

    }

    public void excluir(Cliente clienteexcluir) {
        try {
            servicos.remover(clienteexcluir);
            carregar();
            Mensagens.info(clienteexcluir.getNome() + " foi removido!");

        } catch (Exception e) {
            Mensagens.info(e.getMessage());
        }
    }

    public void salvar() {
        try {
            servicos.salvar(cliente);
            carregar();
            Mensagens.info(cliente.getNome() + " foi editado");
        } catch (Exception e) {
            e.getMessage();

        }
        editar = false;
    }

    public String isEditar(Cliente clienteSelecionado) {

        cliente = clienteSelecionado;

        editar = true;
        Mensagens.info(cliente.getNome() + " selecionado para edição"
        );

        return "clienteCadastro.xhtml?faces-redirect=true";

    }

    public String isAdicionar() {

        cliente = new Cliente();

        editar = false;

        return "clienteCadastro.xhtml?faces-redirect=true";

    }

    public String cadastrarOuAlterar() {

        if (editar) {
            salvar();

        } else {
            adicionar();

        }
        cliente = new Cliente();
        return "clienteListar.xhtml?faces-redirect=true";

    }

    public String voltar() {

        editar = false;
        cliente = new Cliente();

        return "clienteListar.xhtml?faces-redirect=true";
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaClientes() {
        carregar();
        return listaClientes;
    }

    public boolean getEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

}
