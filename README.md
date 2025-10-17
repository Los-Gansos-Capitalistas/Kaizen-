# Kaizen-
*Manual de Instalación y Configuración*
Pasos de instalación:
1.	Descargar el proyecto: Obtener el código fuente desde el repositorio GitHub o el archivo ZIP.
2.	Instalar dependencias: Ejecutar ./mvnw clean install para descargar todas las dependencias necesarias.
3.	Configurar base de datos:
   
a.	Editar el archivo src/main/resources/application.properties.

b.	Especificar las credenciales de la base de datos MySQL.

5.	Ejecutar la aplicación:
   
a.	Desde terminal: ./mvnw spring-boot:run

b.	O ejecutar la clase principal HabitTrackerApplication.java desde el IDE.

8.	Verificar funcionamiento: Acceder a http://localhost:8080 para comprobar que la aplicación se encuentra en ejecución.
   
Notas adicionales:

•	Si se utiliza MySQL, asegurarse de que el servicio esté corriendo.

•	En caso de errores de conexión, revisar los parámetros en application.properties.

•	El entorno recomendado incluye Java 17, Maven 3.8+, y un IDE como IntelliJ IDEA o Eclipse.

*Instrucciones para la Ejecución del Proyecto*

•	Requisitos previos:

  o Java JDK 17 o superior

  o	Maven 3.8 o superior

  o	IDE: IntelliJ IDEA / Eclipse / VS Code

  o	Base de datos MySQL o H2

•	Ejecución desde la línea de comandos:

  o	En macOS/Linux: ./mvnw spring-boot:run

  o	En Windows: mvnw.cmd spring-boot:run

•	Ejecución mediante archivo JAR:

  o	Compilar: ./mvnw clean package

  o	Ejecutar: java -jar target/kaizen-0.0.1-SNAPSHOT.jar
  
•	Acceso a la aplicación: http://localhost:8080
