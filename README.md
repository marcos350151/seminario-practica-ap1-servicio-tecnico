# Sistema de Gestión para un Servicio Técnico de Equipos Informáticos

Prototipo académico correspondiente a la Actividad Práctica 1 (AP1) de Seminario de Práctica Informática.

## Tecnologías
- Java 17 o superior
- MySQL 8.x
- JDBC / MySQL Connector/J

## Alcance implementado
- Alta y listado de clientes.
- Registro de un equipo asociado a un cliente.
- Creación de una orden de servicio.
- Listado de órdenes.
- Actualización del estado de una orden.
- Esquema MySQL preparado para diagnóstico, presupuesto, reparación y repuestos.

## Preparación de la base de datos
1. Abrir MySQL Workbench o el cliente MySQL utilizado.
2. Ejecutar `schema.sql`.
3. La base creada se denomina `servicio_tecnico`.

## Configuración de conexión
La aplicación utiliza estas variables de entorno de forma opcional:

- `ST_DB_URL` (por defecto: `jdbc:mysql://localhost:3306/servicio_tecnico?useSSL=false&serverTimezone=UTC`)
- `ST_DB_USER` (por defecto: `root`)
- `ST_DB_PASS` (por defecto: vacío)

## Compilación
Desde la raíz del proyecto:

```bash
javac -d out src/*.java
```

## Ejecución
Se requiere MySQL Connector/J en el classpath.

Ejemplo en Windows:

```bat
java -cp "out;C:\ruta\mysql-connector-j.jar" Main
```

Ejemplo en Linux/macOS:

```bash
java -cp "out:/ruta/mysql-connector-j.jar" Main
```

## Estructura

```text
.
├── README.md
├── schema.sql
└── src
    ├── ClienteDAO.java
    ├── Database.java
    ├── Main.java
    └── OrdenDAO.java
```

El prototipo es deliberadamente acotado. Las funcionalidades de diagnóstico, presupuesto, reparación, repuestos y autenticación se contemplan en el modelo y pueden incorporarse incrementalmente en etapas posteriores.
