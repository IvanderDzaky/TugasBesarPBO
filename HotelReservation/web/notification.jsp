<!-- Toast Notification Container -->
<div id="toast" style="display:none; position: fixed; top: 20px; left: 50%; transform: translateX(-50%); background-color: #f44336; color: white; padding: 16px 24px; border-radius: 8px; z-index: 1000; box-shadow: 0 2px 6px rgba(0,0,0,0.3); text-align: center;">
    <span id="toast-msg"></span>
</div>

<script>
    function showToast(message, isSuccess = false) {
        const toast = document.getElementById("toast");
        const toastMsg = document.getElementById("toast-msg");

        toastMsg.innerText = message;
        toast.style.backgroundColor = isSuccess ? "#4CAF50" : "#f44336"; // hijau / merah
        toast.style.display = "block";

        setTimeout(() => {
            toast.style.display = "none";
        }, 3000);
    }
</script>

<%
    String errorMsg = (String) session.getAttribute("errorMsg");
    String successMsg = (String) session.getAttribute("successMsg");

    if (errorMsg != null || successMsg != null) {
        String msg = errorMsg != null ? errorMsg : successMsg;
        msg = msg.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
        session.removeAttribute("errorMsg");
        session.removeAttribute("successMsg");
%>
<script>
    showToast("<%= msg %>", <%= (successMsg != null) %>);
</script>
<%
    }
%>
