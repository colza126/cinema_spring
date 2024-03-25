<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inserisci Film</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body class="bg-light">
<script src="script/insert.js"></script>

<div class="container mt-5">
    <h1 class="text-center text-primary mb-4">Inserisci un nuovo film</h1>
    
    <form id="filmForm">
        <div class="form-group">
            <label for="titolo">Titolo:</label>
            <input type="text" class="form-control" id="titolo" name="titolo" required>
        </div>

        <div class="form-group">
            <label for="genere">Genere:</label>
            <select class="form-control" id="genere" name="genere" required>
                <!-- Opzioni verranno aggiunte dinamicamente -->
            </select>
        </div>

        <div class="form-group">
            <label for="anno">Anno:</label>
            <input type="number" class="form-control" id="anno" name="anno" required>
        </div>

        <div class="form-group">
            <label for="bio">Bio:</label>
            <input type="text" class="form-control" id="bio" name="bio" required>
        </div>

        <div class="form-group">
            <label for="foto">Locandina:</label>
            <input type="file" class="form-control" id="foto" name="foto">
        </div>

        <button type="submit" class="btn btn-primary">Inserisci</button>
    </form>
</div>



</body>
</html>
