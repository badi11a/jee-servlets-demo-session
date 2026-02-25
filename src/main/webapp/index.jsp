<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es" class="${cookie.temaVisual.value == 'oscuro' ? 'tema-oscuro' : ''}">
<head>
    <title>Inicio - Demo session</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
            color: #333333;
            transition: background-color 0.3s, color 0.3s;
            padding: 20px;
        }
        .tema-oscuro body {
            background-color: #1a1a1a;
            color: #e0e0e0;
        }
        .tema-oscuro input, .tema-oscuro select {
            background-color: #333333;
            color: #ffffff;
            border: 1px solid #555555;
        }
    </style>
</head>
<body>
    <h2>Demo session</h2>
    <p>Ingresa tus datos y preferencias:</p>
    
    <form action="login" method="GET">
        <label for="nombre">Nombre de usuario:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <label for="correo">Correo electrónico:</label>
        <input type="email" id="correo" name="correo" required><br><br>

        <label for="tema">Preferencia visual:</label>
        <select id="tema" name="tema">
            <option value="claro">Modo Claro</option>
            <option value="oscuro">Modo Oscuro</option>
        </select><br><br>

        <button type="submit">Entrar</button>
    </form>
</body>
</html>