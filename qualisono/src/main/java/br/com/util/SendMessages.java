package br.com.util;

// SendMessage.java – Sample application.
//
// This application shows you the basic procedure for sending messages.
// You will find how to send synchronous and asynchronous messages.
//
// For asynchronous dispatch, the example application sets a callback
// notification, to see what’s happened with messages.

//package examples.modem;

//import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

public class SendMessages{
//	public void doIt(String telefone, String mensagem) throws Exception{
//		Service srv = null;
//		OutboundMessage msg;
//		OutboundNotification outboundNotification = new OutboundNotification();
//		System.out.println("Example: Send message from a serial gsm modem.");
//		System.out.println(Library.getLibraryDescription());
//		System.out.println("Version: " + Library.getLibraryVersion());
////		srv = new Service();
//		SerialModemGateway gateway = new SerialModemGateway("modem.com8", "COM8", 57600, "ZTE", "MF110");
//		gateway.setInbound(true);
//		gateway.setOutbound(true);
//		gateway.setSimPin("0000");
//		// srv.setOutboundNotification(outboundNotification);
//		srv.addGateway(gateway);
//		srv.startService();
//		System.out.println();
//		System.out.println("Modem Information:");
//		System.out.println(" Manufacturer: " + gateway.getManufacturer());
//		System.out.println(" Model: " + gateway.getModel());
//		System.out.println(" Serial No: " + gateway.getSerialNo());
//		System.out.println(" SIM IMSI: " + gateway.getImsi());
//		System.out.println(" Signal Level: " + gateway.getSignalLevel() + "%");
//		System.out.println(" Battery Level: " + gateway.getBatteryLevel() + "%");
//		System.out.println();
//		// Send a message synchronously.
//		msg = new OutboundMessage(telefone, mensagem);
//		
//		// gateway.setSmscNumber("123456");
//		
//		// gateway.getATHandler().setStorageLocations("SMMTME");
//		srv.sendMessage(msg);
//		System.out.println(msg);
//		
//		// Or, send out a WAP SI message.
//		//OutboundWapSIMessage wapMsg = new OutboundWapSIMessage("+306948494037", new URL("https://mail.google.com/"), "Visit GMail now!");
//		//srv.sendMessage(wapMsg);
//		//System.out.println(wapMsg);
//		// You can also queue some asynchronous messages to see how the callbacks
//		// are called…
//		//msg = new OutboundMessage("+309999999999", "Wrong number!");
//		//msg.setPriority(OutboundMessage.Priorities.LOW);
//		//srv.queueMessage(msg, gateway.getGatewayId());
//		//msg = new OutboundMessage("+308888888888", "Wrong number!");
//		//msg.setPriority(OutboundMessage.Priorities.HIGH);
//		// srv.queueMessage(msg, gateway.getGatewayId());
//		// System.out.println("Now Sleeping – Hit to terminate.");
//		// System.in.read();
//		srv.stopService();
//	}
//		
//	public class OutboundNotification implements IOutboundMessageNotification{
//		public void process(String gatewayId, OutboundMessage msg){
//			System.out.println("Outbound handler called from Gateway: " + gatewayId);
//			System.out.println(msg);
//		}
//		
//		public void process(AGateway ag, OutboundMessage om){
//			throw new UnsupportedOperationException("Not supported yet.");
//		}
//	}
//		
//	public static void main(String args[]){
//		SendMessages app = new SendMessages();
//		try{
////			app.doIt();
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
}