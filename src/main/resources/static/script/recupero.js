//al caricamento del documento
$(document).ready(function() {
    //se viene fillato il form di modifica password
    $("form").submit(function(e) {
        e.preventDefault();
        //prendo i valori
        var urlParams = new URLSearchParams(window.location.search);
        var token = urlParams.get('token');
        var newPassword =  $("#password").val();
        //richiedo una modifica della password
        $.ajax({
            url: 'ajax/modificaPassword.php',
            type: 'POST',
            data: {
                token: token,
                new_password: newPassword
            },
            success: function(response) {
                alert(response);
            }
        });
    });
});