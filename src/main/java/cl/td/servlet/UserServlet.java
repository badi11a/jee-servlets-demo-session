package cl.td.servlet;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Lectura del payload (Lo que el cliente ingresa en el formulario)
        String nombreUsuario = request.getParameter("nombre");
        String correoUsuario = request.getParameter("correo");
        String temaElegido = request.getParameter("tema"); 
        
        // 2. Validación de backend estricta
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() || 
            correoUsuario == null || correoUsuario.trim().isEmpty()) {
            response.sendRedirect("index.jsp?error=1");
            return;
        }

        // 3. Creación de Sesión (Identidad)
        String rolAsignado = correoUsuario.endsWith("@alkemy.org") ? "Administrador" : "Estudiante";
        HttpSession session = request.getSession();
        session.setAttribute("usuarioLogueado", nombreUsuario);
        session.setAttribute("correoLogueado", correoUsuario);
        session.setAttribute("rolUsuario", rolAsignado);

        // 4. Lógica de Persistencia de la Cookie (Aquí se resuelve la imprevisibilidad)
        // Solo sobrescribimos la cookie si el cliente efectivamente seleccionó un tema válido.
        if (temaElegido != null && !temaElegido.trim().isEmpty()) {
            Cookie preferenciaTema = new Cookie("temaVisual", temaElegido);
            preferenciaTema.setMaxAge(60 * 60 * 24 * 30); 
            response.addCookie(preferenciaTema);
        }

        // 5. Redirección (Forzar al navegador a aplicar la cookie y solicitar el Dashboard vía GET)
        response.sendRedirect("dashboard");
    }
}