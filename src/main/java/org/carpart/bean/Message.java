package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 消息模板
*/
@Data
@Table("cp_message")
public class Message {

	/**
	 * ID
	 */
	@Id
	@Column("message_id")
	private Integer messageId;
	/**
	 * 消息类型
	 */
	@Column("message_type")
	private String messageType;
	/**
	 * 时间
	 */
	@Column("message_time")
	private String messageTime;
	/**
	 * 内容
	 */
	@Column("message_content")
	private String messageContent;
	/**
	 * 更新时间
	 */
	@Column("update_time")
	private java.util.Date updateTime;
	/**
	 * 状态
	 */
	@Column("STATUS")
	private String status;
	/**
	 * 编辑人
	 */
	@Column("author_name")
	private String authorName;
}