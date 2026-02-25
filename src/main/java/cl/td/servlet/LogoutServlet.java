package cl.td.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Obtener la sesión actual. 
        // El parámetro 'false' evita que se cree una nueva sesión si no existe ninguna.
        HttpSession session = request.getSession(false);
        
        // 2. Destruir la sesión y todos sus atributos en la memoria del servidor
        if (session != null) {
            session.invalidate();
        }
        
        // 3. Redirigir al usuario de vuelta a la página principal
        // A diferencia de RequestDispatcher (interno), sendRedirect obliga al navegador 
        // a realizar una nueva petición GET a la URL indicada.
        response.sendRedirect("index.jsp");
    }
}