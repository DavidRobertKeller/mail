package drkeller.mail.mailapi.dto;

import javax.validation.constraints.NotNull;

public class MailActor {
	
	private String id;

	private ItemObject itemObject;
	
	@NotNull
	private ActorType type;

	private String state;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setItemObject(ItemObject itemObject) {
		this.itemObject = itemObject;
	}
	
	public ItemObject getItemObject() {
		return itemObject;
	}
		
	public void setState(String state) {
		this.state = state;
	}
	
	public void setType(ActorType type) {
		this.type = type;
	}

	public ActorType getType() {
		return type;
	}
	
	public String getState() {
		return state;
	}
	
}
