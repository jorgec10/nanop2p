package es.um.redes.P2P.App;


import java.io.IOException;
import java.util.Scanner;

import es.um.redes.P2P.PeerTracker.Client.Reporter;
import es.um.redes.P2P.PeerTracker.Message.Message;
import es.um.redes.P2P.util.FileInfo;

public class Peer {
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Usage: java Peer <tracker_hostname> <local_shared_folder>");
			return;
		}
		String trackerHostname = args[0];
		String peerSharedFolder = args[1];
		Boolean continua = true;
		
		// Create client object
		Reporter client = new Reporter("PeerThread", peerSharedFolder, trackerHostname); 
		// Start client thread
		client.start();
		
		FileInfo[] localFile = FileInfo.loadFilesFromFolder(peerSharedFolder);
		

		while (continua) {
			Scanner scan = new Scanner(System.in);
			String entrada = scan.nextLine();
			String [] arg = entrada.split("\\s+");
			
			//Add seeds, query files, download, getseeds, removeseed
			switch (arg[0]) {
			case "addseeds":
				System.out.println((client.sendMsg(Message.OP_ADD_SEED, localFile)).toString());
				break;
			case "queryfiles":
				System.out.println((client.sendMsg(Message.OP_QUERY_FILES, localFile)).toString());
				break;
				
			case "exit":
				continua=false;
				System.out.println("Fin del proceso del peer");
				break;
			default:
				break;
			}
		}
		
		
		
		
		
	}
}
