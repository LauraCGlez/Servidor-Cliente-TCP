package ejemplo1tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPejemplo1Cliente {

	public static void main(String[] args) throws Exception {
		String Host = "localhost"; // Cambiar a la dirección del servidor
		int Puerto = 6000; // Puerto

		System.out.println("PROGRAMA CLIENTE INICIADO....");
		Socket Cliente = new Socket(Host, Puerto);

		// CREO FLUJO DE SALIDA AL SERVIDOR
		DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());

		// CREO FLUJO DE ENTRADA DEL SERVIDOR
		DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());

		Scanner scanner = new Scanner(System.in);
		String mensaje;

		do {
			// Capturar mensaje del usuario
			System.out.print("Ingrese un mensaje (o * para salir): ");
			mensaje = scanner.nextLine();

			// Enviar mensaje al servidor
			flujoSalida.writeUTF(mensaje);

			// Recibir y mostrar la respuesta del servidor
			String respuesta = flujoEntrada.readUTF();
			System.out.println("Respuesta del SERVIDOR: " + respuesta);

		} while (!"*".equals(mensaje));

		// CERRAR STREAMS Y SOCKET
		flujoSalida.close();
		flujoEntrada.close();
		Cliente.close();
		System.out.println("Cliente cerrado.");
	}
}

