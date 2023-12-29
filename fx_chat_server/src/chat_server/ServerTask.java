package chat_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTask implements Runnable{

	// 현재 task에 연결된 client socket 정보
	Socket client;
	// display UI 정보가 있는 controller instance 정보 저장
	ServerController sc;
	
	// 연결된 Client에 출력할 스트림
	PrintWriter printer;
	// client에서 출력된 데이터를 입력받을 스트림
	BufferedReader reader;
	// 연결된 client의 nickName정보
	String nickName;
	// 연결된 client task end flag
	boolean isRun = true;
	
	public ServerTask(Socket client, ServerController sc) {
		this.client = client;
		this.sc = sc;
		
		try {
			OutputStream os = client.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter writer = new BufferedWriter(osw);
			printer = new PrintWriter(writer,true);	// auto flush = 자동 배출
			
			reader = new BufferedReader(
				new InputStreamReader(client.getInputStream())
			); 
		} catch (IOException e) {
			sc.printText("Client 연결 오류 : " + e.getMessage());
		}
		
	}

	// client 에서 출력된  발신 메세지를 수신 - receive
	@Override
	public void run() {
		sc.printText(client.getRemoteSocketAddress()+" recieve 시작");
		// receive Data 
		// code - 0 : 닉네임 전달
		// code - 1 : 전체 메세지 - 귓속말 
		
		while(isRun) {
			
			try {
				// client에서 발신 된 메세지 입력 받음
				String receiveData = reader.readLine();
				if(receiveData == null){
					break;
				}
				// receiveData
				// 0|nickName
				// 1|Message
				sc.printText(receiveData);
				String[] data = receiveData.split("\\|");
				
				// [0][nickName]
				// [1][Message]
				String code = data[0];
				String text = data[1];
				
				// code 번호로 기능 분류 
				switch(code) {
					case "0" :
						// 전달된 닉네임으로 연결된 client 정보를 hashtable에 등록
						
						// hashtable에 등록된 닉네임이 이미 key 값을 존재하는지 확인
						if(sc.clients.containsKey(text)) {
							// server textarea에 요청 정보 출력
							sc.printText(text+"-사용할 수 없는 닉네임 요청");
							this.printer.println(text+"는 사용할 수 없는 닉네임입니다.");
							// serverTask Thread 종료
							isRun = false;
							// serverTask에 연결된 socket 자원해제
							client.close();
							// switch 문 종료 end case "0"
							break;
						} // 닉네임 중복 체크 완료
						
						// 중복 되지 않는 닉네임을 필드에 저장
						this.nickName = text;
						// 연결된 client 목록에 등록
						sc.clients.put(text, printer);
						// client 접속자 목록 갱신
						String sendData = "";
						// hashtable key 묶음
						for(String name : sc.clients.keySet()) {
							sendData += name+",";							
						}
						// "0|name,name,name,name,... ";
						// "1|message";
						broadCast(0,sendData);
						broadCast(1,nickName+"님이 입장하셨습니다. 현재인원 : "+sc.clients.size());
						
						break; // end case "0"
					case "1" :	// 연결된 client에게 메세지 전달
						broadCast(1,nickName+" : " +text);
						break;
				}
			} catch (IOException e) {
				System.out.println("client 연결 종료 : " +e.getMessage());
				isRun = false;
			}	
		} // end while
		
		// 연결된 cleint와 수신 작업 종료 
		if(client != null && !client.isClosed()) {
			// serverTask 종료 시 - 연결된 socket된 자원 해제
			try {
				client.close();
			} catch (IOException e) {}		
		}			
		
		// 종료한 client 정보 삭제 및 사용자 목록 갱신
		sc.clients.remove(this.nickName);
		
		// 사용자 목록에서 종료된 사용자 제거 후 남아있는 사용자 목록 갱신
		String list = "";
		for (String name : sc.clients.keySet()) {
			list += name+",";
		}
		// 남아있는 모든 클라이언트에게 접속자 목록 갱신 데이터 발신 
		broadCast(0,list);
		String result = nickName+"님이 나가셨습니다. 방인원 : " + sc.clients.size();
		broadCast(1,result);
		sc.printText(result);
		
		// broadCast(1,nickName+"님이 나가셨습니다. 방인원 : "+sc.clients.size());
		
	} // end serverTask run()

	
	// 연결된 모든 client에게 메세지 전달
	// code - 0 : 접속자 목록 갱신
	// code - 1 : 메세지 출력 
	public void broadCast(int code, String message) {
		// sc.clients
		for(PrintWriter p : sc.clients.values()) {
			p.println(code+"|"+message);
		}
		
	}
}
