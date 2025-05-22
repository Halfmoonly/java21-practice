package cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/5/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Server {


    public static void main(String[] args) {
        Set<String> platformSet = new HashSet<>();
        new Thread(() -> {
            try {
                Thread.sleep(20000);
                System.out.println("另外有一个线程睡眠20秒后打印出Set的大小，则可以看出这些虚拟线程实际上用了多少个平台线程: "+platformSet.size());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            Thread.Builder.OfVirtual clientThreadBuilder = Thread.ofVirtual().name("client", 1);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientThreadBuilder.start(() -> {
                    String platformName = Thread.currentThread().toString().split("@")[1];
                    platformSet.add(platformName);
                    try (
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    ) {
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            System.out.println(inputLine + "（from:" + Thread.currentThread() + "）");
                            out.println(inputLine);
                        }
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Exception caught when trying to listen on port 999");
            System.err.printf(e.getMessage());
        }
    }
}
