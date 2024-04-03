//quando il documento e' pronto
$(document).ready(function () {

    $.ajax({
        url: 'sess_exist',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            if (response)
                window.location.href = "../pages/elencoFilm.html";
        },
        error: function (xhr, status, error) {
            alert('Errore durante controllo sessione');
            console.log(xhr.responseText);
        }
    });

    //mostra il modulo di login
    $('#showLoginForm').click(function () {
        $('#loginForm').show();
        $('#registrationForm').hide();
        $('#recoveryPw').hide();
    });

    //mostra il modulo di registrazione
    $('#showRegistrationForm').click(function () {
        $('#registrationForm').show();
        $('#loginForm').hide();
        $('#recoveryPw').hide();
    });

    //mostra il modulo di recupero password
    $('#showRecoveryForm').click(function () {
        $('#recoveryPw').show();
        $('#loginForm').hide();
        $('#registrationForm').hide();
    });

    // funzione al richiamo di login
    $('#loginForm').submit(function (e) {
        e.preventDefault();
        //prendo i valori
        var mailVal = $('#loginMail').val();
        var password = $('#loginPassword').val();

        $.ajax({
            type: 'GET',
            url: 'login',
            data: {
                mail: mailVal,
                pass: password
            },
            dataType: 'json',
            //gestione degli stati possibili
            complete: function (xhr, textStatus) {
                console.log('Request completed with status: ' + textStatus);
            },
            success: function (response) {
                if (response == true) {
                    window.location.href = "pages/elencoFilm.html";
                }
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
                console.error('Status:', status);
                console.error('XHR:', xhr);
            }
        });

        return false;

    });

    // Registrazione
    $('#registrationForm').submit(function (e) {
        e.preventDefault();
        //prendo i valori
        var mailVal = $('#registrationMail').val();
        var password = $('#registrationPassword').val();
        //faccio la richiesta ajax per aggiornare il token
        $.ajax({
            type: 'POST',
            url: 'aggiornaToken',
            data: {
                mail: mailVal
            },
            //in caso di successo procedo con la registrazione
            success: function (response) {
                console.log(response);
                $.ajax({
                    type: 'POST',
                    url: 'registration',
                    data: {
                        mail: mailVal,
                        password: password
                    },
                    success: function (response) {
                        //se va a buon fine manda una mail di conferma
                        if (response.status === 'success') {
                            // Reindirizza alla home page
                            alert('Registration successful!');
                            var token = response.token;
                            //nel caso di conferma invia una mail
                            $.ajax({
                                type: 'POST',
                                url: 'mailConferma',
                                data: {
                                    mail: mailVal,
                                    contenuto: "Clicca questo link per confermare la tua registrazione: http://localhost:8080/pages/paginaConferma.html?token=" + token
                                },
                                //in caso di successo invia un alert
                                success: function (response) {
                                    alert("Mail inviata");
                                },
                                //altrimenti visualizza in console eventuali errori
                                error: function (xhr, status, error) {
                                    console.error(xhr.responseText);
                                    console.error("Status: " + status);
                                    console.error("Error: " + error);
                                }
                            });
                        } else {
                            // Visualizza messaggio di errore
                            $('#registrationErrorMessage').text(response.message);
                        }
                    }
                });
            },
            error: function (xhr, status, error) {
                console.error(xhr.responseText);
                console.error("Status: " + status);
                console.error("Error: " + error);
            }
        });
        return false;
    });

    //Recupero password 
    $('#recoveryPw').submit(function (e) {
        e.preventDefault();
        //prnedo il valore della mail
        var mailVal = $('#recoveryMail').val();
        //faccio la richiesta ajax per aggiornare il token
        if (mailVal != null) {
            //richiesta per aggiungere il token
            $.ajax({
                type: 'POST',
                url: 'aggiornaToken',
                data: {
                    mail: mailVal
                },
                //se funziono controlla che la mail esista nel database
                success: function (response) {
                    $.ajax({
                        type: 'POST',
                        url: 'checkMail',
                        data: {
                            mail: mailVal
                        },
                        //se la mail esiste invia una mail di recupero password
                        success: function (response) {
                            alert(response.exists);
                            if (response === true) {

                                $.ajax({
                                    type: 'POST',
                                    url: 'getToken',
                                    data: {
                                        mail: mailVal,
                                    },
                                    //notifico l'invio degli errori
                                    success: function (response) {
                                        console.log("token preso");
                                        token = response;

                                        $.ajax({
                                            type: 'POST',
                                            url: 'mailConferma',
                                            data: {
                                                mail: mailVal,
                                                contenuto: "Per recuperare la tua password visita questo link: http://localhost:8080/pages/recuperoPw.html?token=" + token
                                            },
                                            //notifico l'invio degli errori
                                            success: function (response) {
                                                alert("Mail inviata");
                                            },
                                            //visualizzo eventuali errori
                                            error: function (xhr, status, error) {
                                                console.error(xhr.responseText);
                                                console.error("Status: " + status);
                                                console.error("Error: " + error);
                                            }
                                        });
                                    },
                                    //visualizzo eventuali errori
                                    error: function (xhr, status, error) {
                                        console.error(xhr.responseText);
                                        console.error("Status: " + status);
                                        console.error("Error: " + error);
                                    }
                                }
                                );
                            } else {
                                //notifico se la mail none esiste
                                alert("Mail non esistente");
                            }
                        },

                        //visualizzo eventuali errori
                        error: function (xhr, status, error) {
                            console.error(xhr.responseText);
                            console.error("Status: " + status);
                            console.error("Error: " + error);
                        }
                    });
                }
            });
        }
        return false;
    });
});