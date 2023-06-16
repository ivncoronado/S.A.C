class Doctor {
    private int id;
    private String nombre;
    private String especialidad;

    public Doctor(int id, String nombre, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String toString() {
        return "Doctor [id=" + id + ", nombre=" + nombre + ", especialidad=" + especialidad + "]";
    }
}