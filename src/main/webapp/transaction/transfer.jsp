<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Transfer</title>

    <%@ include file="/layout/head.jsp" %>
</head>

<body>
    <div class="container">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-5">
                    <h1>Transfer</h1>
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
                <div class="form-group row">
                    <div class="mb-3 col-md-3">
                        <label class="col-sm-12 col-form-label">Sender ID</label>
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="senderId" value="${requestScope["sender"].id}" readonly>
                        </div>
                    </div>
                    <div class="mb-3 col-md-3">
                        <label class="col-sm-12 col-form-label">Sender Name</label>
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="senderName" value="${requestScope["sender"].fullName}" readonly>
                        </div>
                    </div>
                    <div class="mb-3 col-md-3">
                        <label class="col-sm-12 col-form-label">Email</label>
                        <div class="col-sm-12">
                            <input type="email" class="form-control" name="email" value="${requestScope["sender"].email}" readonly>
                        </div>
                    </div>
                    <div class="mb-3 col-md-3">
                        <label class="col-sm-12 col-form-label">Sender balance</label>
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="balance" value="${requestScope["sender"].balance}" readonly>
                        </div>
                    </div>
                </div>
                <div class="form-group row mb-4">
                    <div class="mb-3 col-md-3">
                        <label for="recipient_id" class="col-sm-12 col-form-label">Recipient Name</label>
                        <div class="col-sm-12">
                            <select id="recipient_id" class="form-select" name="recipientId">
                                <c:forEach items='${requestScope["recipients"]}' var="item">
                                    <option value="${item.getId()}">(${item.getId()}) ${item.getFullName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="mb-3 col-md-3">
                        <label class="col-sm-12 col-form-label">Transfer Amount ($)</label>
                        <div class="col-sm-12">
                            <input type="text" class="form-control num-space" name="transferAmount" id="transferAmount">
                        </div>
                    </div>
                    <div class="mb-3 col-md-3">
                        <label class="col-sm-12 col-form-label">Fees (%)</label>
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="fees" value="10" readonly>
                        </div>
                    </div>
                    <div class="mb-3 col-md-3">
                        <label class="col-sm-12 col-form-label">Total amount of transaction ($)</label>
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="transactionAmount" id="transactionAmount" readonly>
                        </div>
                    </div>
                </div>

                <button type="submit" class="btn btn-outline-primary">
                    <i class="fa-solid fa-exchange"></i>
                    Transfer
                </button>
            </form>
        </div>
    </div>

    <%@ include file="/layout/footer.jsp" %>

    <%@ include file="/layout/script.jsp" %>

    <script>
        let transferAmount = document.getElementById("transferAmount");
        let transactionAmount = document.getElementById("transactionAmount");
        let fees = 0.1;

        transferAmount.addEventListener("input", function () {
            let feesAmount = transferAmount.value * fees;
            transactionAmount.value = Math.round(feesAmount + +transferAmount.value);
        })
    </script>

</body>

</html>