package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cp_message")
public class Message {

	/**
	 * 
	 */
	@Id
	@Column("message_id")
	private Integer messageId;
	/**
	 * 
	 */
	@Column("message_type")
	private String messageType;
	/**
	 * 
	 */
	@Column("message_time")
	private String messageTime;
	/**
	 * 
	 */
	@Column("message_content")
	private String messageContent;
	/**
	 * 
	 */
	@Column("update_time")
	private java.util.Date updateTime;
	/**
	 * 
	 */
	@Column("STATUS")
	private String status;
	/**
	 * 
	 */
	@Column("author_name")
	private String authorName;
}