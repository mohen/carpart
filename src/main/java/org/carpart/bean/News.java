package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cp_news")
public class News {

	/**
	 * 
	 */
	@Id
	@Column("message_id")
	private Integer messageId;
	/**
	 * 
	 */
	@Column("company_id")
	private Integer companyId;
	/**
	 * 
	 */
	@Column("PART_ID")
	private Integer partId;
	/**
	 * 
	 */
	@Column("city")
	private String city;
	/**
	 * 
	 */
	@Column("message_time")
	private String messageTime;
	/**
	 * 
	 */
	@Column("new_content")
	private String newContent;
	/**
	 * 
	 */
	@Column("scope")
	private String scope;
	/**
	 * 
	 */
	@Column("public_time")
	private java.util.Date publicTime;
	/**
	 * 
	 */
	@Column("due_time")
	private java.util.Date dueTime;
	/**
	 * 
	 */
	@Column("view_times")
	private Integer viewTimes;
	/**
	 * 
	 */
	@Column("author_name")
	private String authorName;
}