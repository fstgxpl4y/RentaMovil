import java.util.ArrayList;
import java.util.Locale;

public class RentaCarcachas {
    private ArrayList<Vehiculo> flota;
    private double ingresosAcumulados;

    public RentaCarcachas() {
        flota = new ArrayList<>();
        ingresosAcumulados = 0;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null || buscarPorPlaca(vehiculo.getPlaca()) != null) { return false; }
        flota.add(vehiculo);
        return true;
    }

    public Vehiculo buscarPorPlaca(String placa) {
        if (placa == null) { return null; }
        for (Vehiculo vehiculo : flota) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa.trim())) { return vehiculo; }
        }
        return null;
    }

    public double cotizar(String placa, int dias) {
        Vehiculo vehiculo = buscarPorPlaca(placa);
        if (vehiculo == null) { throw new IllegalArgumentException("La placa no existe."); }
        double total = vehiculo.calcularCosto(dias);
        if (!Double.isFinite(total)) {
            throw new IllegalArgumentException("El monto es demasiado grande.");
        }
        return total;
    }

    public boolean confirmarAlquiler(String placa, int dias) {
        Vehiculo vehiculo = buscarPorPlaca(placa);
        if (vehiculo == null || !vehiculo.isDisponible() || dias <= 0) { return false; }
        double total = cotizar(placa, dias);
        if (!Double.isFinite(ingresosAcumulados + total)) {
            throw new IllegalArgumentException("El ingreso acumulado sería demasiado grande.");
        }
        if (!vehiculo.marcarComoAlquilado()) { return false; }
        ingresosAcumulados += total;
        return true;
    }

    public boolean registrarDevolucion(String placa) {
        Vehiculo vehiculo = buscarPorPlaca(placa);
        return vehiculo != null && vehiculo.registrarDevolucion();
    }

    public void mostrarFlota() {
        if (flota.isEmpty()) { System.out.println("No hay vehículos registrados."); }
        for (Vehiculo vehiculo : flota) {
            System.out.println(vehiculo.obtenerDescripcion());
        }
    }

    public void mostrarReporte() {
        String[] tipos = {"Automóvil", "Motocicleta", "Camioneta de carga"};
        int totalDisponibles = 0;
        for (String tipo : tipos) {
            int disponibles = 0;
            int alquilados = 0;
            for (Vehiculo vehiculo : flota) {
                if (vehiculo.obtenerTipo().equals(tipo)) {
                    if (vehiculo.isDisponible()) { disponibles++; }
                    else { alquilados++; }
                }
            }
            totalDisponibles += disponibles;
            System.out.println(tipo + " | Total: " + (disponibles + alquilados)
                    + " | Disponibles: " + disponibles + " | Alquilados: " + alquilados);
        }
        System.out.println("Vehículos registrados: " + flota.size());
        System.out.println("Disponibles: " + totalDisponibles);
        System.out.println("Alquilados: " + (flota.size() - totalDisponibles));
        System.out.printf(Locale.US, "Ingresos acumulados: Q%.2f%n", ingresosAcumulados);
    }

    public double getIngresosAcumulados() { return ingresosAcumulados; }
    public int getCantidadVehiculos() { return flota.size(); }
}
