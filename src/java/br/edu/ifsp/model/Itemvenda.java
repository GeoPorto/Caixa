package br.edu.ifsp.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "itemvenda")
@NamedQueries({
    @NamedQuery(name = "Itemvenda.findAll", query = "SELECT i FROM Itemvenda i"),
    @NamedQuery(name = "Itemvenda.findByQtde", query = "SELECT i FROM Itemvenda i WHERE i.qtde = :qtde"),
    @NamedQuery(name = "Itemvenda.findByPreco", query = "SELECT i FROM Itemvenda i WHERE i.preco = :preco")})
public class Itemvenda implements Serializable, Base {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "qtde")
    private Integer qtde;
    @Column(name = "preco")
    private Double preco;

    @JoinColumn(name = "idproduto", referencedColumnName = "idProduto")
    @ManyToOne(optional = false)
    private Produto produto;

    @JoinColumn(name = "idvenda", referencedColumnName = "idVenda")
    @ManyToOne(optional = false)
    private Venda venda;

    public Itemvenda() {
    }

    public Integer getQtde() {
        return qtde;
    }

    public void setIdItemvenda(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {

        return this.id;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

}
