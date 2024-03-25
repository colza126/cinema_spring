<?php
    if(!isset($_SESSION)){
        session_start();
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dettaglio Film</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        .custom-card {
            max-width: 400px;
            margin: auto;
        }

        .custom-card .card-img-top {
            max-height: 350px;
            object-fit: cover;
        }

        .custom-card .card-body {
            padding: 10px;
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <h1 class="mb-4 text-center">Dettaglio Film</h1>

    <div id="filmDetails" class="row justify-content-center">
        
    </div>

    <a href="elencoFilm.php" class="btn btn-secondary btn-sm mt-3 d-block mx-auto">Torna a Elenco Film</a>
</div>

<script src="script/film"></script>
</body>
</html>
