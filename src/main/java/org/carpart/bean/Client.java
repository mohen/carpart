package org.carpart.bean;

import org.carpart.CPConstants;
import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cp_client")
public class Client extends Base {

	/**
	 * 客户端ID
	 */
	@Id
	@Column("client_id")
	private Integer clientId=CPConstants.LOCAL_SERVER_ID;
	/**
	 * 客户端描述
	 */
	@Column("client_desc")
	private String clientDesc;
	/**
	 * 客户端类型
	 */
	@Column("client_type")
	private String clientType;
	/**
	 * 客户端IP
	 */
	@Column("client_ip")
	private String clientIp;
	/**
	 * 客户端密文
	 */
	@Column("client_code")
	private String clientCode;
	/**
	 * 密文KEY
	 */
	@Column("client_key")
	private String clientKey;
	/**
	 * 状态
	 */
	@Column("status")
	private String status;
	/**
	 * 
	 */
	@Column("park_id")
	private Integer parkId;
}