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
import java.util.List;
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
    
@WebServlet(name = "CadastrarCliente", urlPatterns = {"/cadastrar-cliente"})
public class CadastrarCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {         
        if (Utils.getCurrentUser(request) == null) {
            response.sendRedirect("login");
            return;
        }
        
        RequestDispatcher meuk = request.getRequestDispatcher("./cliente/cadastrar-cliente.jsp");
        meuk.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                 
        if (Utils.getCurrentUser(request) == null) {
            response.sendError(403, "Acesso negado");
            return;
        } 
        
        Cliente novoCliente = new Cliente();
        try {        
            novoCliente.setNome(request.getParameter("nome"));        
            novoCliente.setCpf(request.getParameter("cpf"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNascimento = sdf.parse(request.getParameter("data_nascimento"));
            novoCliente.setData_nascimento(dataNascimento);
            novoCliente.setTelefone(request.getParameter("telefone"));
            novoCliente.setEmail(request.getParameter("email"));                
            ClienteDAO.inserir(novoCliente);

            PrintWriter resposta = response.getWriter();
            resposta.println("O cliente '" + novoCliente.getNome() + "' foi cadastrado com sucesso.");
        } catch (Exception ex) {
            response.sendError(404, "Erro ao cadastrar o cliente.");
            Logger.getLogger(CadastrarCliente.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }        
    }

}

