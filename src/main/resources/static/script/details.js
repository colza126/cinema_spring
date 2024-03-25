//quando il documento e' pronto 
$(document).ready(function () {
    //ttieni l'ID del film dalla query string dell'url
    var urlParams = new URLSearchParams(window.location.search);
    var filmID = urlParams.get('ID');

    //fai la richiesta ajax per ottenere i dettagli del film
    $.ajax({
        type: 'POST',
        url: 'ajax/getFilm.php',
        dataType: 'json',
        data: { 'id_film': filmID },
        success: function (response) {
            if (response.status !== 'fail') {
                //i dati sono disponibili in response.film
                if (response.film) {
                    //mostra i dettagli del film
                    var film = response.film;
                    var filmDetails = `
                            <div class="col-md-6 custom-card">
                                <div class="card">
                                    <img src="imgs/${film.foto.split("\\")[film.foto.split("\\").length - 1]}" alt="${film.nome}" class="card-img-top img-fluid">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">${film.nome}</h5>
                                        <p class="card-text">
                                            Anno Produzione: ${film.anno_produzione}<br>
                                            Genere: ${film.genere}<br>
                                            Bio: ${film.bio}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        `;
                    $('#filmDetails').html(filmDetails);
                } else {
                    //nessun dettaglio del film disponibile
                    $('#filmDetails').html('<p class="text-muted">Nessun dettaglio disponibile per questo film.</p>');
                }
            } else {
                //errore nella richiesta
                console.error('Errore nella richiesta AJAX:', response);
                $('#filmDetails').html('<p class="text-danger">Errore nella richiesta dei dettagli del film.</p>');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            //gestisce eventuali errori di richiesta ajax
            console.error('Errore nella richiesta AJAX:', textStatus, errorThrown);
            $('#filmDetails').html('<p class="text-danger">Errore nella richiesta dei dettagli del film.</p>');
        }
    });
});