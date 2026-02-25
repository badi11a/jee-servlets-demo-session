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
        
        HttpSession session = request.getSession();
        String nombre = (String) session.getAttribute("usuarioLogueado");
        String correo = (String) session.getAttribute("correoLogueado");
        String rol = (String) session.getAttribute("rolUsuario");

        // 1. Leer del formulario (Informativo)
        String temaFormulario = request.getParameter("tema");
        if (temaFormulario == null) {
            temaFormulario = "No enviado";
        }

        // 2. Leer de la Cookie (Estado persistido)
        String temaCookie = "claro"; 
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("temaVisual")) {
                    temaCookie = c.getValue();
                }
            }
        }

        // 3. Aplicación estricta: Solo depende de temaCookie
        String claseCss = "oscuro".equals(temaCookie) ? "tema-oscuro" : "";

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html class='" + claseCss + "'>");
        out.println("<head>");
        out.println("<title>Panel de Control</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #ffffff; color: #333333; transition: background-color 0.3s, color 0.3s; padding: 20px; }");
        out.println(".tema-oscuro body { background-color: #1a1a1a; color: #e0e0e0; }");
        out.println(".tema-oscuro button { background-color: #333333; color: #ffffff; border: 1px solid #555555; padding: 5px 10px; }");
        out.println("</style>");
        out.println("</head>");
        
        out.println("<body>");
        out.println("<h1>Panel de Control del Sistema</h1>");
        out.println("<h2>Bienvenido, " + nombre + "</h2>");
        out.println("<ul>");
        out.println("<li><strong>Correo:</strong> " + correo + "</li>");
        out.println("<li><strong>Nivel de Acceso (Sesión):</strong> " + rol + "</li>");
        out.println("<li><strong>Tema en Request (Formulario):</strong> " + temaFormulario + "</li>");
        out.println("<li><strong>Tema en Cabecera (Cookie leída):</strong> " + temaCookie + "</li>");
        out.println("</ul>");
        
        out.println("<br>");
        out.println("<a href='logout'><button>Cerrar Sesión</button></a>");
        
        out.println("</body></html>");
    }
}