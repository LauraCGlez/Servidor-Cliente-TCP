package Intercambio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class CustomUDPClient {
    private static final int TIMEOUT = 3000;
    private static final int MAXTRIES = 5;

    public static void main(String[] args) {
        DatagramSocket datagramSocket = null;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.setSoTimeout(TIMEOUT);

            while (true) {
                // Leemos la entrada del usuario desde la consola
                System.out.print("Ingrese un texto (o * para salir): ");
                String inputText = userInput.readLine();

                if ("*".equals(inputText)) {
                    break;  // Salimos del bucle si el usuario ingresa *
                }

                // Convertimos la entrada a bytes y creamos el DatagramPacket
                byte[] data = inputText.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length,
                        InetAddress.getLocalHost(), 8889);
                DatagramPacket receivePacket = new DatagramPacket(new byte[data.length], data.length);

                int tries = 0;
                boolean receivedResponse = false;

                do {
                    datagramSocket.send(sendPacket);

                    try {
                        datagramSocket.receive(receivePacket);

                        if (!receivePacket.getAddress().equals(sendPacket.getAddress())) {
                            throw new IOException("Received packet from an unknown source");
                        }

                        receivedResponse = true;
                    } catch (IOException e) {
                        tries += 1;
                        System.out.println("Timed out, " + (MAXTRIES - tries) + " more tries...");
                    }
                } while (!receivedResponse && (tries < MAXTRIES));

                if (receivedResponse) {
                    System.out.println("Servidor dijo: " + new String(receivePacket.getData()));
                } else {
                    System.out.println("No hubo respuesta del servidor.");
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