/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.filial;

import ads.pi3.DAO.FilialDAO;
import ads.pi3.model.Filial;
import ads.pi3.model.Usuario;
import ads.pi3.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet(name="EditarFilial", urlPatterns ={"/editar-filial"})

public class EditarFilial extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario user = Utils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        } else if (!user.getPerfil().equals("gerente")){
            response.sendRedirect(request.getContextPath() + "/acesso-negado.html");
            return;
        }

        Filial filial = null;
        try {
            filial = FilialDAO.obter(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("filial", filial);
            RequestDispatcher pegar = request.getRequestDispatcher("./filial/editar-filial.jsp");
            pegar.forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(EditarFilial.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(404,"Filial não encontrada");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario user = Utils.getCurrentUser(request);
        if (user ==  null || !user.getPerfil().equals("gerente")) {
            response.sendError(403, "Acesso negado");
            return;
        }
        
        Filial filial = null;
        
        try {
            filial = FilialDAO.obter(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            response.sendError(404, "Filial nao encontrada.");
            return;
        }

        filial.setNome(request.getParameter("nome"));
        filial.setEndereco(request.getParameter("endereco"));
        filial.setNumero(Integer.parseInt(request.getParameter("numero")));
        filial.setCidade(request.getParameter("cidade"));
        filial.setEstado(request.getParameter("estado"));

        try {
            FilialDAO.atualizar(filial);
        } catch (Exception e) {
            response.sendError(503, e.getMessage());
            return;
        }

        PrintWriter resposta = response.getWriter();
        resposta.println("A filial '" + filial.getNome() + "' foi atualizada com sucesso.");
    }



}
