# reto-trainee-backend
Aplicación rest en java sping boot parte del reto de PeruApps

## Apellidos y Nombres

> Sanchez Ramirez Emir Efrain

---

## Instalación

1. Crear la base de datos en MySQL
 
    `create database trainee;`

     `use trainee;`

    `CREATE TABLE tbl_usuario (
    id INT NOT NULL AUTO_INCREMENT,
    usuario VARCHAR(45) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    fecha_nacimiento DATETIME NOT NULL,
    edad INT NOT NULL,
    PRIMARY KEY (id));`
  
2. Importar el proyecto en cualquier editor de códico como: <br>
  editor de código => [Sping Tool Suite](https://spring.io/tools).<br>
  editor de código => [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows). <br>

3. Configurar java 11 en el SO y en el editor <br>

4. Ejecutar el proyecto Sprint Boot <br>

5. Abrir postman <br>
  descargar postman => [postman](https://www.postman.com/downloads/?utm_source=postman-home).<br>

6. Mediante el método POST<br>
  `http://localhost:8071/api/usuarios/crear` <br>
  Body > raw > JSON <br>
  {<br>
    "usuario": "esanchez",<br>
    "correo": "esanchez@correo.com",<br>
    "password": "123456",<br>
    "fechaNacimiento": "1999-10-09"<br>
}<br>

7. Metodo POST para iniciar sesión <br>
    `http://localhost:8071/api/auth/login`<br>
    Body > raw > JSON <br>
  {
    "correo": "esanchez@correo.com",
    "password": "123456"
  }
    
    Retornará un token
8. Metodo GET para listar los usuarios<br>
    `http://localhost:8071/api/usuarios/listar`<br>
    Agregar en el Headers => Authorization y el token
9. Metodo DELETE para eliminar<br>
    `http://localhost:8071/api/usuarios/eliminar/3`
    Agregar en el Headers => Authorization y el token
10. Metodo PUT para actualizar<br>
    `http://localhost:8071/api/usuarios/actualizar`<br>
    Agregar en el Headers => Authorization y el token<br>
    Body > raw > JSON <br>
    {
    "id": 2,
    "usuario": "jcuadros_editww",
    "correo": "jcuadros_edit@correo.com",
    "password": "123456",
    "fechaNacimiento": "1988-10-09"
}
