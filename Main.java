import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static RentaCarcachas empresa = new RentaCarcachas();

    public static void main(String[] args) {
        cargarDatosIniciales();
        boolean continuar = true;
        while (continuar) {
            try {
                mostrarMenu();
                int opcion = leerEnteroPositivo("Opción: ");
                switch (opcion) {
                    case 1: registrarVehiculo(); break;
                    case 2: consultarFlota(); break;
                    case 3: cotizarAlquiler(); break;
                    case 4: alquilarVehiculo(); break;
                    case 5: devolverVehiculo(); break;
                    case 6: mostrarReporte(); break;
                    case 7: continuar = false; break;
                    default: System.out.println("Selecciona una opción del 1 al 7.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("Fin de la entrada.");
                continuar = false;
            }
        }
        scanner.close();
        System.out.println("Programa finalizado.");
    }

    public static void cargarDatosIniciales() {
        empresa.registrarVehiculo(new Automovil("P001ABC", "Toyota", "Corolla", 200, 5, false));
        empresa.registrarVehiculo(new Automovil("P002ABC", "Honda", "Civic", 200, 5, true));
        empresa.registrarVehiculo(new Motocicleta("M001ABC", "Honda", "CB250", 100, 250));
        empresa.registrarVehiculo(new Motocicleta("M002ABC", "Yamaha", "MT03", 100, 321));
        empresa.registrarVehiculo(new CamionetaCarga("C001ABC", "Kia", "K2700", 200, 1.5));
        empresa.registrarVehiculo(new CamionetaCarga("C002ABC", "Hyundai", "H100", 250, 2));
    }

    public static void mostrarMenu() {
        System.out.println("\nRENTACARCACHAS");
        System.out.println("1. Registrar vehículo");
        System.out.println("2. Consultar flota");
        System.out.println("3. Cotizar alquiler");
        System.out.println("4. Alquilar vehículo");
        System.out.println("5. Registrar devolución");
        System.out.println("6. Mostrar reporte");
        System.out.println("7. Salir");
    }

    public static void registrarVehiculo() {
        System.out.println("1. Automóvil  2. Motocicleta  3. Camioneta de carga");
        int tipo = leerEnteroPositivo("Categoría: ");
        if (tipo > 3) {
            System.out.println("Categoría inválida.");
            return;
        }
        System.out.print("Placa: ");
        String placa = scanner.nextLine().trim();
        if (placa.isEmpty()) {
            System.out.println("La placa no puede estar vacía.");
            return;
        }
        if (empresa.buscarPorPlaca(placa) != null) {
            System.out.println("La placa ya está registrada.");
            return;
        }
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        double tarifa = leerDoublePositivo("Tarifa diaria: Q");
        Vehiculo vehiculo;
        if (tipo == 1) {
            int pasajeros = leerEnteroPositivo("Cantidad de pasajeros: ");
            int transmision = leerEnteroPositivo("Transmisión: 1. Automática  2. Manual: ");
            while (transmision > 2) {
                transmision = leerEnteroPositivo("Escribe 1 o 2: ");
            }
            vehiculo = new Automovil(placa, marca, modelo, tarifa, pasajeros, transmision == 1);
        } else if (tipo == 2) {
            int cilindraje = leerEnteroPositivo("Cilindraje en cc: ");
            vehiculo = new Motocicleta(placa, marca, modelo, tarifa, cilindraje);
        } else {
            double capacidad = leerDoublePositivo("Capacidad en toneladas: ");
            vehiculo = new CamionetaCarga(placa, marca, modelo, tarifa, capacidad);
        }
        if (empresa.registrarVehiculo(vehiculo)) {
            System.out.println("Vehículo registrado y disponible.");
        } else {
            System.out.println("No se pudo registrar el vehículo.");
        }
    }

    public static void consultarFlota() { empresa.mostrarFlota(); }

    public static void cotizarAlquiler() {
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        Vehiculo vehiculo = empresa.buscarPorPlaca(placa);
        if (vehiculo == null) {
            System.out.println("La placa no existe.");
            return;
        }
        int dias = leerEnteroPositivo("Días de alquiler: ");
        double total = empresa.cotizar(placa, dias);
        System.out.println(vehiculo.obtenerDescripcion());
        System.out.println("Días: " + dias);
        System.out.printf(Locale.US, "Cotización: Q%.2f%n", total);
    }

    public static void alquilarVehiculo() {
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        Vehiculo vehiculo = empresa.buscarPorPlaca(placa);
        if (vehiculo == null) {
            System.out.println("La placa no existe.");
            return;
        }
        if (!vehiculo.isDisponible()) {
            System.out.println("El vehículo ya está alquilado.");
            return;
        }
        int dias = leerEnteroPositivo("Días de alquiler: ");
        double total = empresa.cotizar(placa, dias);
        System.out.println(vehiculo.obtenerDescripcion());
        System.out.printf(Locale.US, "Total por %d días: Q%.2f%n", dias, total);
        System.out.print("¿Confirmar alquiler? (s/n): ");
        String respuesta = scanner.nextLine().trim();
        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            System.out.print("Escribe s o n: ");
            respuesta = scanner.nextLine().trim();
        }
        if (respuesta.equalsIgnoreCase("n")) {
            System.out.println("Alquiler cancelado. No se hicieron cambios.");
            return;
        }
        if (empresa.confirmarAlquiler(placa, dias)) {
            System.out.println("Alquiler confirmado. Pago registrado.");
        } else {
            System.out.println("No se pudo confirmar el alquiler.");
        }
    }

    public static void devolverVehiculo() {
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        Vehiculo vehiculo = empresa.buscarPorPlaca(placa);
        if (vehiculo == null) {
            System.out.println("La placa no existe.");
        } else if (vehiculo.isDisponible()) {
            System.out.println("El vehículo ya está disponible; no se puede devolver.");
        } else if (empresa.registrarDevolucion(placa)) {
            System.out.println("Devolución registrada. El vehículo está disponible.");
        }
    }

    public static void mostrarReporte() { empresa.mostrarReporte(); }

    public static int leerEnteroPositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor > 0) { return valor; }
            } catch (NumberFormatException e) {
                System.out.println("Formato incorrecto.");
            }
            System.out.println("Ingresa un número entero mayor que cero.");
        }
    }

    public static double leerDoublePositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (Double.isFinite(valor) && valor > 0) { return valor; }
            } catch (NumberFormatException e) {
                System.out.println("Formato incorrecto.");
            }
            System.out.println("Ingresa un número mayor que cero. Usa punto para los decimales.");
        }
    }
}
