import java.util.Locale;

public abstract class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponible;

    public Vehiculo(String placa, String marca, String modelo, double tarifaDiaria) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía.");
        }
        if (marca == null || marca.trim().isEmpty() || modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca y el modelo son obligatorios.");
        }
        if (!Double.isFinite(tarifaDiaria) || tarifaDiaria <= 0) {
            throw new IllegalArgumentException("La tarifa debe ser mayor que cero y finita.");
        }
        this.placa = placa.trim().toUpperCase(Locale.ROOT);
        this.marca = marca.trim();
        this.modelo = modelo.trim();
        this.tarifaDiaria = tarifaDiaria;
        disponible = true;
    }

    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getTarifaDiaria() { return tarifaDiaria; }
    public boolean isDisponible() { return disponible; }

    public boolean marcarComoAlquilado() {
        if (!disponible) { return false; }
        disponible = false;
        return true;
    }

    public boolean registrarDevolucion() {
        if (disponible) { return false; }
        disponible = true;
        return true;
    }

    public abstract double calcularCosto(int dias);
    public abstract String obtenerTipo();

    public String obtenerDescripcion() {
        String estado = disponible ? "Disponible" : "Alquilado";
        return String.format(Locale.US, "%s | %s | %s %s | Tarifa: Q%.2f | %s",
                obtenerTipo(), placa, marca, modelo, tarifaDiaria, estado);
    }
}
