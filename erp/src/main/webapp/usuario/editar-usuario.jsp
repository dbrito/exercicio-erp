<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Usuário</title>

        <style>
            <%@include file="../css/geral.css" %>
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" >

        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <%@include file="../partials/menu.jsp" %>

        <div class="content">
            <h1><i class="fa fa-edit fa-lg"></i> Editar Usuário</h1>
            <form action="/editar-usuario" method="post" id="formulario">
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="nome">Nome:</label>
                        <input type="text" class="form-control" name="nome" id="nome" value="<c:out value="${user.getNome()}" />" required>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="cpf">CPF:</label>
                        <input type="text" class="form-control" name="cpf" id="cpf" value="<c:out value="${user.getCpf()}" />" required>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="user">User:</label>
                        <input type="text" class="form-control" name="user" id="user" value="<c:out value="${user.getUser()}" />" required>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="pass">Senha:</label>
                        <input type="password" class="form-control" name="pass" id="pass" value="<c:out value="${user.getPass()}" />" required>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="perfil">Perfil:</label>
                        <select class="form-control" name="perfil" id="perfil">
                            <option value="caixa" <c:if test="${user.getPerfil() == 'caixa'}">selected</c:if>>Caixa</option>
                            <option value="estoquista" <c:if test="${user.getPerfil() == 'estoquista'}">selected</c:if>>Estoquista</option>
                            <option value="gerente" <c:if test="${user.getPerfil() == 'gerente'}">selected</c:if>>Gerente</option>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="filial">Filial:</label>
                        <select class="form-control" name="filial" id="filial">
                            <c:forEach var="fili" items="${filiais}">
                                <option
                                    <c:if test="${user.getFilial().getId() == fili.getId()}">selected</c:if>
                                    value="<c:out value="${fili.getId()}" />">
                                    <c:out value="${fili.getNome()}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <input type="submit" class="btn btn-info" value="Salvar">
                <a href="./usuarios" class="btn btn-info" role="button">Cancelar</a>
            </form>
            <script>
                $("#formulario").submit(function (e) {
                    e.preventDefault();
                    $.ajax({
                        type: "POST",
                        url: window.location.href,
                        data: $("#formulario").serialize(),
                        success: function (result, status) {
                            alert(result);
                            window.location.href = './usuarios';
                        }, error: function (err) {
                            alert('Erro tente novamente mais tarde;')
                        }
                    });
                });
            </script>
        </div>

    </body>
</html>
