package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
 * 消息
 */
@Data
@Table("cp_news")
public class News {
	/**
	 * ID
	 */
	@Id
	@Column("message_id")
	private Integer messageId;
	/**
	 * 发布商户
	 */
	@Column("company_id")
	private Integer companyId;
	/**
	 * 停车场
	 */
	@Column("PART_ID")
	private Integer partId;

	@One(field = "partId", target = Park.class)
	private Park park;
	/**
	 * 城市
	 */
	@Column("city")
	private String city;
	/**
	 * 编辑时间
	 */
	@Column("message_time")
	private String messageTime;
	/**
	 * 内容
	 */
	@Column("new_content")
	private String newContent;
	/**
	 * 产品说明
	 */
	@Column("scope")
	private String scope;
	/**
	 * 状态
	 */
	@Column("status")
	private String status;
	/**
	 * 发布时间
	 */
	@Column("public_time")
	private java.util.Date publicTime;
	/**
	 * 到期时间
	 */
	@Column("due_time")
	private java.util.Date dueTime;
	/**
	 * 浏览次数
	 */
	@Column("view_times")
	private Integer viewTimes;
	/**
	 * 发布人
	 */
	@Column("author_name")
	private String authorName;
}