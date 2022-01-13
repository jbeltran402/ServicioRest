# ServicioRest
Aplicativo creado en **Java** el cual hace uso de **httpserver** y **Gson**,  quien recibe como parámetros los atributos del objeto empleado al ser invocado mediante el método POST.

Posteriormente, verifica  los formatos de las fechas y a su vez  válida que los atributos no se encuentren vacíos, aunado a esto, el aplicativo comprueba que el empleado sea mayor de edad. Una vez realizado este proceso, se almacena la información en una base de datos **Mysql** de nombre **servicio_rest**.

------------

### Instalación

**1.** Clonar el repositorio.
`git clone https://github.com/jbeltran402/ServicioRest.git`

**2.** Abrir el código con un IDE Java (Para este caso se usó Intelij IDEA).

**3.** Crear Base de datos.
```sql
CREATE DATABASE servicio_rest;

CREATE TABLE servicio_rest.usuarios (
	id INT auto_increment NOT NULL,
	nombres varchar(100) NOT NULL,
	apellidos varchar(100) NOT NULL,
	tipoDocumento varchar(100) NOT NULL,
	numerodocumento varchar(100) NOT NULL,
	fecha_nacimiento DATE NOT NULL,
	fecha_vinculación DATE NOT NULL,
	cargo varchar(100) NOT NULL,
	salario DOUBLE NOT NULL,
	CONSTRAINT usuarios_pkey PRIMARY KEY (id)
);
```

**4.** Cambiar usuario y contraseña en archivo **config/ConexionDB.java**

**5.** Correr el aplicativo desde el main.

**6.** Para verificar el funcionamiento se puede realizar una consulta de ping y el método GET.

`http://localhost:10100/api/ping`
```json
{
    "success": true
}
```

------------

### Recepción de parámetros

El aplicativo captura los datos a través del método POST.

`http://localhost:10100/api/ping`

```json
[
    {
        "nombres": "maria",
        "apellidos": "Gomez",
        "tipoDocumento": "CC",
        "numerodocumento": "1285722153",
        "fechaNacimiento": "2003-05-08",
        "fechaVinculacion": "2020-05-18",
        "cargo": "developer Junior",
        "salario": 5000000
    }
]
```

Dado el caso de que los datos enviados no cumplan las validaciones, el aplicativo retorna un objeto en estructura **JSON**, presentando la siguiente estructura:

```json
{
    "success": true,
    "mensaje": "DATOS ERRONEOS"
}
```
Cuando los datos suministrados son correctos,  el aplicativo retorna un objeto en estructura JSON, en los que se encuentran:
- Tiempo de Vinculación a la compañía (años, meses)
- Edad actual del empleado (años, meses y días)

```json
{
    "success": true,
    "vinculacion": {
        "anio": 1,
        "mes": 7
    },
    "edad": {
        "anio": 18,
        "mes": 8,
        "dia": 5
    }
}
```