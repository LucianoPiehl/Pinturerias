# Documentacion del Proyecto Pinturerias

## 1. Vision general

El sistema gestiona una red de pinturerias con una arquitectura multibase:

- Base general:
  administra sucursales, catalogos globales, productos globales, stock central y pedidos de distribucion.
- Base de sucursal:
  administra productos propios, ventas, stock local y control local de productos globales.

La integracion entre ambos contextos se basa en dos ideas:

- Los productos globales no se duplican en las sucursales.
- Las sucursales guardan solo el identificador del producto global y el stock/precio local que les corresponde.

## 2. Contextos del sistema

### 2.1. Contexto general

Representa la operacion central de la empresa.

Responsabilidades:

- alta y mantenimiento de sucursales
- administracion de catalogos generales
- administracion de productos globales
- administracion del stock central
- creacion y finalizacion de pedidos de distribucion hacia sucursales

### 2.2. Contexto sucursal

Representa la operacion de una sucursal particular.

Responsabilidades:

- alta y mantenimiento de productos propios de sucursal
- consulta del catalogo combinado de pinturas
- administracion del control local de productos globales
- ventas directas a clientes finales

## 3. Reglas de negocio principales

### 3.1. Productos

- Un producto global vive solo en la base general.
- Un producto propio de sucursal vive solo en la base de esa sucursal.
- Una sucursal puede vender en el mismo pedido productos globales y productos propios.

### 3.2. Stock

- El stock central vive en la base general.
- El stock local vive en cada base de sucursal.
- Al finalizar un pedido general:
  se descuenta stock central y se acredita stock local.
- Al finalizar un pedido de sucursal:
  se descuenta stock local.

### 3.3. Pedidos

El sistema usa la misma estructura de pedido en ambos contextos:

- En general:
  un pedido representa un envio hacia una sucursal.
- En sucursal:
  un pedido representa una venta al cliente final.

Estados soportados:

- `INICIADO`
- `ACEPTADO`
- `FINALIZADO`

## 4. Tenant y seleccion de sucursal

Los endpoints de sucursal usan la ruta:

`/api/sucursal/{sucursalId}/...`

Reglas:

- La URL define la sucursal efectiva.
- Si se envia `X-Tenant-Id`, debe coincidir con el codigo real de la sucursal.
- Si el header no coincide, el backend responde `400`.

Recomendacion:

- para endpoints de sucursal, no enviar `X-Tenant-Id` salvo que realmente lo necesites.

## 5. Endpoints

### 5.1. Sucursales

Base:

`/api/general/sucursales`

#### GET `/api/general/sucursales`

Teoria:
obtiene todas las sucursales registradas en la base general.

Request:
sin body.

#### POST `/api/general/sucursales`

Teoria:
crea una sucursal, inicializa su base y la registra como tenant.

Body esperado:

```json
{
  "codigo": "suc-006",
  "nombre": "Sucursal Nueva",
  "username": "root",
  "password": "",
  "habilitada": true
}
```

#### PUT `/api/general/sucursales/{id}`

Teoria:
actualiza los datos de una sucursal existente.

#### DELETE `/api/general/sucursales/{id}`

Teoria:
elimina una sucursal del registro general y la quita del registro de tenants en memoria.

### 5.2. Catalogos generales

#### Tipo de pintura

Base:

`/api/general/tipo-pintura`

Endpoints:

- `GET /api/general/tipo-pintura`
- `GET /api/general/tipo-pintura/{id}`
- `POST /api/general/tipo-pintura`
- `PUT /api/general/tipo-pintura/{id}`
- `DELETE /api/general/tipo-pintura/{id}`

Body ejemplo:

```json
{
  "porcentajeAumento": 12.5,
  "rendimientoMT2": 35.0
}
```

#### Color base

Base:

`/api/general/color-base`

Endpoints:

- `GET /api/general/color-base`
- `GET /api/general/color-base/{id}`
- `POST /api/general/color-base`
- `PUT /api/general/color-base/{id}`
- `DELETE /api/general/color-base/{id}`

Body ejemplo:

```json
{
  "nombre": "Blanco",
  "formula": "BASE-W",
  "porcentajeAumento": 0.0
}
```

#### Tamano de envase

Base:

`/api/general/tamano-envase`

Endpoints:

- `GET /api/general/tamano-envase`
- `GET /api/general/tamano-envase/{id}`
- `POST /api/general/tamano-envase`
- `PUT /api/general/tamano-envase/{id}`
- `DELETE /api/general/tamano-envase/{id}`

Body ejemplo:

```json
{
  "nombre": "4L",
  "capacidad": 4.0,
  "porcentajeAumento": 10.0
}
```

### 5.3. Productos generales

Base:

`/api/general/productos`

#### GET `/api/general/productos/otro`

Teoria:
lista productos generales no pintura.

Dato actual en MySQL:

- producto `1`: `Rodillo Global`

#### POST `/api/general/productos/otro`

Body esperado:

```json
{
  "nombre": "Brocha Estandar",
  "descripcion": "Brocha 15cm",
  "precioFinal": 3000,
  "marca": "Brochista",
  "etiquetas": ["Brochas", "Herramientas"],
  "tipo": "OTRO"
}
```

#### PUT `/api/general/productos/otro/{id}`

Teoria:
actualiza un producto general no pintura.

#### DELETE `/api/general/productos/otro/{id}`

Teoria:
elimina un producto general no pintura.

#### GET `/api/general/productos/pintura`

Teoria:
lista pinturas generales.

Estado actual:

- actualmente no hay pinturas cargadas.

#### POST `/api/general/productos/pintura`

Body tecnico:
el backend espera un `ProductoPinturaDTO` con objetos anidados reales de `tipoPintura`, `colorBase` y `tamanoEnv`.

Ejemplo orientativo:

```json
{
  "nombre": "Latex Interior",
  "descripcion": "Pintura lavable",
  "precioFinal": 8500,
  "marca": "Sherwin",
  "etiquetas": ["blanco", "mate"],
  "tipo": "PINTURA",
  "tipoPintura": {
    "id": 1
  },
  "colorBase": {
    "id": 1
  },
  "tamanoEnv": {
    "id": 1
  }
}
```

#### PUT `/api/general/productos/pintura/{id}`

Teoria:
actualiza una pintura general.

#### DELETE `/api/general/productos/pintura/{id}`

Teoria:
elimina una pintura general.

### 5.4. Stock central

Base:

`/api/general/stock`

#### GET `/api/general/stock`

Teoria:
lista todos los registros de stock central.

#### GET `/api/general/stock/{productoId}`

Teoria:
obtiene el stock central de un producto global.

Dato actual en MySQL:

- producto `1` tiene stock central `15`

#### POST `/api/general/stock/{productoId}?stock=20`

Teoria:
crea o reemplaza el stock central de un producto global.

### 5.5. Pedidos generales

Base:

`/api/general/pedidos`

#### GET `/api/general/pedidos`

Teoria:
lista los pedidos de distribucion de la base general.

Dato actual en MySQL:

- pedido `1`: `Envio Rodillo a Sucursal 2`, estado `FINALIZADO`

#### GET `/api/general/pedidos/{id}`

Teoria:
obtiene un pedido puntual de distribucion.

#### POST `/api/general/pedidos`

Teoria:
crea un pedido para enviar productos desde el centro a una sucursal.

Body esperado:

```json
{
  "mail": "central@pinturerias.com",
  "telefono": "111111",
  "nombre": "Envio Rodillo a Sucursal 2",
  "sucursalDestinoId": 2,
  "productos": [
    {
      "idProducto": 1,
      "contextoProducto": "GENERAL",
      "cantidad": 5,
      "precioUnitario": 2500
    }
  ]
}
```

#### PUT `/api/general/pedidos/{id}/estado?estado=FINALIZADO`

Teoria:
al finalizar:

- descuenta stock central
- acredita stock local en la sucursal destino

### 5.6. Productos propios de sucursal

Base:

`/api/sucursal/{sucursalId}/productos`

#### GET `/api/sucursal/2/productos/otro`

Teoria:
lista productos propios de la sucursal.

Dato actual en MySQL para sucursal `2`:

- producto `1`: `Martillo Sucursal 2`

#### POST `/api/sucursal/2/productos/otro`

Teoria:
crea un producto propio en la base de la sucursal.

Body esperado:

```json
{
  "nombre": "Martillo Sucursal 2",
  "descripcion": "Martillo de acero",
  "precioFinal": 5000,
  "marca": "Truper",
  "etiquetas": ["herramienta", "metal"],
  "tipo": "OTRO",
  "stock": 10
}
```

#### DELETE `/api/sucursal/2/productos/otro/{id}`

Teoria:
elimina un producto propio de la sucursal.

### 5.7. Catalogo de pinturas en sucursal

#### GET `/api/sucursal/{sucursalId}/productos/pintura`

Teoria:
devuelve el catalogo combinado de pinturas generales enriquecido con stock/precio local de la sucursal.

### 5.8. Control local de productos globales

Base:

`/api/sucursal/{sucursalId}/productos-general`

#### GET `/api/sucursal/2/productos-general`

Teoria:
lista los registros de control local para productos globales en esa sucursal.

Dato actual en MySQL:

- producto global `1` tiene stock local `3` y precio `2500.0`

#### GET `/api/sucursal/2/productos-general/1`

Teoria:
obtiene el control local de un producto global en esa sucursal.

#### POST `/api/sucursal/2/productos-general/1?precio=2500&stock=3`

Teoria:
crea o actualiza el control local de precio y stock de un producto global.

#### DELETE `/api/sucursal/2/productos-general/1`

Teoria:
elimina el control local de ese producto global.

### 5.9. Pedidos de sucursal

Base:

`/api/sucursal/{sucursalId}/pedidos`

#### GET `/api/sucursal/2/pedidos`

Teoria:
lista las ventas de la sucursal.

Dato actual en MySQL:

- pedido `1`: `Venta Rodillo Cliente`, estado `FINALIZADO`

#### GET `/api/sucursal/2/pedidos/1`

Teoria:
obtiene una venta puntual.

#### POST `/api/sucursal/2/pedidos`

Teoria:
crea una venta de sucursal.

Body ejemplo usando un producto global:

```json
{
  "nombre": "Venta Rodillo Cliente",
  "mail": "cliente@test.com",
  "telefono": "222222",
  "nombreCliente": "Cliente Demo",
  "nombreVendedor": "Vendedor Demo",
  "medioPago": "EFECTIVO",
  "productos": [
    {
      "idProducto": 1,
      "contextoProducto": "GENERAL",
      "cantidad": 2,
      "precioUnitario": 2500
    }
  ]
}
```

#### PUT `/api/sucursal/2/pedidos/1/estado?estado=FINALIZADO`

Teoria:
al finalizar:

- si el item es `GENERAL`, descuenta del control local `productos-general`
- si el item es `SUCURSAL`, descuenta del stock del producto propio

## 6. Datos cargados actualmente

### Base general

- sucursal `1`: `suc-004`
- sucursal `2`: `suc-005`
- producto general `1`: `Rodillo Global`
- stock central producto `1`: `15`
- pedido general `1`: `Envio Rodillo a Sucursal 2`

### Sucursal 2

- producto propio `1`: `Martillo Sucursal 2`
- control local producto global `1`: stock `3`, precio `2500.0`
- pedido sucursal `1`: `Venta Rodillo Cliente`

## 7. Sobre las migraciones SQL

### Pregunta: "¿No se puede volver a tener solo dos V1__init.sql?"

Respuesta corta:
no, no conviene si queres conservar las bases y datos actuales.

Motivo tecnico:

- Flyway registra cada migracion aplicada en la tabla `flyway_schema_history`.
- Si una base ya paso por cambios estructurales, esos cambios deben seguir representados como nuevas versiones.
- Si compactaras todo otra vez en solo `V1__init.sql`, deberias:
  o resetear completamente las bases,
  o reconstruir el historial de Flyway,
  o recrear una base nueva desde cero.

Conclusion:

- con datos actuales: multiple archivos versionados son necesarios
- en un proyecto nuevo o con reinicio total de BD: si, podrias consolidar nuevamente

## 8. Recomendaciones de uso

- Para endpoints de sucursal, usar la URL con el `id` correcto de sucursal.
- No enviar `X-Tenant-Id` salvo que realmente lo necesites.
- Para pruebas de distribucion:
  primero crear o setear stock central, luego crear pedido general, luego finalizarlo.
- Para pruebas de venta:
  primero asegurarse de que exista stock local en la sucursal.
