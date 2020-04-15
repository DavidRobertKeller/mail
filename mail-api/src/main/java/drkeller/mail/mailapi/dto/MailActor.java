package drkeller.mail.mailapi.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MailActor {
	
	private String id;

	private ItemObject itemObject;
	
	@NotNull
	private ActorType type;

	private String state;

	@JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private LocalDateTime creationDate;

	@JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private LocalDateTime lastModificationDate;

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
	
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getLastModificationDate() {
		return lastModificationDate;
	}
	
	public void setLastModificationDate(LocalDateTime lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

}
