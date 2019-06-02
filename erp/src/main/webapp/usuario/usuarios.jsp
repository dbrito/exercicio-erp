<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gerenciamento de Usuários</title>

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
            <h1><i class="fa fa-edit fa-lg"></i> Gerenciar Usuários</h1>
            <table class="table table-hover" >
                <thead >
                    <tr>
                        <th scope="col">Nome</th>
                        <th scope="col">Usuário</th>
                        <th scope="col">Perfil</th>
                        <th scope="col">CPF</th>
                        <th scope="col">Filial</th>
                        <th scope="col">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${usuarios}">
                        <tr>
                            <td><c:out value="${user.getNome()}" /></td>
                            <td><c:out value="${user.getUser()}" /></td>
                            <td><c:out value="${user.getPerfil()}" /></td>
                            <td><c:out value="${user.getCpf()}" /></td>
                            <td><c:out value="${user.getFilial().getNome()}" /></td>
                            <td>
                                <a href="./editar-usuario?id=<c:out value="${user.getId()}" />" title="Editar"><i class="fa fa-edit fa-lg"></i></a>
                                <a class="remove-item"href="#" title="Excluir" dt-id="<c:out value="${user.getId()}" />"><i class="fa fa-trash fa-lg"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>                
            </table>
        </div>
        <script>
            $('.remove-item').click(function (e) {
                e.preventDefault();
                var user = e.currentTarget;
                var nomeUsuario = user.parentNode.parentNode.children[0].innerText;
                var excluir = confirm('Deseja excluir o usuário "'+nomeUsuario+'" ?');
                if(!excluir) return;

                window.currentUser = user;
                $.ajax({
                    type: "POST",
                    url: './excluir-usuario',
                    data: {id : user.getAttribute('dt-id')},
                    success: function (result, status) {
                        //alert(result);
                        $(window.currentUser.parentNode.parentNode).hide();
                    }, error: function (err) {
                        alert('Erro tente novamente mais tarde;')
                    }
                });

            });
        </script>
    </body>
</html>
