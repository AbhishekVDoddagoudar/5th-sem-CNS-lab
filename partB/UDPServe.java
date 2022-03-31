import java.io.*;
import java.net.*;
public class UDPServe
{
	public static void main(String args[]) throws Exception
	{
		DatagramSocket serverSocket = new DatagramSocket(6788);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true)
		{
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();

			System.out.println("Enter message to echo:");
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			String Sentence = inFromUser.readLine();
			sendData = Sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,port);
			serverSocket.send(sendPacket);

		}
	}
}
