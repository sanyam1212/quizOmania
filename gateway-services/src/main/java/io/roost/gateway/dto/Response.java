package io.roost.gateway.dto;

import org.springframework.boot.common.object.AbstractObject;

public class Response extends AbstractObject {
	private String entity;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

}
