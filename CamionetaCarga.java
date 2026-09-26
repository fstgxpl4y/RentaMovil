import java.util.Locale;

public class CamionetaCarga extends Vehiculo {
    private double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo, double tarifaDiaria,
                         double capacidadToneladas) {
        super(placa, marca, modelo, tarifaDiaria);
        if (!Double.isFinite(capacidadToneladas) || capacidadToneladas <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva y finita.");
        }
        this.capacidadToneladas = capacidadToneladas;
    }

    public double getCapacidadToneladas() { return capacidadToneladas; }

    @Override
    public double calcularCosto(int dias) {
        if (dias <= 0) { throw new IllegalArgumentException("Los días deben ser positivos."); }
        return (getTarifaDiaria() + 100 * capacidadToneladas) * dias;
    }

    @Override
    public String obtenerTipo() { return "Camioneta de carga"; }

    @Override
    public String obtenerDescripcion() {
        return super.obtenerDescripcion()
                + String.format(Locale.US, " | Capacidad: %.2f toneladas", capacidadToneladas);
    }
}
