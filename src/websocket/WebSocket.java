package websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import po.Item;
import service.CommodityService;
import vo.Message;

@ServerEndpoint("/websocket/{clientname}")
public class WebSocket {
	private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
	private String clientName;
	private Session session;
	private CommodityService service = new CommodityService();

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam("clientname") String clientname, Session session) {
		this.clientName = clientname;
		this.session = session;
		clients.put(clientname, this);
		System.out.println(clientname + "连接成功");
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	@OnClose
	public void onClose() {
		if (this.clientName != null) {
			clients.remove(this.clientName);
			System.out.println(this.clientName + "退出了连接!");
		}
	}

	/**
	 * 收到客户端消息后调用的方法 根据instruction对象中的operation字段的数据决定调用何种方法，作何种处理
	 * 
	 * @param message 客户端发送过来的消息 Json化的instruction类1对象
	 * @param session 可选的参数
	 * @throws IOException
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException, NullPointerException {
		System.out.println(this.clientName + ">>:" + message);
		// 先解析出instruction字段，查看是何种指令，在根据此字段确定解析成何种JavaBean
		JsonParser parser = new JsonParser();
		JsonElement jsonNode = parser.parse(message);
		if (jsonNode.isJsonObject()) {
			// 解析出instruction字段
			JsonObject object = jsonNode.getAsJsonObject();
			String instruction = object.get("instruction").getAsString();
			if (this.clientName.equals("phone")) {
				if (instruction.equals("open")) { // 手机端发送来开门指令，转发给机器
					clients.get("machine").session.getBasicRemote().sendText(message);
				} else if (instruction.equals("test")) {
					ArrayList<Item> itemList = test();
					Message send = new Message(instruction, itemList);
					WebSocket.sendMessageTo("phone", send.toJson());
				}
			} else if (this.clientName.equals("machine")) {
				if (instruction.equals("settleup")) { // 如果是结算指令，将结算信息发送给手机端
					ArrayList<Item> itemList = findItems(message);
					Message send = new Message(instruction, itemList);
					WebSocket.sendMessageTo("phone", send.toJson());
				}
			}
		}
	}

	/***
	 * 静态方法，给某个客户端发送消息，可以供Servlet或其他外部类调用
	 * 
	 * @param name    客户端名字
	 * @param message 待发送的消息
	 */
	public static void sendMessageTo(String name, String message) {
		try {
			WebSocket.clients.get(name).session.getBasicRemote().sendText(message);
			System.out.println("发送" + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据machine发送来的json序列，填充具体消息组成ArrayList发送给phone
	 * 
	 * @return id序列对应的Itemlist
	 */
	private ArrayList<Item> findItems(String message) {
		JsonParser parser = new JsonParser();
		JsonElement jsonNode = parser.parse(message);
		if (jsonNode.isJsonObject()) {
			JsonObject object = jsonNode.getAsJsonObject();
			JsonArray items = object.get("items").getAsJsonArray();
			System.out.println(items);
			ArrayList<Item> itemList = new ArrayList<Item>();
			for (int i = 0; i < items.size(); i++) {
				JsonObject itemObject = items.get(i).getAsJsonObject();
				int id = itemObject.get("id").getAsInt();
				int num = itemObject.get("num").getAsInt();
				Item item = service.findById(id);
				item.setNum(num);
				itemList.add(item);
			}
			return itemList;
		}
		return null;
	}

	private ArrayList<Item> test() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		for (int i = 1; i <= 7; i++) {
			Item item = service.findById(i);
			item.setNum(new Random().nextInt());
			itemList.add(item);
		}
		return itemList;
	}
}
