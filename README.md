# Parking App

## Contexto

Hemos sido contratados por una empresa que se dedica a la administración de estacionamientos, 
la cual necesita un sistema que le permita llevar un control de los vehículos que ingresan y salen de sus instalaciones. 
Para ello, nos han solicitado que desarrollemos una aplicación que les permita registrar los vehículos que ingresan y 
salen del estacionamiento, así como también llevar un registro de los vehículos que se encuentran actualmente estacionados.

El estacionamiento está ubicado en un edificio que cuenta con departamentos y con una zona comercial, por lo que el mismo
ofrece cocheras para la venta en el primer piso, cocheras para alquileres mensuales en el subsuelo y cocheras para uso 
temporal en la planta baja. A la vez, cada cochera cuenta con un número de identificación único en todo el estacionamiento
y esta destinado a un tipo de vehículo en particular (MOTO, AUTO o CAMIONETA); un vehículo de un tipo no puede ocupar 
una cochera destinada a otro tipo de vehículo o que no es del mismo tipo.

El estacionamiento cuenta con 3 esquemas de precios:
1. **Venta de cocheras**: El precio de la cochera se establece en base al tipo de vehículo y se cobra por única vez.
2. **Alquiler mensual de cocheras**: El precio del alquiler se establece en base al tipo de vehículo y se cobra por la cantidad de meses.
3. **Uso temporal de cocheras**: El precio del uso temporal se establece en base al tipo de vehículo y se cobra por la cantidad de horas.
   Para el calculo de las horas se toman la cantidad las horas iniciadas sin tolerancia. por ejemplo si un vehículo esta
   55 minutos o 5 minutos paga una hora; si un vehículo esta 65 minutos paga 2 horas.

## Requerimientos

La aplicación debe permitir:
1. Buscar un vehículo por su patente en una API externa.
2. Registrar la venta de una cochera **(YA RESUELTO)**.
2. Registrar el alquiler mensual de una cochera.
3. Registrar el ingreso de un vehículo a una cochera temporal.
4. Registrar el egreso de un vehículo de una cochera temporal.

## Examen

Para facilitar el desarrollo de la aplicación, las entidades, DTOs, modelos, controladores y repository ya han sido creados.
El alumno debe:
1. Implementar el método getVehicleById(String id) de la clase VehicleRestClient.
2. Implementar el método rentLot(Long lotId, Vehicle vehicle, Integer months, LocalDateTime entryDateTime) de la clase LotServiceImpl.
3. Implementar el método entryLot(Long lotId, Vehicle vehicle, LocalDateTime entryDateTime) de la clase LotServiceImpl.
4. Implementar el método exitTrace(Long lotId, LocalDateTime exitDateTime) de la clase LotServiceImpl.
5. Testear toda la clase LotServiceImpl. **La se medirá no solo cobertura sino calidad de los tests.**
6. Crear el Dockerfile para la aplicación.
7. Crear el docker-compose.yml para la aplicación con los siguientes componentes:
    - Aplicación BE (La que estamos desarrollando)
    - Aplicación de la API externa (Será provista durante el examen)
    - Aplicación FE (Será provista durante el examen)
    - Base de datos MySql