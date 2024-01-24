package Intercambio;

import java.io.IOException;
import java.net.*;

public class CustomUDPServer {
    private static final int ECHOMAX = 255;

    public static void main(String[] args) {
        byte[] sendData;
        DatagramSocket datagramSocket = null;

        try {
            datagramSocket = new DatagramSocket(8889);

            DatagramPacket receivePacket = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
            DatagramPacket sendPacket;

            while (true) {
                datagramSocket.receive(receivePacket);

                // Obtenemos la cadena del paquete recibido
                String receivedText = new String(receivePacket.getData()).trim();
                System.out.println("Recibiendo del Cliente en " + receivePacket.getSocketAddress());
                System.out.println("El Cliente dice: " + receivedText);

                // Convertimos la cadena a mayúsculas
                String upperCaseText = receivedText.toUpperCase();
                sendData = upperCaseText.getBytes();

                // Enviamos la respuesta al cliente
                sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getSocketAddress());
                datagramSocket.send(sendPacket);

                // Salimos del bucle si recibimos un asterisco del cliente
                if ("*".equals(receivedText)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }
}