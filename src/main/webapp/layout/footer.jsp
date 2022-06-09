<div id="footer">
    <div class="container">
        <div id="message-alert">
            <c:if test='${requestScope["success"] != null}'>
                <div class="alert alert-success">
                    <ul class="mess-success">
                        <li>${requestScope["success"]}</li>
                    </ul>
                    <script>
                        document.getElementById("footer").classList.add("footer-success");
                        document.getElementById("footer").classList.remove("footer-error");
                    </script>
                </div>
                <span id="close">X</span>
                <script>
                    function fadeIn(el, el2, timeOut) {
                        el.animate({
                            opacity: 0
                        }, {
                            duration: timeOut,
                            easing: "linear",
                            iterations: 1,
                            fill: "both"
                        })
                            .onfinish = function() {
                            el2.innerHTML = "";
                        }
                    }

                    window.onload = function() {
                        let messageAlert = document.getElementById("message-alert");
                        let footer = document.getElementById("footer");

                        fadeIn(footer, messageAlert, 10000);

                        let btnClose = document.getElementById("close");

                        btnClose.addEventListener("click", function () {
                            fadeIn(footer, messageAlert, 50);
                        })
                    }

                </script>
            </c:if>
            <c:if test='${requestScope["errors"] != null}'>
                <div class="alert alert-danger">
                    <ul class="mess-errors">
                        <c:forEach items='${requestScope["errors"]}' var="item">
                            <li>${item}</li>
                        </c:forEach>
                    </ul>
                    <script>
                        document.getElementById("footer").classList.remove("footer-success");
                        document.getElementById("footer").classList.add("footer-error");
                    </script>
                </div>
            </c:if>
        </div>
    </div>
</div>