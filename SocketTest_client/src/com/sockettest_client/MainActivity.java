package com.sockettest_client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	private Socket s1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 System.out.println("Begin");
		try {
            // 创建socket对象，指定服务器的ip地址，和服务器监听的端口号
            // 客户端在new的时候，就发出了连接请求，服务器端就会进行处理，如果服务器端没有开启服务，那么
            // 这时候就会找不到服务器，并同时抛出异常==》java.net.ConnectException: Connection
            // refused: connect
			
			 System.out.print("try   connected");
            s1 = new Socket("159.226.176.125", 5555);
            System.out.print("connected");
            //打开输出流
            OutputStream os = s1.getOutputStream();
            //封装输出流
            DataOutputStream dos = new DataOutputStream(os);
            //打开输入流
            InputStream is = s1.getInputStream();
            //封装输入流
            DataInputStream dis = new DataInputStream(is);
            //读取键盘输入流
            InputStreamReader isr = new InputStreamReader(System.in);
            //封装键盘输入流
            BufferedReader br = new BufferedReader(isr);

            String info;
            while (true) {
                //客户端先读取键盘输入信息
                info = "asdasd";
                //把他写入到服务器方
                dos.writeUTF(info);
                //如果客户端自己说：bye，即结束对话
                if (info.equals("bye"))
                    break;
             
            }
            //关闭相应的输入流，输出流，socket对象
            dis.close();
            dos.close();
            s1.close();
        } catch (IOException e) {
        	System.out.print("Kick out==================");
            e.printStackTrace();
        }
    
	        
        
     //   send2PTZ.println("I'm here");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
