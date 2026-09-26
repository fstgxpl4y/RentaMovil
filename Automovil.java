public class Automovil extends Vehiculo {
    private int cantidadPasajeros;
    private boolean automatico;

    public Automovil(String placa, String marca, String modelo, double tarifaDiaria,
                     int cantidadPasajeros, boolean automatico) {
        super(placa, marca, modelo, tarifaDiaria);
        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser positiva.");
        }
        this.cantidadPasajeros = cantidadPasajeros;
        this.automatico = automatico;
    }

    public int getCantidadPasajeros() { return cantidadPasajeros; }
    public boolean isAutomatico() { return automatico; }

    @Override
    public double calcularCosto(int dias) {
        if (dias <= 0) { throw new IllegalArgumentException("Los días deben ser positivos."); }
        double tarifa = getTarifaDiaria();
        if (automatico) { tarifa += 50; }
        return tarifa * dias;
    }

    @Override
    public String obtenerTipo() { return "Automóvil"; }

    @Override
    public String obtenerDescripcion() {
        String transmision = automatico ? "Automática" : "Manual";
        return super.obtenerDescripcion() + " | Pasajeros: " + cantidadPasajeros
                + " | Transmisión: " + transmision;
    }
}
