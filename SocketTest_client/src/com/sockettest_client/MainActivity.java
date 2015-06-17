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
            // ����socket����ָ����������ip��ַ���ͷ����������Ķ˿ں�
            // �ͻ�����new��ʱ�򣬾ͷ������������󣬷������˾ͻ���д��������������û�п���������ô
            // ��ʱ��ͻ��Ҳ�������������ͬʱ�׳��쳣==��java.net.ConnectException: Connection
            // refused: connect
			
			 System.out.print("try   connected");
            s1 = new Socket("159.226.176.125", 5555);
            System.out.print("connected");
            //�������
            OutputStream os = s1.getOutputStream();
            //��װ�����
            DataOutputStream dos = new DataOutputStream(os);
            //��������
            InputStream is = s1.getInputStream();
            //��װ������
            DataInputStream dis = new DataInputStream(is);
            //��ȡ����������
            InputStreamReader isr = new InputStreamReader(System.in);
            //��װ����������
            BufferedReader br = new BufferedReader(isr);

            String info;
            while (true) {
                //�ͻ����ȶ�ȡ����������Ϣ
                info = "asdasd";
                //����д�뵽��������
                dos.writeUTF(info);
                //����ͻ����Լ�˵��bye���������Ի�
                if (info.equals("bye"))
                    break;
             
            }
            //�ر���Ӧ�����������������socket����
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
