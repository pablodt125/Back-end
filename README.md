# Back-end
Back-end
El proyecto se realizó utilizando el framework de Java llamado SpringBoot 

Para correr la app en localhost se debe:

1. Clonar el repositorio

2.  Tener el proyecto en un IDE preferiblemente IntelliJ si no pues en Eclipse y esperar a que descargue las dependencias.

3. se debe tener instalado previamente el docker Engine para tener acceso al archivo YAML de docker compose.

4. Se ejecutará el comando"./sql:/docker-entrypoint-initdb.d" para poder tener acceso a la base de datos

5. Luego se debería levantar los servicios correctamente


Ahora si por algún motivo el  paso 3 no funciona

1. Se deberá instalar postgresql

2. Crear una base de datos llamada bd_app_person con usuario postgres y contraseña Juan1995*

3. Por último ejecutar el proyecto de springboot
