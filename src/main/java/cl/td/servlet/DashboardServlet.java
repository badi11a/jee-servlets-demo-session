package cl.td.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Recuperación de datos desde la Sesión (Estado en Servidor)
        HttpSession session = request.getSession();
        String nombre = (String) session.getAttribute("usuarioLogueado");
        String correo = (String) session.getAttribute("correoLogueado");
        String rol = (String) session.getAttribute("rolUsuario");

        // 2. Lectura del parámetro del formulario (Estado en el Request actual)
        String temaFormulario = request.getParameter("tema");
        if (temaFormulario == null) {
            temaFormulario = "No enviado";
        }

        // 3. Lectura de Cookies (Estado persistido en el Cliente)
        String temaCookie = "claro"; // Valor por defecto
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("temaVisual".equals(c.getName())) {
                    temaCookie = c.getValue();
                }
            }
        }

        // 4. Lógica de Aplicación Estricta:
        // El CSS se define UNICAMENTE según la Cookie recibida en los headers.
        String claseCss = "oscuro".equals(temaCookie) ? "tema-oscuro" : "";

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html class='" + claseCss + "'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Dashboard - Gestión de Estado</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #ffffff; color: #333333; transition: all 0.3s; padding: 40px; }");
        out.println(".tema-oscuro body { background-color: #121212; color: #e0e0e0; }");
        out.println("ul { line-height: 1.8; }");
        out.println("button { cursor: pointer; padding: 8px 16px; margin-top: 20px; }");
        out.println(".tema-oscuro button { background-color: #333; color: white; border: 1px solid #555; }");
        out.println("</style>");
        out.println("</head>");
        
        out.println("<body>");
        out.println("<h1>Panel de Control</h1>");
        out.println("<hr>");
        out.println("<h3>Información de la Sesión:</h3>");
        out.println("<ul>");
        out.println("<li><strong>Usuario:</strong> " + nombre + "</li>");
        out.println("<li><strong>Email:</strong> " + correo + "</li>");
        out.println("<li><strong>Rol:</strong> " + rol + "</li>");
        out.println("</ul>");

        out.println("<h3>Análisis de Persistencia:</h3>");
        out.println("<ul>");
        out.println("<li><strong>Origen Formulario (Request):</strong> " + temaFormulario + "</li>");
        out.println("<li><strong>Origen Persistido (Cookie):</strong> " + temaCookie + "</li>");
        out.println("</ul>");
        
        out.println("<a href='logout'><button>Cerrar Sesión</button></a>");
        out.println("</body></html>");
    }
}