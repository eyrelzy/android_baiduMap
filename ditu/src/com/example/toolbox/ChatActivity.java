package com.example.toolbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ditu.R;

public class ChatActivity extends Activity 
{
	private EditText etPort = null ;//�˿ں�
	private EditText etIP = null ;//IP
	private Button bChangePort = null ;//�ı�˿ڵ��Ǹ���ť
	private EditText etHistory = null ;//��ʷ�ı���
	private EditText etMessage = null ;//Ҫ�����Ǹ�
	private Button bSendMessage = null ;//������Ϣ�İ�ť
	String sPort = null ;//�˿�
	
	String sIP = null ;//IP
	MyHandler handler;
	String sMessage = null ;
	Socket s = null ;
	ServerSocket ss = null ;
	int CLIENTPORT = 5555 ;
	
	
	  SharedPreferences settings;
	  SharedPreferences.Editor localEditor ;
	  
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_main);etPort = (EditText)findViewById(R.id.Port) ;
		
		handler = new MyHandler();
		ServerSocketThread serverSocketThread=new ServerSocketThread();
		serverSocketThread.start();
		
		etPort = (EditText)findViewById(R.id.Port) ;
		etIP = (EditText)findViewById(R.id.IP) ;
		bChangePort = (Button)findViewById(R.id.ChangePort) ;
		bChangePort.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sIP = etIP.getText().toString() ;
				sPort = etPort.getText().toString() ;
				localEditor.putString("SERVERIP", sIP);
				localEditor.putInt("SERVERPORT",Integer.parseInt(sPort));				
				localEditor.commit();
			}
		}) ;
		
		etHistory = (EditText)findViewById(R.id.History) ;
		etMessage = (EditText)findViewById(R.id.Message) ;
		bSendMessage = (Button)findViewById(R.id.SendMessage) ;
		bSendMessage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sMessage = etMessage.getText().toString() ;
				if(!sMessage.equals(""))
					new ClientThread().start() ;
			}
		}) ;
		settings = this.getSharedPreferences("SettingXML", 0);
       localEditor = settings.edit();
       sIP = settings.getString("SERVERIP", "192.168.191.2");
       sPort= ""+settings.getInt("SERVERPORT",8888);

	       etIP.setText(sIP) ;
	       etPort.setText(sPort) ;
		//	sPort = etPort.getText().toString() ;
		//	sIP = etIP.getText().toString() ;
		
	}

	public String getTime() {
		SimpleDateFormat time = new SimpleDateFormat("yy��MM��dd�� HH:mm:ss");// �������ڸ�ʽ
		String timestring = time.format(new Date());
		return timestring;
	}
	public class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(ChatActivity.this, getTime() + "��������ʧ��", 1000).show();
				String serr = "\r\n" +getTime()+"����ʧ��" ;
				SpannableString sserr = new SpannableString(serr) ;
				sserr.setSpan(new ForegroundColorSpan(Color.RED), 0, serr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) ;
				
				etHistory.append(sserr);
				etMessage.setText("");
				break;
			case 2:
				String message = (String) msg.obj;
				System.out.println("Handler:" + message);  
				String str = "\n"+"���Լ���"
						+ getTime() + "\n"
						+ sMessage ;
				SpannableString sstr = new SpannableString(str) ;
				sstr.setSpan(new ForegroundColorSpan(Color.argb(255, 13, 144, 10)), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) ;
				/*etHistory.append("\n"+"���Լ���"
						+ getTime() + "\n"
						+ sMessage);*/
				etHistory.append(sstr) ;
				etMessage.setText("");
				break;
			case 3:
				String receiveMessage = (String) msg.obj;
				String t=getTime();
				String str1 = "\n"+"�����㣺"
						+t + "\n"
						+receiveMessage ;
				SpannableString sstr1 = new SpannableString(str1) ;
				sstr1.setSpan(new ForegroundColorSpan(Color.argb(255, 35, 155, 183)), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) ;
				
				etHistory.append(sstr1);
				break;				
			}			
		}
	}
	class ServerSocketThread extends Thread {
		@Override
		public void run(){
			try{
				ServerSocket serverSocket = new ServerSocket();
				Log.e(getLocalIpAddress(), "server start");
				serverSocket.bind(new InetSocketAddress(getLocalIpAddress(),8889));
				while (true)
				{
					Socket socket = serverSocket.accept();
					Log.e("CCQQ", "accept");
					new SocketThread(socket).start();
				} 
			}
			catch(Exception e)
			{
				Log.v("CCQQ", "error");
			}
		}
	}
	class SocketThread extends Thread{
		private Socket socket;
	    public SocketThread(Socket socket) {
				super();
				this.socket = socket;
			}
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				Log.v("MyQQ", "socket accept");
				InputStream is;
				try {
					is = socket.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String message=br.readLine();
					/*server receive message to main thread*/
						Message receivemessage = new Message();
						receivemessage.obj = message;
						receivemessage.what = 3;
						handler.sendMessage(receivemessage);
					String temp="";
					while((temp=br.readLine())!=null)
					{
						message+=temp;
					}					
				    is.close();
				    isr.close();
				} 
				catch (IOException e) 
				{
				}
			}
		}
	private OnClickListener oclChangePort = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			sPort = etPort.getText().toString() ;
			sIP = etIP.getText().toString() ;
		}
	};
	private OnClickListener oclSendMessage = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			new ClientThread().start() ;
		}
	};
	public String getLocalIpAddress() {  
		WifiManager wm=(WifiManager)getSystemService(Context.WIFI_SERVICE);  
    //���Wifi״̬    
    if(!wm.isWifiEnabled())  
        wm.setWifiEnabled(true);  
    WifiInfo wi=wm.getConnectionInfo();  
    //��ȡ32λ����IP��ַ    
    int ipAdd=wi.getIpAddress();  
    //�����͵�ַת���ɡ�*.*.*.*����ַ    
    String ip=intToIp(ipAdd);  
    return ip;  
    }  
	private String intToIp(int i) {  
        return (i & 0xFF ) + "." +  
        	    ((i >> 8 ) & 0xFF) + "." +  
        	    ((i >> 16 ) & 0xFF) + "." +  
        	    ( i >> 24 & 0xFF) ;  
        	}   
	private class ClientThread extends Thread
	{
		public void run()
		{
			sMessage = etMessage.getText().toString() ;
			PrintWriter out;
			try {
				s = new Socket(sIP,Integer.parseInt(sPort));
				out = new PrintWriter(s.getOutputStream());
				out.println(sMessage);
				out.flush();				
				Message message = handler.obtainMessage();
				message.obj = sMessage;
				message.what = 2;
				handler.sendMessage(message);
				out.close();
				out = null ;
				s.close();
				s = null ;
				Log.e("runwanle", "run wanle");
				
			} catch (NumberFormatException e) {
				Log.e(sPort, sIP);
				Message receivemessage = new Message();
				receivemessage.obj = sMessage;
				receivemessage.what = 1;
				handler.sendMessage(receivemessage);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				Log.e(sPort, sIP);
				Message receivemessage = new Message();
				receivemessage.obj = sMessage;
				receivemessage.what = 1;
				handler.sendMessage(receivemessage);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				Log.e(sPort, sIP);
				Message receivemessage = new Message();
				receivemessage.obj = sMessage;
				receivemessage.what = 1;
				handler.sendMessage(receivemessage);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		return true;
	}

}
