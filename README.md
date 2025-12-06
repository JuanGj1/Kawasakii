 🏍️ Proyecto E-commerce Kawasaki (Spring Boot & Thymeleaf)

Este proyecto es una plataforma de comercio electrónico para la venta de motocicletas y servicios de la marca Kawasaki, construida utilizando el framework Spring Boot.



🚀 Tecnologías Clave

Este proyecto fue desarrollado con un stack tecnológico moderno y robusto:

* **Backend:** Java 21 con Spring Boot 3.x
* **Base de Datos:** MySQL
* **Persistencia:** Spring Data JPA / Hibernate
* **Frontend:** Thymeleaf (HTML)
* **Estilos/UI:** Bootstrap 5 (Temas Oscuros y Neón)
* **Seguridad:** Spring Security (Gestión de roles y autenticación)
* **Gráficas/Reportes:** Chart.js (para visualizar estadísticas de ventas)
* **Generación de Documentos:** iText (para tickets de compra en PDF)
* **Gestión de Dependencias:** Apache Maven


 ✨ Características Principales

1.  **Catálogo Dinámico:** Muestra productos cargados directamente desde la base de datos (Entidad `Producto`).
2.  **Carrito de Compras:** Permite agregar, ver y eliminar ítems de la sesión.
3.  **Flujo de Compra Completo:** Simulación de checkout, registro de la orden (`Compra` y `ItemCarrito`) en la base de datos.
4.  **Generación de Ticket PDF:** Después de la compra, se puede descargar un ticket detallado de la transacción (requiere iText).
5.  **Panel de Administración (Protegido):**
    * **Autenticación:** Acceso restringido por rol (`ROLE_ADMIN`) usando Spring Security.
    * **CRUD:** Gestión completa de productos (Crear, Listar, Modificar, Eliminar).
    * **Estadísticas:** Visualización de ventas mensuales mediante gráficas de Chart.js.
6.  **Funcionalidad de Favoritos:** Permite marcar productos en el catálogo (Entidad `Producto`).



 ⚙️ Configuración del Entorno Local

 1. Requisitos Previos

Asegúrate de tener instalado:

* **JDK 21 o superior**
* **Apache Maven** (configurado en el PATH)
* **Servidor MySQL** (corriendo en el puerto 3305)

 2. Configuración de la Base de Datos

Edita el archivo `src/main/resources/application.properties` con tus credenciales de MySQL.

```properties
# Configuración en application.properties
spring.datasource.url=jdbc:mysql://localhost:3305/kawasaki_DB1?createDatabaseIfNotExist=true...
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA
