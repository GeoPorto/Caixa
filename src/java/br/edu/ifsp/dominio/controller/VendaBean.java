package br.edu.ifsp.dominio.controller;

import br.edu.ifsp.model.Cliente;
import br.edu.ifsp.model.Itemvenda;
import br.edu.ifsp.model.Produto;
import br.edu.ifsp.model.Venda;
import br.edu.ifsp.servicos.ItemvendaServicos;
import br.edu.ifsp.servicos.ProdutoServicos;
import br.edu.ifsp.servicos.VendaServicos;
import br.edu.ifsp.utility.VendaException;
import br.edu.ifsp.utility.Mensagens;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import javax.inject.Named;

@Named("vendaBean")
@SessionScoped
public class VendaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Venda venda;

    @Inject
    private VendaServicos servicos;

    @Inject
    private ProdutoServicos servicosProduto;

    @Inject
    private ItemvendaServicos servicosIV;

    private List<Venda> listaVendas;

    private Cliente clienteSelecionado;

    private List<Itemvenda> itensVenda;

    private Double valorTotal;

    public void carregar() {
        listaVendas = servicos.listarTodos();

    }

    public void cadastrarVenda() {

        venda = new Venda();
        if (clienteSelecionado != null) {

            try {
                venda.setCliente(clienteSelecionado);
                venda.setData(new Date());
                venda.setValorTotal(valorTotal);

                servicos.salvar(venda);

                itensVenda.forEach(item -> {
                    item.setVenda(venda);
                    try {
                        servicosIV.salvar(item);
                    } catch (VendaException e) {
                        Mensagens.erro(e.getMessage());
                    }
                });

                venda.setItemvenda(itensVenda);
                venda.setCliente(clienteSelecionado);
                servicos.salvar(venda);
                venda = new Venda();
                itensVenda = null;

                carregar();
                Mensagens.info("Venda adicionada");

            } catch (Exception e) {
                Mensagens.erro(e.getMessage());
            }
        } else {
            Mensagens.info("Selecione o Cliente");
        }

        valorTotal = Double.valueOf(0);

    }

    public void selecionarCliente(Cliente cliente) {
        clienteSelecionado = cliente;
    }

    public void excluir(Venda vendaexcluir) {
        try {
            servicos.remover(vendaexcluir);
            carregar();
            Mensagens.info("Venda " + vendaexcluir.getId() + " foi removido!");

        } catch (Exception e) {
            Mensagens.erro(e.getMessage());
        }
    }

    public void remover(Itemvenda itemremover) {

        if (itemremover.getQtde() > 1) {
            itensVenda.forEach(item -> {
                if (item.getProduto().getId().equals(itemremover.getProduto().getId())) {
                    item.setQtde(item.getQtde() - 1);
                    item.getProduto().setQtde(item.getProduto().getQtde() + 1);
                    try {
                        servicosProduto.salvar(item.getProduto());
                    } catch (VendaException ex) {
                        Mensagens.aviso("erro");
                    }
                }
            });

            valorTotal = valorTotal - itemremover.getPreco();
        } else {
            valorTotal = valorTotal - itemremover.getPreco();
            itensVenda.remove(itemremover);

        }
    }

    public void salvar() {
        try {
            servicos.salvar(venda);
            carregar();
            Mensagens.info("Venda " + venda.getId() + " foi editado");
        } catch (Exception e) {
            Mensagens.erro(e.getMessage());
        }

    }

    public void selecionarProduto(Produto produto) throws VendaException {

        if (venda == null) {
            venda = new Venda();
        }

        if (valorTotal == null) {
            valorTotal = 0.0;
        }

        if (itensVenda == null) {
            itensVenda = new ArrayList<Itemvenda>();
        }

        boolean produtoJaExiste = false;

        for (Itemvenda item : itensVenda) {
            if (item.getProduto().getId().equals(produto.getId())) {
                // O produto já existe na lista de itens

                if (produto.getQtde() > 0) {
                    produto.setQtde(produto.getQtde() - 1);
                    valorTotal += produto.getPreco();
                    item.setQtde(item.getQtde() + 1);
                    item.setProduto(produto);
                    produtoJaExiste = true;
                } else {
                    Mensagens.erro("Erro: Produto fora de estoque.");
                }
                break; // item encontrado e fim do loop
            }
        }

        if (!produtoJaExiste) {

            if (produto.getQtde() > 0) {
                produto.setQtde(produto.getQtde() - 1);

                Itemvenda i = new Itemvenda();

                i.setPreco(produto.getPreco());
                i.setQtde(1);
                i.setProduto(produto);

                itensVenda.add(i);

                valorTotal += produto.getPreco();
            } else {
                Mensagens.erro("Erro: Produto fora de estoque.");
            }

        }

    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Venda> getListaVendas() {
        return listaVendas;
    }

    public void setListaVendas(List<Venda> listaVendas) {
        this.listaVendas = listaVendas;
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public List<Itemvenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<Itemvenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

}
