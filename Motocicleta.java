public class Motocicleta extends Vehiculo {
    private int cilindraje;

    public Motocicleta(String placa, String marca, String modelo, double tarifaDiaria, int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria);
        if (cilindraje <= 0) {
            throw new IllegalArgumentException("El cilindraje debe ser positivo.");
        }
        this.cilindraje = cilindraje;
    }

    public int getCilindraje() { return cilindraje; }

    @Override
    public double calcularCosto(int dias) {
        if (dias <= 0) { throw new IllegalArgumentException("Los días deben ser positivos."); }
        double total = getTarifaDiaria() * dias;
        if (cilindraje > 250) { total += 75; }
        return total;
    }

    @Override
    public String obtenerTipo() { return "Motocicleta"; }

    @Override
    public String obtenerDescripcion() {
        return super.obtenerDescripcion() + " | Cilindraje: " + cilindraje + " cc";
    }
}
