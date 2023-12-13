package ejemplo1tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPejemplo1Servidor {

	public static void main(String[] arg) throws IOException {
		int numeroPuerto = 6000; // Puerto
		ServerSocket servidor = new ServerSocket(numeroPuerto);

		System.out.println("Esperando al cliente.....");
		Socket clienteConectado = servidor.accept();

		// CREO FLUJO DE ENTRADA DEL CLIENTE
		DataInputStream flujoEntrada = new DataInputStream(clienteConectado.getInputStream());

		// CREO FLUJO DE SALIDA AL CLIENTE
		DataOutputStream flujoDeSalida = new DataOutputStream(clienteConectado.getOutputStream());

		String mensajeCliente;
		do {
			// EL CLIENTE ME ENVÍA UN MENSAJE
			mensajeCliente = flujoEntrada.readUTF();
			System.out.println("Recibiendo del CLIENTE: \n\t" + mensajeCliente);

			// Convertir a mayúsculas y enviar de vuelta al cliente
			String mensajeServidor = mensajeCliente.toUpperCase();
			flujoDeSalida.writeUTF(mensajeServidor);

		} while (!"*".equals(mensajeCliente));

		// CERRAR STREAMS Y SOCKET
		flujoEntrada.close();
		flujoDeSalida.close();
		clienteConectado.close();
		servidor.close();
		System.out.println("Servidor cerrado.");
	}
}
