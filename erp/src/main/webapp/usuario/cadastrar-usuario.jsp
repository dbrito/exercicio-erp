<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastrar Usuário</title>

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
            <h1>
                <i class="fa fa-edit fa-lg"></i> Cadastrar Usuário
                <c:out value="${sessionScope.funcionario.getNome()}"/>
            </h1>
            <form action="/cadastrar-usuario" method="post" id="formulario">
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="nome">Nome:</label>
                        <input type="text" class="form-control" name="nome" id="nome" required>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="cpf">CPF:</label>
                        <input type="text" class="form-control" name="cpf" id="cpf" required>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="user">Usuário:</label>
                        <input type="text" class="form-control" name="user" id="user" required>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="pass">Senha:</label>
                        <input type="password" class="form-control" name="pass" id="pass" required>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="perfil">Perfil:</label>
                        <select class="form-control" name="perfil" id="perfil">
                            <option value="caixa">Caixa</option>
                            <option value="estoquista">Estoquista</option>
                            <option value="gerente">Gerente</option>
                        </select>
                    </div>

                    <div class="form-group col-md-3">
                        <label for="filial">Filial:</label>
                        <select class="form-control" name="filial" id="filial">
                            <c:forEach var="fili" items="${filiais}">
                                <option value="<c:out value="${fili.getId()}" />">
                                    <c:out value="${fili.getNome()}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <input type="submit" class="btn btn-info" value="Salvar">
                <!-- <a href="#" class="btn btn-info" role="button">Cancelar</a> -->
            </form>
            <script>
                $('#formulario').click(function () { console.log('here') })
                $("#formulario").submit(function (e) {
                    e.preventDefault();
                    $.ajax({
                        type: "POST",
                        url: window.location.href,
                        data: $("#formulario").serialize(),
                        success: function (result, status) {
                            alert(result);
                            if (status == 'success') {
                                $('#nome').val('');
                                $('#cpf').val('');
                                $('#user').val('');
                                $('#pass').val('');
                            }
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
