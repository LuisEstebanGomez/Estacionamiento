# Estacionamiento
 Consigna de entrenamiento 

 # Estacionamiento - Back-end

Este es el repositorio del back-end de la aplicación "Estacionamiento". Esta aplicación está desarrollada utilizando Spring Boot con Java 17 y gestiona el sistema de estacionamiento.

## Requisitos previos

Antes de comenzar con la instalación, asegúrate de tener instalados los siguientes componentes en tu entorno de desarrollo:

- Java Development Kit (JDK) 17
- Eclipse IDE
- Maven 3.x
- MySQL Server (asegúrate de tener una base de datos creada para el proyecto)

## Instalación
_______________

Clona este repositorio en tu máquina local:

Abre Eclipse y selecciona "File" (Archivo) -> "Import" (Importar).

En el cuadro de diálogo "Importar", selecciona "Existing Maven Projects" (Proyectos Maven existentes) y haz clic en "Next" (Siguiente).

Haz clic en "Browse" (Examinar) y navega hasta el directorio del proyecto "estacionamiento" que clonaste anteriormente. Luego, selecciona el directorio y haz clic en "Finish" (Finalizar).


Configuración de la base de datos:
___________________________________

Crea una base de datos en MySQL para la aplicación (puedes utilizar PhpMyAdmin o cualquier otra herramienta).
Abre el archivo application.properties ubicado en src/main/resources y modifica la configuración de la base de datos con tus credenciales:

spring.datasource.url=jdbc:mysql://localhost:3306/user?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=
spring.main.banner-mode=off
spring.jpa.hibernate.ddl-auto=update

Ejecuta la aplicación:
______________________

Abre el archivo EstacionamientoApplication.java ubicado en src/main/java/com/example/estacionamiento.
Haz clic derecho en el archivo y selecciona "Run As" (Ejecutar como) -> "Java Application" (Aplicación Java).
La aplicación ahora estará en funcionamiento en http://localhost:8080.

La carpeta "Collections" tiene collections para Postman para realizar diferentes pruebas.
