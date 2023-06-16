import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final String DOCTORES_FILE = "doctores.csv";
    private static final String PACIENTES_FILE = "pacientes.csv";
    private static final String CITAS_FILE = "citas.csv";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private List<Doctor> doctores;
    private List<Paciente> pacientes;
    private List<Cita> citas;
    private Scanner scanner;

    public Principal() {
        doctores = new ArrayList<>();
        pacientes = new ArrayList<>();
        citas = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void ejecutar() {
        System.out.println("Bienvenido al sistema de administración de citas del consultorio clínico.");

        // Autenticación del administrador
        if (!autenticarAdministrador()) {
            System.out.println("Credenciales de administrador incorrectas. Intenta de nuevo.");
            ejecutar();
            return;
        }

        cargarDatos();

        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    altaDoctor();
                    break;
                case 2:
                    altaPaciente();
                    break;
                case 3:
                    crearCita();
                    break;
                case 4:
                    listarCitas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        } while (opcion != 0);

        scanner.close();
        System.out.println("¡Hasta luego!");
    }

    private boolean autenticarAdministrador() {
        System.out.println("Por favor, ingrese sus credenciales de administrador.");

        System.out.print("Usuario: ");
        String username = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }

    private void cargarDatos() {
        cargarDoctores();
        cargarPacientes();
        cargarCitas();
    }

    private void cargarDoctores() {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String nombre = data[1];
                String especialidad = data[2];
                Doctor doctor = new Doctor(id, nombre, especialidad);
                doctores.add(doctor);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los doctores: " + e.getMessage());
        }
    }

    private void cargarPacientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(PACIENTES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String nombre = data[1];
                Paciente paciente = new Paciente(id, nombre);
                pacientes.add(paciente);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los pacientes: " + e.getMessage());
        }
    }

    private void cargarCitas() {
        try (BufferedReader br = new BufferedReader(new FileReader(CITAS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String fechaHora = data[1];
                String motivo = data[2];
                int doctorId = Integer.parseInt(data[3]);
                int pacienteId = Integer.parseInt(data[4]);
                Cita cita = new Cita(id, fechaHora, motivo, doctorId, pacienteId);
                citas.add(cita);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar las citas: " + e.getMessage());
        }
    }

    private void altaDoctor() {
        System.out.println("Ingrese los datos del doctor.");

        int id = generarNuevoIdDoctor(); // Generar nuevo identificador único

        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        Doctor doctor = new Doctor(id, nombre, especialidad);
        doctores.add(doctor);
        System.out.println("Doctor registrado exitosamente.");
        guardarDoctores();
    }
    private int generarNuevoIdDoctor() {
        if (doctores.isEmpty()) {
            return 1;
        } else {
            int ultimoId = doctores.get(doctores.size() - 1).getId();
            return ultimoId + 1;
        }
    }

    private void altaPaciente() {
        System.out.println("Ingrese los datos del paciente.");

        int id = generarNuevoIdPaciente();

        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();

        Paciente paciente = new Paciente(id, nombre);
        pacientes.add(paciente);
        System.out.println("Paciente registrado exitosamente.");
        guardarPacientes();
    }
    private int generarNuevoIdPaciente() {
        if (pacientes.isEmpty()) {
            return 1;
        } else {
            int ultimoId = pacientes.get(pacientes.size() - 1).getId();
            return ultimoId + 1;
        }
    }
    private void crearCita() {
        System.out.println("Ingrese los datos de la cita.");

        int id = generarNuevoIdCrearCita();

        System.out.print("Fecha y hora (dd/mm/aaaa hh:mm): ");
        String fechaHora = scanner.nextLine();

        System.out.print("Motivo de la cita: ");
        String motivo = scanner.nextLine();

        System.out.println("Seleccione el doctor:");
        listarDoctores();

        System.out.print("Identificador del doctor: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Seleccione el paciente:");
        listarPacientes();

        System.out.print("Identificador del paciente: ");
        int pacienteId = scanner.nextInt();
        scanner.nextLine();

        Cita cita = new Cita(id, fechaHora, motivo, doctorId, pacienteId);
        citas.add(cita);
        System.out.println("Cita creada exitosamente.");
        guardarCitas();
    }
    private int generarNuevoIdCrearCita() {
        if (citas.isEmpty()) {
            return 1;
        } else {
            int ultimoId = citas.get(citas.size() - 1).getId();
            return ultimoId + 1;
        }
    }

    private void listarCitas() {
        System.out.println("Lista de citas:");

        for (Cita cita : citas) {
            System.out.println(cita);
        }
    }

    private void listarDoctores() {
        for (Doctor doctor : doctores) {
            System.out.println(doctor);
        }
    }

    private void listarPacientes() {
        for (Paciente paciente : pacientes) {
            System.out.println(paciente);
        }
    }

    private void guardarDoctores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTORES_FILE))) {
            for (Doctor doctor : doctores) {
                bw.write(doctor.getId() + "," + doctor.getNombre() + "," + doctor.getEspecialidad());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los doctores: " + e.getMessage());
        }
    }

    private void guardarPacientes() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PACIENTES_FILE))) {
            for (Paciente paciente : pacientes) {
                bw.write(paciente.getId() + "," + paciente.getNombre());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los pacientes: " + e.getMessage());
        }
    }

    private void guardarCitas() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CITAS_FILE))) {
            for (Cita cita : citas) {
                bw.write(cita.getId() + "," + cita.getFechaHora() + "," + cita.getMotivo()
                        + "," + cita.getDoctorId() + "," + cita.getPacienteId());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las citas: " + e.getMessage());
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("Seleccione una opción:");
        System.out.println("1. Dar de alta doctores");
        System.out.println("2. Dar de alta pacientes");
        System.out.println("3. Crear una cita con fecha y hora");
        System.out.println("4. Listar citas");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }
    public static void main(String[] args) {
        Principal consultorio = new Principal();
        consultorio.ejecutar();
    }
}







