# RentaMovil

Aplicación de consola del Ejercicio 4. Requiere JDK 8 o posterior.

## Compilar y ejecutar

Abre una terminal dentro de la carpeta que contiene los seis archivos Java:

```bash
javac -encoding UTF-8 *.java
java Main
```

El menú permite registrar vehículos, consultar la flota, cotizar, confirmar alquileres, devolver vehículos y consultar el reporte. La opción 7 termina el programa. Usa punto para escribir decimales.

## Datos de demostración y resultados esperados

Todos comienzan disponibles y los ingresos comienzan en Q0.00.

| Placa | Característica | Tarifa diaria | Costo por 3 días |
|---|---|---:|---:|
| P001ABC | Automóvil manual | Q200.00 | Q600.00 |
| P002ABC | Automóvil automático | Q200.00 | Q750.00 |
| M001ABC | Motocicleta de 250 cc | Q100.00 | Q300.00 |
| M002ABC | Motocicleta de 321 cc | Q100.00 | Q375.00 |
| C001ABC | Camioneta de 1.5 toneladas | Q200.00 | Q1050.00 |
| C002ABC | Camioneta de 2 toneladas | Q250.00 | Q1350.00 |

## Comprobación manual pendiente

1. Cotizar los seis vehículos por tres días y comparar con la tabla. Los ingresos deben seguir en cero.
2. Iniciar un alquiler de C001ABC por tres días y responder n. Debe seguir disponible y los ingresos en cero.
3. Repetir y responder s. Debe quedar alquilado y los ingresos en Q1050.00.
4. Intentar alquilarlo nuevamente. Debe rechazarse sin cambiar los ingresos.
5. Cotizarlo estando ocupado. Debe permitirse sin cambiar los ingresos.
6. Devolverlo. Debe quedar disponible y los ingresos mantenerse en Q1050.00.
7. Volver a devolverlo. Debe rechazarse sin cambios.
8. Intentar registrar p001abc. Debe rechazarse por placa repetida.
9. Probar una placa inexistente, una placa vacía, días negativos, cero y texto en entradas numéricas.
10. Consultar el reporte inicial: seis registrados, seis disponibles, cero alquilados y dos por categoría.

Estos son resultados esperados, no evidencia de ejecución. El entorno de preparación no tenía disponible el compilador javac.

## Ajustes del UML

- Vehiculo es abstracta. calcularCosto(int dias): double y obtenerTipo(): String son abstractos.
- Automovil, Motocicleta y CamionetaCarga heredan de Vehiculo: triángulo vacío en Vehiculo.
- RentaCarcachas tiene una asociación navegable hacia Vehiculo, con multiplicidades 1 y 0..*.
- Main tiene una asociación navegable hacia RentaCarcachas: conserva empresa como atributo estático, con una sola instancia.
- Main depende de las tres clases concretas porque crea sus objetos. Representar estas dependencias con flechas discontinuas es opcional si se omiten dependencias de creación en el diagrama simplificado.
- disponible es boolean. Los atributos de Main y todos sus métodos son static (subrayados en UML).
- Los métodos del programa son públicos. Los atributos son privados. Los constructores no llevan tipo de retorno.
- En el análisis, cambiar el nombre de la clase administradora RentaMovil por RentaCarcachas para mantener coherencia con el UML y el código.
# RentaMovil