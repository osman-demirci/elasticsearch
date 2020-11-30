package io.od.example.elastic.common;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author osman.demirci
 *
 */
public abstract class DocumentEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
