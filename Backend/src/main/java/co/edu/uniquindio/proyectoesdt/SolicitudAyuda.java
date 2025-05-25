package co.edu.uniquindio.proyectoesdt;

public class SolicitudAyuda extends Publicacion {
    private boolean activa;
    private Prioridad prioridad;

    public SolicitudAyuda(String titulo, String tema, Usuario autor, Contenido contenido, boolean activa,
                          Prioridad prioridad) {
        super(titulo, tema, autor, TipoPublicacion.SOLICITUD_AYUDA, contenido);
        if (prioridad == null) {
            throw new IllegalArgumentException("La prioridad no puede ser nula.");
        }
        this.activa = activa;
        this.prioridad = prioridad;
    }

    public int compareTo(SolicitudAyuda s) {
        return this.prioridad.getValor().compareTo(s.prioridad.getValor());
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        if(prioridad == null) {
            throw new IllegalArgumentException("Valor nulo al tratar de cambiar la prioridad de la solicitud de ayuda."
            );
        }

        this.prioridad = prioridad;
    }
}