package cl.td.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Capturar parámetros, incluyendo la preferencia visual
        String nombreUsuario = request.getParameter("nombre");
        String correoUsuario = request.getParameter("correo");
        String temaElegido = request.getParameter("tema"); 
        
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            nombreUsuario = "Estudiante Anónimo";
        }
        if (correoUsuario == null || correoUsuario.trim().isEmpty()) {
            correoUsuario = "sin-correo@test.com";
        }
        if (temaElegido == null || temaElegido.trim().isEmpty()) {
            temaElegido = "claro"; // Valor por defecto en caso de acceso directo
        }

        // 2. Control de acceso en la Sesión
        String rolAsignado = correoUsuario.endsWith("@alkemy.org") ? "Administrador" : "Estudiante";
        
        HttpSession session = request.getSession();
        session.setAttribute("usuarioLogueado", nombreUsuario);
        session.setAttribute("correoLogueado", correoUsuario);
        session.setAttribute("rolUsuario", rolAsignado);

        // 3. Persistencia de la preferencia en la Cookie
        // Ahora el valor de la cookie depende estrictamente de la elección del usuario
        Cookie preferenciaTema = new Cookie("temaVisual", temaElegido);
        preferenciaTema.setMaxAge(60 * 60 * 24 * 30); 
        response.addCookie(preferenciaTema);

        // 4. Delegar al Dashboard
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard");
        dispatcher.forward(request, response);
    }
}