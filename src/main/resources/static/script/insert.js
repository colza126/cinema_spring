
//quando il documento e' pronto
$(document).ready(function () {
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
            url: 'ajax/inserisci_film.php',
            type: 'POST',
            data: {
                'titolo': titolo,
                'genere': genere,
                'anno': anno,
                'bio': bio,
                'foto': foto
            },
            //notifico successo o errori
            success: function (response) {

                alert('Film inserito con successo!');
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
    //controllo i generi e li carico 
    $.ajax({
        url: 'ajax/controllaGeneri.php',
        type: 'POST',
        dataType: 'json',
        success: function (response) {
            $.each(response.generi, function (index, genere) {
                aggiungiOpzione("genere", genere, genere);
            });
        },
        error: function (xhr, status, error) {
            alert('Si è verificato un errore durante il caricamento dei generi.');
            console.log(xhr.responseText);
        }
    });
});