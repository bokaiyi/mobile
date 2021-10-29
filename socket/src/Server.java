import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    //define relative params,port ,store Socket list,ServerSocket object
    //and thread pool
    private static final int PORT = 12345;
    private List<Socket> mList = new ArrayList<Socket>();
    private ServerSocket server = null;
    private ExecutorService myExecutorService = null;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("服务器已经运行在" + getServAddr() + ":12345 ，等待客户端发送数据");

            //make thread pool
            myExecutorService = Executors.newCachedThreadPool();
            Socket client = null;
            while (true) {
                client = server.accept();
                mList.add(client);
                myExecutorService.execute(new Service(client));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Service implements Runnable {
        private Socket socket;
        private BufferedReader in = null;
        private String msg = "";

        public Service(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                msg = "client:" + this.socket.getInetAddress() + ":" +
                        String.valueOf(this.socket.getPort()) + "~is coming";
                System.out.println(msg);
                this.sendmsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            try {
                while (true) {
                    if ((msg = in.readLine()) != null) {
                        if (msg.equals("exit server")) {
                            System.out.println("~~~~~~~~~~~~~");
                            mList.remove(socket);
                            in.close();
                            msg = "client:" + this.socket.getInetAddress() + ":" + String.valueOf(this.socket.getPort())
                                    + " exit...";
                            System.out.println(msg);
                            socket.close();
                            this.sendmsg();
                            break;
                        } else {
//                            msg = this.socket.getInetAddress() + ":" + String.valueOf(this.socket.getPort()) + "   说: " + msg;
                            System.out.println(this.socket.getInetAddress() + ":" + String.valueOf(this.socket.getPort()) + "   说: " + msg);
                            this.sendmsg();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //send message to each client 
        public void sendmsg() {
            int num = mList.size();
            for (int index = 0; index < num; index++) {
                Socket mSocket = mList.get(index);
                PrintWriter pout = null;
                try {
                    pout = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8")), true);
                    pout.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //  get server address
    public static String getServAddr() {

        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();

                // get rid of loop back, virtual and not used intefaces
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip != null) {
                        // ipv4
                        if (ip instanceof Inet4Address) {
                            if (ip.getHostAddress().startsWith("192") || ip.getHostAddress().startsWith("10")
                                    || ip.getHostAddress().startsWith("172") || ip.getHostAddress().startsWith("169")) {
                                return ip.getHostAddress();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Error when getting host ip address" + e.getMessage());
        }

        return "";
    }
}