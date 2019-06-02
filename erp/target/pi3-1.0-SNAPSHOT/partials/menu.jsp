<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="nav-side-menu">
    <div class="brand"><img class="profile-img" src="https://cdn.vectorstock.com/i/thumb-large/80/48/perfume-boutique-logo-vintage-style-vector-18088048.jpg" alt=""></div>
    <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>
        <div class="menu-list">
            <div class="hello" style="text-align: center; font-size: 25px; padding: 20px 0;">
                Olá <c:out value="${sessionScope.funcionario.getNome()}"/>
            </div>

            <ul id="menu-content" class="menu-content collapse out">
                <c:if test="${sessionScope.funcionario.getPerfil() == 'gerente'}">
                    <li  data-toggle="collapse" data-target="#loja" class="collapsed">
                        <a href="#">
                            <i class="fa fa-gift fa-lg"></i> Lojas<span class="arrow"></span>
                        </a>
                    </li>
                    <ul class="sub-menu collapse" id="loja">
                        <li><a href="./cadastrar-filial">Cadastrar Loja</a></li>
                        <li><a href="./filiais">Gerenciar Lojas</a></li>
                    </ul>
                </c:if>

                <c:if test="${sessionScope.funcionario.getPerfil() == 'gerente' || sessionScope.funcionario.getPerfil() == 'estoquista'}">
                    <li data-toggle="collapse" data-target="#produto" class="collapsed">
                        <a href="#"><i class="fa fa-shopping-cart fa-lg"></i> Produtos <span class="arrow"></span></a>
                    </li>
                    <ul class="sub-menu collapse" id="produto">
                        <li><a href="./cadastrar-produto">Cadastrar Produto</a></li>
                        <li><a href="./produtos">Gerenciar Produtos</a></li>
                    </ul>
                </c:if>

                <li data-toggle="collapse" data-target="#cliente" class="collapsed">
                    <a href="#"><i class="fa fa-users fa-lg"></i> Clientes <span class="arrow"></span></a>
                </li>
                <ul class="sub-menu collapse" id="cliente">
                    <li><a href="./cadastrar-cliente">Cadastrar Cliente</a></li>
                    <li><a href="./clientes">Gerenciar Clientes</a></li>
                </ul>

                <c:if test="${sessionScope.funcionario.getPerfil() == 'gerente'}">
                    <li data-toggle="collapse" data-target="#usuario" class="collapsed">
                        <a href="#"><i class="fa fa-user fa-lg"></i> Usuários <span class="arrow"></span></a>
                    </li>
                    <ul class="sub-menu collapse" id="usuario">
                        <li><a href="./cadastrar-usuario">Cadastrar Usuário</a></li>
                        <li><a href="./usuarios">Gerenciar Usuários</a></li>
                    </ul>
                </c:if>


                <li data-toggle="collapse" data-target="#venda" class="collapsed">
                    <a href="#"><i class="fa fa-shopping-cart fa-lg"></i> Vendas <span class="arrow"></span></a>
                </li>
                <ul class="sub-menu collapse" id="venda">
                    <li><a href="./realizar-venda">Realizar Venda</a></li>
                    <c:if test="${sessionScope.funcionario.getPerfil() == 'gerente'}">
                        <li><a href="./aprovar-venda">Aprovar Vendas</a></li>    
                        <li><a href="./relatorios">Relátorios de Vendas</a></li>
                    </c:if>
                </ul>


                <li>
                    <a href="./gerenciar-conta"><i class="fa fa-edit fa-lg"></i> Gerenciar Conta</span></a>
                </li>
                <li>
                    <a href="./logout"><i class="fa fa-times fa-lg"></i> Sair</span></a>
                </li>
            </ul>
     </div>
</div>
