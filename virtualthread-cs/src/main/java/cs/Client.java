package cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/5/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Client {


    public static void main(String[] args) throws InterruptedException {
        Thread.Builder.OfVirtual builder = Thread.ofVirtual().name("client", 1);
        for (int i = 0; i < 100000; i++) {
            builder.start(() -> {
                try (
                        Socket serverSocket = new Socket("localhost", 9999);
                        BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
                ) {
                    out.println("hello");
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                    }
                } catch (UnknownHostException e) {
                    System.err.println("Don't know about localhost");
                } catch (IOException e) {
                    System.err.println("Couldn't get I/O for the connection to localhost");
                }
            });
        }
        //主线程长时间睡眠避免程序直接结束。
        Thread.sleep(1000000000);
    }
}