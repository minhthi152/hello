<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Transfer history</title>

    <%@ include file="/layout/head.jsp" %>
</head>

<body>
    <div class="container">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-5">
                    <h1>Transfer history</h1>
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
            <nav>
                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                    <button class="nav-link active" id="nav-date-tab" data-bs-toggle="tab" data-bs-target="#nav-date" type="button" role="tab" aria-controls="nav-date" aria-selected="true">Search by date</button>
                    <button class="nav-link" id="nav-month-tab" data-bs-toggle="tab" data-bs-target="#nav-month" type="button" role="tab" aria-controls="nav-month" aria-selected="false">Search by month</button>
                    <button class="nav-link" id="nav-date-range-tab" data-bs-toggle="tab" data-bs-target="#nav-date-range" type="button" role="tab" aria-controls="nav-date-range" aria-selected="false">Search by date range</button>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-date" role="tabpanel" aria-labelledby="nav-date-tab" tabindex="0">
                    <form method="get">
                        <div class="d-none">
                            <input type="text" name="action" value="search-by-date">
                        </div>
                        <div class="input-group mt-3 mb-3">
                            <label for="sDate" class="col-md-1 col-form-label">Date:</label>
                            <div class="col-md-2">
                                <input type="date" class="form-control" id="sDate" name="sDate" value="${sDate}">
                            </div>
                            <div class="col-md-3 ms-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa-solid fa-magnifying-glass"></i>
                                    Search
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="tab-pane fade" id="nav-month" role="tabpanel" aria-labelledby="nav-month-tab" tabindex="0">
                    <form method="get">
                        <div class="d-none">
                            <input type="text" name="action" value="search-by-month">
                        </div>
                        <div class="input-group mt-3 mb-3">
                            <label for="sMonth" class="col-md-1 col-form-label">Month:</label>
                            <div class="col-md-2">
                                <select class="form-control" id="sMonth" name="sMonth">
                                    <option value="1">Month 1</option>
                                </select>
                            </div>
                            <label for="sYear" class="col-md-1 col-form-label ms-2">Year:</label>
                            <div class="col-md-2">
                                <select class="form-control" id="sYear" name="sYear">
                                    <option value="2020">2020</option>
                                </select>
                            </div>
                            <div class="col-md-3 ms-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa-solid fa-magnifying-glass"></i>
                                    Search
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="tab-pane fade" id="nav-date-range" role="tabpanel" aria-labelledby="nav-date-range-tab" tabindex="0">
                    <form method="get">
                        <div class="d-none">
                            <input type="text" name="action" value="search-by-date-range">
                        </div>
                        <div class="input-group mt-3 mb-3">
                            <label for="sFromDate" class="col-md-1 col-form-label">From:</label>
                            <div class="col-md-2">
                                <input type="date" class="form-control" id="sFromDate" name="sFromDate" value="${sFromDate}">
                            </div>
                            <label for="sToDate" class="col-md-1 col-form-label ms-2">To:</label>
                            <div class="col-md-2">
                                <input type="date" class="form-control" id="sToDate" name="sToDate" value="${sToDate}">
                            </div>
                            <div class="col-md-3 ms-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa-solid fa-magnifying-glass"></i>
                                    Search
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div>
            <table class="table table-hover table-borderless">
                <thead>
                    <th class="text-center">#</th>
                    <th class="text-center">Sender ID</th>
                    <th class="text-center">Sender Name</th>
                    <th class="text-center">Recipient ID</th>
                    <th class="text-center">Recipient Name</th>
                    <th class="text-center">Created At</th>
                    <th class="text-center">Transfer Amount</th>
                    <th class="text-center">Fees (%)</th>
                    <th class="text-center">Fee Amount ($)</th>
                    <th class="text-center">Transaction Amount ($)</th>
                </thead>
                <tbody>
                    <c:forEach items='${transferDTOList}' var="item">
                        <fmt:setLocale value="en_US"/>
                        <tr>
                            <td class="text-center">${item.id}</td>
                            <td class="text-center">${item.senderId}</td>
                            <td class="text-center">${item.senderName}</td>
                            <td class="text-center">${item.recipientId}</td>
                            <td class="text-center">${item.recipientName}</td>
                            <td class="text-center">
                                <fmt:formatDate type="date" pattern="dd-MM-yyyy" value="${item.createdOn}" />
                                <fmt:formatDate type="time" pattern="HH:mm:ss" value="${item.createdAt}" />
                            </td>
                            <td class="text-end">
                                <fmt:formatNumber type="currency" pattern="#,##0" value="${item.transferAmount}" />
                            </td>
                            <td class="text-center">${item.fees}</td>
                            <td class="text-end">
                                <fmt:formatNumber type="currency" pattern="#,##0" value="${item.feesAmount}" />
                            </td>
                            <td class="text-end">
                                <fmt:formatNumber type="currency" pattern="#,##0" value="${item.transactionAmount}" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="6"></td>
                        <td class="text-center fw-bold">Total Fees Amount:</td>
                        <td></td>
                        <td class="text-end fw-bold">______ $</td>
                        <td></td>
                    </tr>
                </tfoot>

            </table>
        </div>
    </div>

    <%@ include file="/layout/footer.jsp" %>

    <%@ include file="/layout/script.jsp" %>

    <c:if test='${requestScope["navTab"] != null}'>
        <script>
            let navTab = '${requestScope["navTab"]}';

            switch (navTab) {
                case "date":
                    document.getElementById("nav-date-tab").click();
                    break;
                case "month":
                    document.getElementById("nav-month-tab").click();
                    break;
                case "date-range":
                    document.getElementById("nav-date-range-tab").click();
                    break;
            }
        </script>
    </c:if>

    <script>

        function drawMonth() {
            let monthArr = [
                'January',
                'February',
                'March',
                'April',
                'May',
                'June',
                'July',
                'August',
                'September',
                'October',
                'November',
                'December'
            ];

            let sMonth = document.getElementById("sMonth");
            let str = '';

            for (let i = 0; i < monthArr.length; i++) {
                str += `<option value="\${i + 1}">\${monthArr[i]}</option>`;
            }

            sMonth.innerHTML = str;
        }

        function drawYear() {
            let currentYear= new Date().getFullYear();

            let sYear = document.getElementById("sYear");
            let str = '';

            for (let i = 2020; i <= currentYear; i++) {

                str += `<option value='\${i}'>\${i}</option>`;
            }

            sYear.innerHTML = str;
        }

        function setMonthYear() {
            let sMonth = '${requestScope["sMonth"]}' || new Date().getMonth() + 1;
            let sYear = '${requestScope["sYear"]}' || new Date().getFullYear();

            document.getElementById('sMonth').value = sMonth;
            document.getElementById('sYear').value = sYear;
        }

        window.onload = function() {
            drawMonth();
            drawYear();

            setMonthYear();
        }

    </script>

</body>

</html>