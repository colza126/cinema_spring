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
            url: '../recoveryPW',
            type: 'POST',
            data: {
                token: token,
                password: newPassword
            },
            success: function(response) {
                console.log(response)
                if(response == true){
                    window.location.href="../index.html";
                }
            }
        });
    });
});