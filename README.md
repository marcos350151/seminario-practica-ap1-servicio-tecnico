# Prototipo AP2 - Servicio técnico de equipos informáticos

Ampliación incremental del repositorio AP1 de Marcos Waigel. Incluye las funciones previas y el flujo de diagnóstico, presupuesto, decisión, reparación, repuestos, entrega e historial. Es una aplicación de consola académica, no un sistema productivo.

## Requisitos

- JDK 17 o superior y una IDE de Java (por ejemplo, IntelliJ IDEA Community, Eclipse o NetBeans).
- MySQL 8.x y MySQL Connector/J compatible, descargado desde MySQL.
- Base de datos local configurada. Es mejor crear un usuario específico con permisos limitados para el prototipo.

## Preparación

1. En una base nueva, ejecutar `schema.sql` y luego `ap2_migracion.sql`. La migración se ejecuta una sola vez; si ya existe el esquema AP1, ejecutar solo la migración.
2. Registrar un técnico de prueba: el campo `clave_hash` de AP1 es obligatorio, aunque **no se implementa autenticación en este prototipo**. No poner una contraseña real. Ejemplo de dato no autenticable:

```sql
INSERT INTO usuario(nombre, usuario, clave_hash, rol)
VALUES ('Técnico de prueba', 'tecnico_demo', 'PROTOTIPO_SIN_LOGIN', 'TECNICO');
SELECT id_usuario FROM usuario WHERE usuario='tecnico_demo';
```

3. Configurar `ST_DB_URL`, `ST_DB_USER` y `ST_DB_PASS`. La URL por defecto es `jdbc:mysql://localhost:3306/servicio_tecnico?useSSL=false&serverTimezone=UTC`. Para un servidor externo usar TLS con verificación de certificado y credenciales propias.
4. En la IDE agregar MySQL Connector/J como biblioteca y ejecutar `Main`. Alternativamente:

```bat
javac -encoding UTF-8 -d out src\*.java
java -cp "out;C:\ruta\mysql-connector-j.jar" Main
```

En Linux o macOS el separador del classpath es `:`.

## Flujo mínimo

Alta de cliente (1), ingreso de equipo y orden (3), diagnóstico (5), presupuesto (6), aceptación (7), finalizar reparación (8), uso opcional de repuesto (9), entrega (10), historial (11). El rechazo cancela la orden. El registro de repuestos exige catálogo y stock cargados; por ejemplo:

```sql
INSERT INTO repuesto(nombre, stock, precio) VALUES ('SSD 240 GB', 4, 30000.00);
```

`consultas.sql` contiene alta, consultas y eliminación controlada de datos de ejemplo. Revisar los identificadores de prueba antes de ejecutar su bloque de borrado. Las claves foráneas impiden eliminar una orden que tenga registros dependientes.

## Verificación y límites

La compilación de `src/*.java` fue verificada con el compilador de Java 17. No se ejecutó una integración con MySQL en el entorno de elaboración: se debe probar localmente el flujo y los casos de error de la matriz del informe. La aplicación no autentica usuarios ni permite actualizar clientes, reutilizar un equipo ya registrado o programar un presupuesto con varios ítems. `clave_hash` queda solo como campo de esquema del TP1 y el usuario de demostración no habilita inicio de sesión.

Este paquete debe incorporarse al repositorio del TP1 antes de entregar el enlace de GitHub como versión completa del AP2.
