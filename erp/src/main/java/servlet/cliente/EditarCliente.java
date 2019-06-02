/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.cliente;

import ads.pi3.DAO.ClienteDAO;
import ads.pi3.model.Cliente;
import ads.pi3.model.Usuario;
import ads.pi3.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ninck
 */
@WebServlet(name = "EditarCliente", urlPatterns = {"/editar-cliente"})
public class EditarCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                 
        if (Utils.getCurrentUser(request) == null) {
            response.sendRedirect("login");
            return;
        }
        
        Cliente clie = null;
        try {        
            clie = ClienteDAO.obter(Integer.parseInt(request.getParameter("id")));
        } catch (Exception ex) {
            response.sendError(404, "Cliente não encontrado");
            Logger.getLogger(EditarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("clie", clie);
        RequestDispatcher meuk = request.getRequestDispatcher("./cliente/editar-cliente.jsp");
        meuk.forward(request, response);                                        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                 
        if (Utils.getCurrentUser(request) == null) {
            response.sendError(403, "Acesso negado");
            return;
        }        
        
        Cliente clie;
        try {        
            clie = ClienteDAO.obter(Integer.parseInt(request.getParameter("id")));
        } catch (Exception ex) {
            response.sendError(404, "Cliente não encontrado");
            Logger.getLogger(EditarCliente.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }                
                     
        try {
            clie.setNome(request.getParameter("nome"));        
            clie.setCpf(request.getParameter("cpf"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNascimento = sdf.parse(request.getParameter("data_nascimento"));
            clie.setData_nascimento(dataNascimento);
            clie.setTelefone(request.getParameter("telefone"));
            clie.setEmail(request.getParameter("email"));            
            ClienteDAO.atualizar(clie);
        } catch (Exception ex) {
            response.sendError(503, ex.toString());
            Logger.getLogger(EditarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PrintWriter resposta = response.getWriter();
        resposta.println("O cliente '" + clie.getNome() + "' foi atualizado com sucesso.");
    }

}
