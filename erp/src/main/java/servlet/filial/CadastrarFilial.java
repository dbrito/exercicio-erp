package servlet.filial;

import ads.pi3.DAO.FilialDAO;
import ads.pi3.model.Filial;
import ads.pi3.model.Usuario;
import ads.pi3.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CadastrarFilial", urlPatterns = {"/cadastrar-filial"} )

public class CadastrarFilial extends HttpServlet {

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
        RequestDispatcher pegar = request.getRequestDispatcher("./filial/cadastrar-filial.jsp");
        pegar.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario user = Utils.getCurrentUser(request);
        if (user ==  null || !user.getPerfil().equals("gerente")) {
            response.sendError(403, "Acesso negado");
            return;
        }
        
        Filial filial = new Filial();
        filial.setNome(request.getParameter("nome"));
        filial.setEndereco(request.getParameter("endereco"));
        filial.setNumero(Integer.parseInt(request.getParameter("numero")));
        filial.setCidade(request.getParameter("cidade"));
        filial.setEstado(request.getParameter("estado"));

        FilialDAO.inserir(filial);

        PrintWriter resposta = response.getWriter();
        resposta.println("A filial '" + filial.getNome() + "' foi cadastrado com sucesso.");


    }




}
