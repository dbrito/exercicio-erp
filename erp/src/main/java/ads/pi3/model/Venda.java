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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Venda {

    //Atributos
    private List<ItemVenda> listaItens = new ArrayList<ItemVenda>();
    private Usuario vendedor;
    private Filial filial;
    private Cliente cliente;
    private Date data;
    private Integer id;
    private Integer aprovado = 0;
    
    //Métodos de acesso
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getAprovado() {
        return aprovado;
    }

    public void setAprovado(Integer aprovado) {
        this.aprovado = aprovado;
    }
    
    public Date getData() {
        return data;
    }
    
    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");        
        return sdf.format(data);        
    }    

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }
    
    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }
    
    public List<ItemVenda> getItens() {
        return listaItens;
    }

    public void addItem(ItemVenda item) {
        listaItens.add(item);
    }
    
    public Double getTotal() {
        double total=0;        
        for (ItemVenda i : listaItens) {
            total += i.getPreco() * i.getQuantidade();
        }        
        return total;
    }
    
    public String getTotalFormatado() {
        double total=0;        
        for (ItemVenda i : listaItens) {
            total += i.getPreco() * i.getQuantidade();
        }
        Locale ptBr = new Locale("pt", "BR");        
        return NumberFormat.getCurrencyInstance(ptBr).format(total);
    }
          
}