package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;


public class SocketServer 
{
    public static void main( String[] args ) throws IOException {
        // Check arguments
        if (args.length < 1) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s port%n", SocketServer.class.getName());
            return;
        }

        // Convert port from String to int
        int port = Integer.parseInt(args[0]);

        // Create server socket
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.printf("Server accepting connections on port %d %n", port);

        // wait for and then accept client connection
        // a socket is created to handle the created connection
        Socket clientSocket = serverSocket.accept();
        System.out.printf("Connected to client %s on port %d %n",
        clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());

        // Create stream to receive data from client
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        //Obtem o tempo do cliente, como string
        String tempoDoClienteString;
        tempoDoClienteString = in.readLine();
        
        //Obtem o tempo do servidor
        long tempoDoServidor = System.currentTimeMillis();
                
        System.out.println("O tempo recebido: " + tempoDoClienteString + "\n" + "O tempo do servidor: " + tempoDoServidor + "\n");
        
        //Converte o tempo do cliente, de string para long.
        //Faco isto apos obter o tempo do servidor, para que caso a conversao demore muito tempo, esta nao afecte o tempo obtido no servidor
        long tempoDoCliente = Long.parseLong(tempoDoClienteString);
        
        //Abre o canal para responder ao cliente
        DataOutputStream outc = new DataOutputStream( clientSocket.getOutputStream() );

    	tempoDoServidor = tempoDoServidor + 50;
    	
        if( (tempoDoCliente < (tempoDoServidor + 50)) || (tempoDoCliente > (tempoDoServidor - 50) )){
        	//Resposta     
            outc.writeBytes("0\n");	        	
        }else{
        	//Resposta
        	outc.writeBytes("-1\n");        	
        }
        
        // Receive data until client closes the connection
        String response;
        response = in.readLine();
        
        //  while ((response = in.readLine()) )//!= null)
        System.out.printf("Received message with content: '%s'%n", response);
      
       
        
        // Close connection to current client
        clientSocket.close();
        System.out.println("Closed connection with client");

        // Close server socket
        serverSocket.close();
        System.out.println("Closed server socket");
    }
}

