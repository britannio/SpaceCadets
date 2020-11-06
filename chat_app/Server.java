import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        int portNumber = 1337;

        if (args.length >= 1) {
            portNumber = Integer.parseInt(args[0]);
        }

        // Used to connect to client sockets
        ServerSocket serverSocket = new ServerSocket(portNumber);
        // Waits for a client to connect and returns it's socket
        Socket clientSocket = serverSocket.accept();
        System.out.println("Connected on port" + clientSocket.getLocalPort() );
        BufferedReader request = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter response = new PrintWriter(clientSocket.getOutputStream(), true);

        String inputLine;
        while ((inputLine = request.readLine()) != null) {
            System.out.println("Client sent: " + inputLine);
            response.println("server says:" + inputLine);
        }

    }
}