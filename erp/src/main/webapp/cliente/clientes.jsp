<%-- 
    Document   : cliente
    Created on : 08/05/2018, 20:42:58
    Author     : marina.fmoreira
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gerenciamento de Clientes</title>

        <style>
            <%@include file="../css/geral.css" %>
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" >

        <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <%@include file="../partials/menu.jsp" %>

        <div class="content">
            <h1><i class="fa fa-edit fa-lg"></i> Gerenciar Clientes</h1>
            <table class="table table-hover" >
                <thead >
                    <tr>
                        <th scope="col">Nome</th>
                        <th scope="col">CPF</th>
                        <th scope="col">Data de Nascimento</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Telefone</th>
                        <th scope="col">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="clie" items="${clientes}">
                        <tr>
                            <td><c:out value="${clie.getNome()}" /></td>
                            <td><c:out value="${clie.getCpf()}" /></td>
                            <td><c:out value="${clie.getDataNascimentoFormatada()}" /></td>
                            <td><c:out value="${clie.getEmail()}" /></td>
                            <td><c:out value="${clie.getTelefone()}" /></td>                                                      
                            <td>
                                <a href="./editar-cliente?id=<c:out value="${clie.getId()}" />" title="Editar"><i class="fa fa-edit fa-lg"></i></a>
                                <a class="remove-item"href="#" title="Excluir" dt-id="<c:out value="${clie.getId()}" />"><i class="fa fa-trash fa-lg"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <script>
                    $('.remove-item').click(function (e) {
                        e.preventDefault();        
                        var clie = e.currentTarget;                        
                        var nomeCliente = clie.parentNode.parentNode.children[0].innerText;
                        var excluir = confirm('Deseja excluir o cliente "'+nomeCliente+'" ?');
                        if(!excluir) return;
                                
                        window.currentUser = clie;
                        $.ajax({
                            type: "POST",
                            url: './excluir-cliente',
                            data: {id : clie.getAttribute('dt-id')},
                            success: function (result, status) {
                                //alert(result);     
                                $(window.currentUser.parentNode.parentNode).hide();
                            }, error: function (err) {
                                alert('Erro tente novamente mais tarde;')
                            }
                        });
                            
                    });
                    
                </script>
            </table>
        </div>

    </body>
</html>
