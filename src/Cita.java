class Cita {
    private int id;
    private String fechaHora;
    private String motivo;
    private int doctorId;
    private int pacienteId;

    public Cita(int id, String fechaHora, String motivo, int doctorId, int pacienteId) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.doctorId = doctorId;
        this.pacienteId = pacienteId;
    }

    public int getId() {
        return id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    @Override
    public String toString() {
        return "Cita [id=" + id + ", fechaHora=" + fechaHora + ", motivo=" + motivo
                + ", doctorId=" + doctorId + ", pacienteId=" + pacienteId;
}
}