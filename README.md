# Demo session - Gestión de Estado y PRG en Java Web

Este proyecto es una aplicación educativa desarrollada en Java Jakarta EE, diseñada para demostrar la diferencia técnica entre el manejo de estado en el servidor (Sesiones) y en el cliente (Cookies), implementando validación estricta y el patrón arquitectónico PRG (Post-Redirect-Get).

## Objetivo Técnico

El propósito es exponer el ciclo de vida de las peticiones HTTP seguras, el control de acceso en rutas protegidas y la sincronización de estado entre cliente y servidor.

## Arquitectura y Componentes

### 1. Patrón PRG (Post-Redirect-Get)
Se implementa para evitar reenvíos accidentales de formularios y solucionar la desincronización en la lectura de Cookies.
* **POST:** Mutación de estado y validación de entrada (`UserServlet`).
* **REDIRECT:** Interrupción del flujo mediante código HTTP 302 (`response.sendRedirect()`).
* **GET:** Consumo de estado persistido en una nueva petición limpia (`DashboardServlet`).

### 2. Persistencia en Servidor (HttpSession)
Se utiliza para almacenar datos críticos de autorización y aplicar control de acceso.
* **Atributos:** Nombre de usuario, correo y rol de acceso.
* **Seguridad y Control de Acceso:** Las rutas protegidas utilizan `request.getSession(false)` para denegar el acceso a usuarios no autenticados y evitar la creación de sesiones vacías. Los datos residen en la memoria RAM de Tomcat.
* **Cierre:** Se implementa `session.invalidate()` en el `LogoutServlet` para garantizar la destrucción total del estado.

### 3. Persistencia en Cliente (Cookie)
Se utiliza para almacenar preferencias de interfaz (UI) no sensibles.
* **Atributo:** `temaVisual` (Modo claro/oscuro).
* **Ciclo de Vida:** Posee una persistencia de 30 días definida mediante `setMaxAge`.
* **Sincronización:** Gracias al redireccionamiento PRG, la cookie es almacenada por el navegador durante el paso intermedio y enviada inmediatamente en la petición GET posterior, permitiendo su lectura sin desfase.

### 4. Controladores (Servlets)
* **UserServlet:** Actúa como controlador de entrada. Procesa el payload vía `POST`, ejecuta validación estricta de backend (rechazando nulos y cadenas vacías), establece el estado inicial y ejecuta la redirección.
* **DashboardServlet:** Actúa como controlador de acceso y vista dinámica (vía `PrintWriter`). Verifica la integridad de la sesión y lee las cabeceras HTTP entrantes para determinar el estilo CSS a aplicar.
* **LogoutServlet:** Gestiona la terminación segura del estado del usuario y redirige al inicio.

## Requisitos

* **Java:** 17 o superior.
* **Servidor:** Apache Tomcat 11.0+.
* **Gestor de Dependencias:** Maven.

## Instalación y Despliegue

Clonar el repositorio:
```bash
git clone [https://github.com/badi11a/jee-servlets-demo-session.git](https://github.com/badi11a/jee-servlets-demo-session.git)