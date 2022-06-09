<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Withdraw</title>

    <%@ include file="/layout/head.jsp" %>
</head>

<body>
<div class="container">
    <div class="table-title">
        <div class="row">
            <div class="col-sm-5">
                <h1>Withdraw</h1>
            </div>
            <div class="col-sm-7">
                <a href="/customers">
                    <button type="button" class="btn btn-outline-light">
                        <i class="fa-solid fa-bars"></i>
                        List of customers
                    </button>
                </a>
            </div>
        </div>
    </div>
    <div>
        <form method="post">
            <div class="row mt-3">
                <div class="col-md-6 mb-3">
                    <label for="customerId" class="form-label">Customer ID</label>
                    <input type="text" class="form-control" id="customerId" name="customerId" value="${requestScope["customer"].id}">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="fullName" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullName" value="${requestScope["customer"].fullName}">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="balance" class="form-label">Current balance ($)</label>
                    <input type="number" class="form-control" id="balance" name="balance" value="${requestScope["customer"].balance}" readonly>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="transactionAmount" class="form-label">Transaction Amount ($)</label>
                    <input type="number" class="form-control" id="transactionAmount" name="transactionAmount">
                </div>
            </div>

            <button type="submit" class="btn btn-outline-warning">
                <i class="fa-solid fa-minus"></i>
                Withdraw
            </button>
        </form>
    </div>
</div>

<%@ include file="/layout/footer.jsp" %>

<%@ include file="/layout/script.jsp" %>

</body>

</html>