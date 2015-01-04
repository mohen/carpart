package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cp_error")
public class Error {

	/**
	 * 
	 */
	@Name
	@Column("ERR_CODE")
	private String errCode;
	/**
	 * 客户端ID
	 */
	@Column("client_id")
	private Integer clientId;
	/**
	 * 错误类型
	 */
	@Column("ERR_TYPE")
	private String errType;
	/**
	 * 错误内容
	 */
	@Column("ERR_DETAIL")
	private String errDetail;
	/**
	 * 发生时间
	 */
	@Column("CREATE_TIME")
	private java.util.Date createTime;
	/**
	 * 查询次数
	 */
	@Column("QUERY_NUM")
	private Integer queryNum;
	/**
	 * 最近查询时间
	 */
	@Column("QUERY_TIME")
	private java.util.Date queryTime;
}