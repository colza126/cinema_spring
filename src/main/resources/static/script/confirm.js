
//quando il documento è pronto esegue la funzione
$(document).ready(function() {
    // recupera il token dalla query string
    var urlParams = new URLSearchParams(window.location.search);
    var token = urlParams.get('token');
    
    
    // ed effettua la chiamata AJAX a confermaToken.php

    $.ajax({
        //con richiesta get al file php che interroga il database
        type: 'GET',
        url: 'ajax/confermaToken.php',
        //verifica se il token e' presente
        data: { token: token },
        //in caso di successo esegue la funzione
        success: function(response) {
            // Visualizza il risultato nella pagina
            $('#confirmationResult').html(response);

            // Mostra il pulsante "Torna alla Home" solo se l'account è stato attivato con successo
            if (response.includes('Account attivato con successo')) {
                $('#backToHome').show();
            }
        },
        //altrimenti in caso di errore mostra l'errore
        error: function() {
            // Gestisci gli errori, se necessario
            $('#confirmationResult').html('<div class="alert alert-danger" role="alert">Errore durante la conferma dell\'account.</div>');
        }
    });

    //abilita il pulsante torna alla home
    $('#backToHome').click(function() {
        window.location.href = "index.php";
    });
});