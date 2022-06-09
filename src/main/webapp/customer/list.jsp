<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>List of customers</title>

    <%@ include file="/layout/head.jsp" %>
</head>

<body>
<div class="container">
    <div class="table-title">
        <div class="row">
            <div class="col-sm-5">
                <h1>List of customers</h1>
            </div>
            <div class="col-sm-7">
                <a href="/customers?action=create">
                    <button type="button" class="btn btn-outline-light">
                        <i class="fa-solid fa-plus"></i>
                        Add New Customer
                    </button>
                </a>
                <a href="/transfer-histories" class="btn btn-outline-light">
                    <i class="fa fa-history" aria-hidden="true"></i>
                    Transfer history
                </a>
            </div>
        </div>
    </div>
    <div>
        <table class="table table-hover table-borderless">
            <thead>
            <tr>
                <th>#</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>phone</th>
                <th>Address</th>
                <th>Balance</th>
                <th colspan="5" class="text-center">Action</th>
            </tr>
            </thead>
            <tbody id="tbCustomer">
                <c:forEach items='${requestScope["customers"]}' var="item">
                    <tr>
                        <td>${item.getId()}</td>
                        <td>${item.getFullName()}</td>
                        <td>${item.getEmail()}</td>
                        <td class="text-center">${item.getPhone()}</td>
                        <td>${item.getAddress()}</td>
                        <td class="text-end">
                            <fmt:formatNumber type="currency" pattern="#,##0" value="${item.getBalance()}" />
                        </td>
                        <td>
                            <a href="/customers?action=edit&id=${item.getId()}" data-toggle="tooltip" data-placement="top" title="Edit">
                                <button class="btn btn-outline-secondary">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                </button>
                            </a>
                        </td>
                        <td>
                            <a href="/transactions?action=deposit&id=${item.getId()}" data-toggle="tooltip" data-placement="top" title="Deposit">
                                <button class="btn btn-outline-success">
                                    <i class="fa fa-plus"></i>
                                </button>
                            </a>
                        </td>
                        <td>
                            <a href="/transactions?action=withdraw&id=${item.getId()}" data-toggle="tooltip" data-placement="top" title="Withdraw">
                                <button class="btn btn-outline-warning">
                                    <i class="fa fa-minus"></i>
                                </button>
                            </a>
                        </td>
                        <td>
                            <a href="/transactions?action=transfer&id=${item.getId()}" data-toggle="tooltip" data-placement="top" title="Transfer">
                                <button class="btn btn-outline-primary">
                                    <i class="fa fa-exchange"></i>
                                </button>
                            </a>
                        </td>
                        <td>
                            <a href="/customers?action=delete&id=${item.getId()}" data-toggle="tooltip" data-placement="top" title="Delete">
                                <button class="btn btn-outline-danger">
                                    <i class="fa fa-trash-alt fa-fw"></i>
                                </button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/layout/script.jsp" %>

<script>
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })
</script>

</body>

</html>