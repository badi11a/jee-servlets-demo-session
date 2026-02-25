# Demo session - Gestión de Estado en Java Web

Este proyecto es una aplicación educativa desarrollada en **Java Jakarta EE** diseñada para demostrar la diferencia técnica entre el manejo de estado en el servidor (Sesiones) y en el cliente (Cookies).

## Objetivo Técnico
El propósito es exponer el ciclo de vida de una petición HTTP y cómo la información persiste de manera distinta según el mecanismo utilizado, aplicando los principios del patrón de diseño **MVC (Modelo-Vista-Controlador)**.

## Arquitectura y Componentes

### 1. Persistencia en Servidor (`HttpSession`)
Se utiliza para almacenar datos críticos de autorización que no deben ser manipulados por el cliente.
* **Atributos:** Nombre de usuario, correo y rol de acceso.
* **Seguridad:** Los datos residen en la memoria RAM de Tomcat.
* **Cierre:** Se implementa `session.invalidate()` en el `LogoutServlet` para garantizar la destrucción del estado.

### 2. Persistencia en Cliente (`Cookie`)
Se utiliza para almacenar preferencias de interfaz (UI) no sensibles.
* **Atributo:** `temaVisual` (Modo claro/oscuro).
* **Ciclo de Vida:** Posee una persistencia de 30 días definida mediante `setMaxAge`.
* **Nota Técnica:** El proyecto demuestra el "desfase de ciclo" de las cookies, donde el servidor no puede leer una cookie en la misma petición en la que ordena su creación.

### 3. Controladores (Servlets)
* **`UserServlet`**: Actúa como controlador de entrada. Procesa los parámetros del request y establece el estado inicial.
* **`DashboardServlet`**: Actúa como vista dinámica (vía `PrintWriter`). Evalúa los encabezados HTTP para determinar el estilo CSS a aplicar.
* **`LogoutServlet`**: Gestiona la terminación segura del estado del usuario.

## Requisitos
* **Java:** 17 o superior.
* **Servidor:** Apache Tomcat 11.0+.
* **Gestor de Dependencias:** Maven.

## Instalación y Despliegue

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/badi11a/jee-servlets-demo-session.git](https://github.com/badi11a/jee-servlets-demo-session.git)
