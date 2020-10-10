package vo;

import java.util.ArrayList;

import com.google.gson.Gson;

import po.Item;

public class Message {
	private String instruction;
	private ArrayList<Item> items;

	public Message(String instruction, ArrayList<Item> items) {
		super();
		this.instruction = instruction;
		this.items = items;
	}

	public Message() {
		super();
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
}
