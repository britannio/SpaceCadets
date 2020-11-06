import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        int port = 1337;

        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }

        // Client socket used to send and receive messages
        Socket socket = new Socket("localhost", port);
        // Socket output
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        // Socket input
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Gets user input from the terminal
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        while ((userInput = stdIn.readLine()) != null) {
            // add the terminal input to the socket output stream
            out.println(userInput);
            // print the server response
            System.out.println("response: " + in.readLine());
        }

    }

}
