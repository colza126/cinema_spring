// Funzione che crea la finestra di conferma
function conferma() {
    return confirm("Sei sicuro di voler eliminare il film?");
}

// Funzione che crea un'opzione per un select (per il filtro)
function aggiungiOpzione(selectId, valore, testo) {
    var select = document.getElementById(selectId);
    var option = document.createElement("option");
    option.value = valore;
    option.text = testo;
    select.add(option);
}

// Funzione che carica i film
function caricaFilm(genere, permessi_admin) {
    // Effettua una richiesta AJAX per ottenere i film
    $.ajax({
        type: 'GET',
        url: '../getFilms',
        dataType: 'json',
        // Nel caso la risposta abbia successo
        success: function(response) {
            if (response) {
                // Per ogni film
                $.each(response, function(index, film) {
                    // Controlla il genere e i permessi
                    if (permessi_admin && (genere === "Tutti" || genere === film.genere)) {
                        // Visualizza il film con i pulsanti per eliminare e modificare
                        var filmCard = `
                            <div class="col-md-6 mb-4">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">${film.nome}</h5>
                                        <p class="card-text">
                                            Anno Produzione: ${film.anno_produzione}<br>
                                            Genere: ${film.genere}<br>
                                            Bio: ${film.bio}
                                        </p>
                                        <a href="dettaglioFilm.html ?ID=${encodeURIComponent(film.id)}" class="btn btn-primary mr-2">Dettagli</a>
                                        <button id="eliminaFilm${film.id}" class="btn btn-danger" value="${encodeURIComponent(film.id)}">Elimina</button>
                                    </div>
                                </div>
                            </div>`;
                        // Inserisci il div contenente il film all'interno del container
                        $('#filmContainer').append(filmCard);
                        // Se viene cliccato il tasto per eliminare il film
                        $(`#eliminaFilm${film.ID}`).click(function(e) {
                            // Conferma
                            if (!conferma()) {
                                return;
                            }
                            e.preventDefault();
                            var id_film = $(this).val();
                            // Effettua una richiesta AJAX per eliminare il film
                            $.ajax({
                                type: 'POST',
                                url: 'ajax/cancellaFilm.php',
                                data: {
                                    "id_film": id_film
                                },
                                success: function(response) {
                                    if (response.status === 'success') {
                                        $(`#${film.id}`).remove();
                                        alert('Film eliminato!');
                                    } else {
                                        alert('Film non eliminato!');
                                    }
                                }
                            });
                        });
                    } else if (genere === "Tutti" || genere === film.genere) {
                        // Visualizza il film per genere
                        var filmCard = `
                            <div class="col-md-6 mb-4">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">${film.nome}</h5>
                                        <p class="card-text">
                                            Anno Produzione: ${film.anno_produzione}<br>
                                            Genere: ${film.genere}<br>
                                            Bio: ${film.bio}
                                        </p>
                                        <a href="dettaglioFilm.html?ID=${encodeURIComponent(film.id)}" class="btn btn-primary">Dettagli</a>
                                    </div>
                                </div>
                            </div>`;
                        $('#filmContainer').append(filmCard);
                    }
                });
                // Se l'utente ha i permessi admin, visualizza il pulsante per inserire un film
                if (permessi_admin) {
                    var bottone = '<div class="col-md-12 mt-4"><a href="inserisci.php" class="btn btn-success btn-block">Inserisci film</a></div>';
                    $('#filmContainer').append(bottone);
                }
            } else {
                // Se non ci sono film disponibili
                $('#filmContainer').html('<p class="text-muted">Nessun film disponibile.</p>');
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Errore nella richiesta AJAX:', textStatus, errorThrown);
        }
    });
}

// Inizializza la variabile per i permessi admin di default a false
var permessi_admin = false;

// Quando il documento è pronto
$(document).ready(function() {
    // Fai una richiesta AJAX per ottenere i generi
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
                aggiungiOpzione("filtro", genere, genere);
            });
        },
        error: function(xhr, status, error) {
            alert('Si è verificato un errore durante il caricamento dei generi.');
            console.log(xhr.responseText);
        }
    });

    // Fai una richiesta AJAX per controllare i permessi
    $.ajax({
        type: 'GET',
        url: '../getPermessi',
        dataType: 'json',
        // Controlla i permessi e nel caso richiama la funzione per caricare i film
        success: function(response) {
            permessi_admin = response === "true";
            caricaFilm("Tutti", permessi_admin);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Errore nella richiesta AJAX:', textStatus, errorThrown);
        }
        
        
    });

    //quando il filtro cambia modifica i film
    $("#filtro").change(function() {
        $("#filmContainer").empty();
        caricaFilm($("#filtro").val(), permessi_admin);
    });
    
});
