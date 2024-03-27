//quando il documento e' pronto 
$(document).ready(function () {

    $.ajax({
        url: '../controllaSessione',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            
        },
        error: function(xhr, status, error) {
            window.location.href = '../index.html';
        }

    });
    
    //ttieni l'ID del film dalla query string dell'url
    var urlParams = new URLSearchParams(window.location.search);
    var filmID = urlParams.get('ID');

    //fai la richiesta ajax per ottenere i dettagli del film
    $.ajax({
        type: 'GET',
        url: '../getFilm',
        dataType: 'json',
        data: { 'id_film': filmID },
        success: function (response) {
                    //mostra i dettagli del film
                    var film = response;
                    var filmDetails = `
                            <div class="col-md-6 custom-card">
                                <div class="card">
                                    <img src="imgs/${film.percorso_loc.split("\\")[film.percorso_loc.split("\\").length - 1]}" alt="${film.nome}" class="card-img-top img-fluid">
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
        },
        error: function (jqXHR, textStatus, errorThrown) {
            //gestisce eventuali errori di richiesta ajax
            console.error('Errore nella richiesta AJAX:', textStatus, errorThrown);
            $('#filmDetails').html('<p class="text-danger">Errore nella richiesta dei dettagli del film.</p>');
        }
    });
});