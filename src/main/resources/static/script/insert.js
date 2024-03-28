
//quando il documento e' pronto
$(document).ready(function () {
    $.ajax({
        url: '/sess_exist',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            if(response == false){
                window.location.href = '../index.html';
            }
        },
        error: function(xhr, status, error) {
            window.location.href = '../index.html';
        }

    });    
    //nel caso in cui il form venga inviato
    $('#filmForm').submit(function (e) {
        e.preventDefault();

        //prendo i valori
        var titolo = $('#titolo').val();
        var genere = $('#genere').val();
        var anno = $('#anno').val();
        var bio = $('#bio').val();
        var foto = $('#foto').val();

        //inserisco i valori nel database tramite ajax
        $.ajax({
            url: '/inserisci',
            type: 'GET',
            data: {
                'titolo': titolo,
                'genere': genere,
                'anno': anno,
                'bio': bio,
                'foto': foto
            },
            //notifico successo o errori
            success: function (response) {

                window.location.href = '../pages/elencofilm.html';
                // Aggiungi qui eventuali azioni da eseguire dopo l'inserimento del film
            },
            error: function (xhr, status, error) {
                alert('Si è verificato un errore durante l\'inserimento del film.');
                console.log(xhr.responseText);
            }
        });
    });

    //funzione per aggiungere le opzioni
    function aggiungiOpzione(selectId, valore, testo) {
        var select = document.getElementById(selectId);
        var option = document.createElement("option");
        option.value = valore;
        option.text = testo;
        select.add(option);
    }
    $.ajax({
        url: '../controllaGeneri',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            // Aggiungi l'opzione "Tutti"
    
            // Itera attraverso ogni oggetto genere nella lista
            $.each(response.generi, function(index, item) {
                // Accedi al campo "genere" di ciascun oggetto
                var genere = item.genere;
                // Aggiungi l'opzione al filtro
                aggiungiOpzione("genere", genere, genere);
            });
        },
        error: function(xhr, status, error) {
            alert('Si è verificato un errore durante il caricamento dei generi.');
            console.log(xhr.responseText);
        }
    });
});