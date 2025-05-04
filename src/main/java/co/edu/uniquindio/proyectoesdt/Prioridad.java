package co.edu.uniquindio.proyectoesdt;

public enum Prioridad {
    MUY_ALTA(0),
    ALTA(1),
    MEDIA(2),
    BAJA(3);

    private final Integer valor;

    Prioridad(int valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }
}
