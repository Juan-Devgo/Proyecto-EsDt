package co.edu.uniquindio.proyectoesdt.Patrones;

public class GestorComandosModerador {
    private ComandoModerador comando;

    public void setComando(ComandoModerador comando) {
        this.comando = comando;
    }

    public void ejecutarComando() {
        if (comando != null) {
            comando.ejecutar();
        }
    }
}
