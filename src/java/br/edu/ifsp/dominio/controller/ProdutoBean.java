package br.edu.ifsp.dominio.controller;

import br.edu.ifsp.model.Produto;
import br.edu.ifsp.servicos.ProdutoServicos;
import br.edu.ifsp.utility.Mensagens;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("produtoBean")
@SessionScoped
public class ProdutoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Produto produto;

    @Inject
    private ProdutoServicos servicos;

    private List<Produto> listaProdutos;

    private boolean editar = false;

    @PostConstruct
    public void carregar() {
        try {
            listaProdutos = servicos.listarTodos();
        } catch (Exception e) {
            listaProdutos = null;
        }
    }

    public void adicionar() {

        try {
            servicos.salvar(produto);
            produto = new Produto();
            carregar();
            Mensagens.info("Produto adicionado");
        } catch (Exception e) {
            Mensagens.erro(e.getMessage());
        }

    }

    public void setFalse() {
        editar = false;
        listaProdutos = servicos.listarTodos();
    }

    public void excluir(Produto produtoexcluir) {
        try {
            servicos.remover(produtoexcluir);
            carregar();
            Mensagens.info(produtoexcluir.getNome() + " foi removido!");

        } catch (Exception e) {
            Mensagens.erro(e.getMessage());
        }
    }

    public void salvar() {
        try {
            servicos.salvar(produto);
            carregar();
            Mensagens.info(produto.getNome() + " foi editado");
        } catch (Exception e) {
            Mensagens.erro(e.getMessage());
        }
        editar = false;
    }

    public String isEditar(Produto produtoSelecionado) {

        produto = produtoSelecionado;

        editar = true;

        Mensagens.info(produto.getNome() + " selecionado para edição");

        return "produtoCadastro.xhtml?faces-redirect=true";

    }

    public String isAdicionar() {

        produto = new Produto();

        editar = false;

        return "produtoCadastro.xhtml?faces-redirect=true";

    }

    public String cadastrarOuAlterar() {

        if (editar) {
            salvar();

        } else {
            adicionar();

        }
        produto = new Produto();
        return "produtoListar.xhtml?faces-redirect=true";

    }

    public String voltar() {

        editar = false;
        produto = new Produto();

        return "produtoListar.xhtml?faces-redirect=true";
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

}
