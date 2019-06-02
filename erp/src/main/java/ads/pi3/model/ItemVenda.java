/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads.pi3.model;

/**
 *
 * @author dbrito
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemVenda {

    //Atributos
    private Produto produto;
    private Integer quantidade;
    private Double preco;
    
    //Métodos de acesso
    public Produto getProduto() {
        return produto;
    }
    
    public void setProduto(Produto prd) {
        this.produto = prd;
        if (quantidade != null) {
            setPreco();
        }                
    }

    public Integer getQuantidade() {                
        return quantidade;
    }
    
    public void setQuantidade(Integer qtd) {
        this.quantidade = qtd;
        if (produto != null) {
            setPreco();
        }                
    }
    
    public Double getPreco() {
        return this.preco;
    }
    
    public void setPreco() {
        this.preco = produto.getPreco() * this.quantidade;
    }          
    
    public void setPreco(Double preco) {
        this.preco = preco;
    }          
}