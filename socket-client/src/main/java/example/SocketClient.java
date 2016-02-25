package example;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;


public class SocketClient {

    public static void main( String[] args ) throws IOException {
        // Check arguments
        if (args.length < 3) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s host port file%n", SocketClient.class.getName());
            return;
        }

        String host = args[0];
        // Convert port from String to int
        int port = Integer.parseInt(args[1]);
        // Concatenate arguments using a string builder
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length-1) {
                sb.append(" ");
            }
        }
        String text = sb.toString();

        // Create client socket
        Socket socket = new Socket(host, port);
        System.out.printf("Connected to server %s on port %d %n", host, port);

        // Create stream to send data to server
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    	// Obtem o tempo em segundos
        long tempoActual = System.currentTimeMillis();
        System.out.println("Tempo do Cliente: " + tempoActual);
                        
        out.writeBytes(Long.toString(tempoActual));
        out.writeBytes("\n");
        
        
        DataInputStream in = new DataInputStream(socket.getInputStream());
        BufferedReader d = new BufferedReader(new InputStreamReader(in));
        
        //recebe a resposta
        String resposta;
        resposta = d.readLine();
        System.out.println("Recebemos a resposta do servidor: " + resposta);
        
        if(Integer.parseInt(resposta) == 0){
	        // Send text to server as bytes
	        out.writeBytes(text);
	        out.writeBytes("\n");
	        
	        System.out.println("Sent text: " + text);
        }else{
	        System.out.println("A timestamp do cliente em relação ao servidor, difere em mais de 50ms, por favor sincronize os relógios \n");

        	
        }

        // Close client socket
        socket.close();
        System.out.println("Connection closed");
    }
}

