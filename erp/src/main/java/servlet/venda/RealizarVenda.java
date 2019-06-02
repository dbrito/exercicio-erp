/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.venda;

import ads.pi3.DAO.ClienteDAO;
import ads.pi3.DAO.ProdutoDAO;
import ads.pi3.DAO.VendaDAO;
import ads.pi3.model.Cliente;
import ads.pi3.model.ItemVenda;
import ads.pi3.model.Produto;
import ads.pi3.model.Usuario;
import ads.pi3.model.Venda;
import ads.pi3.utils.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;

/**
 *
 * @author dbrito
 */
@WebServlet(name = "RealizarVenda", urlPatterns = {"/realizar-venda"})
public class RealizarVenda extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {         
        if (Utils.getCurrentUser(request) == null) {
            response.sendRedirect("login");
            return;
        }
        
        List<Cliente> clientes = ClienteDAO.listar();                
        request.setAttribute("clientes", clientes);        
        
        List<Produto> produtos = ProdutoDAO.listar();                
        request.setAttribute("produtos", produtos);        
        RequestDispatcher meuk = request.getRequestDispatcher("./venda/realizar-venda.jsp");
        meuk.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                                 
        Usuario user = Utils.getCurrentUser(request);
        if (user == null) {
            response.sendError(403, "Acesso negado");
            return;
        }        
        
        try {
            JSONObject json = new JSONObject(request.getParameter("jsonData"));
            Cliente cliente = ClienteDAO.obter((Integer) json.get("clienteId"));        
            
            Venda novaVenda = new Venda();
            novaVenda.setCliente(cliente);
            novaVenda.setData(new Date());
            novaVenda.setVendedor(user);
            novaVenda.setFilial(user.getFilial());
            
                        
            JSONArray produtos = (JSONArray) json.get("produtos");
            for(int n = 0; n < produtos.length(); n++) {                                
                JSONObject parametrosProduto = produtos.getJSONObject(n);
                
                Produto produto = ProdutoDAO.obter(Integer.parseInt((String) parametrosProduto.get("id")));
                ItemVenda item = new ItemVenda();
                item.setProduto(produto);
                item.setQuantidade((Integer) parametrosProduto.get("quantidade"));                                                
                novaVenda.addItem(item);
            }
            VendaDAO.criar(novaVenda);
            PrintWriter resposta = response.getWriter();
            resposta.println("A venda foi solicitada com sucesso! Agora é só esperar a aprovação do responsável.");
        } catch (Exception ex) {
            response.sendError(503, ex.getMessage());
            Logger.getLogger(RealizarVenda.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
    }

}
