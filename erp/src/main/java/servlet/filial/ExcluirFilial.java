package servlet.filial;

import ads.pi3.DAO.FilialDAO;
import ads.pi3.model.Usuario;
import ads.pi3.utils.Utils;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet(name="ExcluirFilial", urlPatterns = {"/excluir-filial"})

public class ExcluirFilial extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario user = Utils.getCurrentUser(request);
        if (user ==  null || !user.getPerfil().equals("gerente")) {
            response.sendError(403, "Acesso negado");
            return;
        }
        
        request.getParameterMap();
        try {
            FilialDAO.excluir(Integer.parseInt(request.getParameter("id")));
        } catch (Exception ex) {
            response.sendError(404, ex.getMessage());
        }
        PrintWriter resposta = response.getWriter();
        resposta.println("O produto foi excluido com sucesso.");
    }
}







