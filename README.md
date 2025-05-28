# Eventos Java Sprint Security

## Descripción

Este proyecto implementa una aplicación de gestión de eventos utilizando Java y Spring Security. Está diseñado para proporcionar una solución robusta y segura para la administración de eventos, incluyendo autenticación y autorización de usuarios.

## Características principales

- **Autenticación y autorización**: Utiliza Spring Security para garantizar la seguridad de la aplicación.
- **Gestión de eventos**: Permite crear, editar y eliminar eventos.
- **Roles de usuario**: Soporte para diferentes roles de usuario como administrador y usuario estándar.
- **Configuración de Maven**: Incluye un archivo `pom.xml` para la gestión de dependencias.
- **Scripts de Maven Wrapper**: Proporciona los archivos `mvnw` y `mvnw.cmd` para ejecutar Maven sin necesidad de instalación previa.

## Estructura del proyecto

- **src/**: Contiene el código fuente de la aplicación.
- **pom.xml**: Archivo de configuración de Maven.
- **mvnw y mvnw.cmd**: Scripts para ejecutar Maven.
- **.mvn/**: Configuración adicional de Maven.

## Requisitos

- Java 17 o superior.
- Maven 3.8 o superior.

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/deperdomo/eventos-java-sprint-security.git
   ```

2. Navega al directorio del proyecto:
   ```bash
   cd eventos-java-sprint-security
   ```

3. Compila el proyecto:
   ```bash
   ./mvnw clean install
   ```

## Uso

1. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

2. Accede a la aplicación en tu navegador en `http://localhost:8080`.

## Contribución

Si deseas contribuir a este proyecto, por favor sigue los siguientes pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tus cambios.
3. Realiza tus modificaciones y realiza un commit.
4. Envía un pull request para revisión.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.

---

Para más información, visita el repositorio en [GitHub](https://github.com/deperdomo/eventos-java-sprint-security).
