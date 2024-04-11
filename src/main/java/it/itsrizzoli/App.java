package it.itsrizzoli;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class App {
    static int portNumber = 5555;
    // ip:172.16.49.169



    public static void main(String[] args) {

        System.out.println("Server started!");

        ServerSocket serverSocket = null;

        serverSocket = getServerSocket();

        Socket clientSocket = null;
        clientSocket = getSocket(serverSocket);

        BufferedReader in = null;
        in = getReader(clientSocket);
        PrintWriter out = null;

        out = getWriter(out, clientSocket);


        ArrayList<Hotel> hotel = new ArrayList<>();
        hotel.add(new Hotel("Rambagh Palace", 400.50, true));
        hotel.add(new Hotel("Ozen Reserve Bolifushi", 220.25, false));
        hotel.add(new Hotel("Colline de France", 140.60, false));
        hotel.add(new Hotel("Shangri-La The Shard",700.80, true));
        hotel.add(new Hotel("The Ritz-Carlton", 334.23, true));


        extracted(in, out, hotel);

    }


    private static void extracted(BufferedReader in, PrintWriter out, ArrayList<Hotel> hotels) {
        String s;

        try {
            while ((s = in.readLine()) != null) {
                s = s.trim();
                int scelta = 0;
                if (s.equalsIgnoreCase("all")) {
                    scelta = 1;
                } else if (s.equalsIgnoreCase("sorted_by_name")) {
                    scelta = 2;
                } else if (s.equalsIgnoreCase("with_spa")) {
                    scelta = 3;
                } else {
                    scelta = 4;
                }


                int nCaratteri = 0;
                for (Hotel hotel : hotels) {
                    if (nCaratteri < hotel.getNome().length())
                        nCaratteri = hotel.getNome().length();
                }
                String formatSpecifier = "%-" + nCaratteri + "s";


                switch (scelta) {

                    case 1:
                        for (Hotel struttura : hotels) {
                            out.println("Nome: " + String.format(formatSpecifier, struttura.getNome(), struttura.getCosto()) +
                                    " Costo: " + String.format("%,.2f", struttura.getCosto()) +
                                    " spa: " + struttura.getSpa());
                        }
                        break;

                    case 2:
                        Comparator<Hotel> nameComparator = Comparator.comparing(Hotel::getNome);
                        Collections.sort(hotels, nameComparator);

                        for (Hotel struttura : hotels) {
                            out.println("Nome: " + String.format(formatSpecifier, struttura.getNome(), "%,.2f", struttura.getCosto()) +
                                    " Costo: " + String.format("%,.2f", struttura.getCosto()) +
                                    " spa: " + struttura.getSpa());                    }
                        break;

                    case 3:
                        for (Hotel struttura : hotels) {
                            if (struttura.spa) {
                                out.println("Nome: " + String.format(formatSpecifier, struttura.getNome(), "%,.2f", struttura.getCosto()) +
                                        " Costo: " + String.format("%,.2f", struttura.getCosto()) +
                                        " spa: " + struttura.getSpa());
                            }
                        }
                        break;

                    case 4:
                        out.println("Errore, comando non valido");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PrintWriter getWriter(PrintWriter out, Socket clientSocket) {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    private static BufferedReader getReader(Socket clientSocket) {
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return in;
    }

    private static Socket getSocket(ServerSocket serverSocket) {
        Socket clientSocket;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clientSocket;
    }

    private static ServerSocket getServerSocket() {
        ServerSocket serverSocket = null;
        while (true) {
            try {
                serverSocket = new ServerSocket(portNumber);
                return serverSocket;
            } catch (IOException e) {
                portNumber++;
            }
            System.out.printf("Porta: " + portNumber);
            return serverSocket;
        }
    }
}