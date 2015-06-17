package com.Liu.toycarv3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class SocketClient {
	//=================================================
	private SocketChannel socketChannel = null;
	private String sendbuf;
	//=================================================
	public double Longitude = 1;
	public double Latitude = 1;
	
	//=================================================
	public boolean mRun = false;
	
	
	public SocketChannel SetsockerChannel(SocketChannel in)
	{
		System.out.println("Try0.75");
		socketChannel = in;
		return socketChannel;
	}
	
	protected void finalize()
    {
		try {
			if(socketChannel.isOpen())
			socketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   		// add something....................
    }
	
	//==============================================================
	//=========================================================
		public class SocketThread extends Thread {
			public void run() {
				while(mRun){
			        StringBuilder sbcmd = new StringBuilder();
			        sbcmd.append("X");
			        sbcmd.append(""+ String.valueOf(Longitude));
			        sbcmd.append("Y");
			        sbcmd.append(""+ String.valueOf(Latitude));
			        sbcmd.append("E\r\n");
			        
			       // System.out.println("again:"+new String(sbcmd));
			        byte[] sendBytes = new String(sbcmd).getBytes();
			        try {
						socketChannel.write(ByteBuffer.wrap(sendBytes));				
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        
			        try {
						Thread.sleep(50);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
		private SocketThread thread;
		
		public void onStart(){
			mRun = true;
			if(thread==null){
	        	thread = new SocketThread();
	        }
	        thread.start();    	
		}
		public void onStop(){
			
			mRun = false;

			/*if(thread!=null){
	        	boolean retry = true;
		        while(retry) {
		            try {
		                thread.join();
		                retry = false;
		            } catch (InterruptedException e) {}
		        }
		        thread = null;
	        }*/
		}


}
