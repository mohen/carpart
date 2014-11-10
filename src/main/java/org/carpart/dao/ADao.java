package org.carpart.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.g4studio.core.exception.PrcException;
import org.g4studio.core.metatype.Dto;
import org.g4studio.core.orm.xibatis.support.SqlMapClientTemplate;
import org.g4studio.core.orm.xibatis.support.bridge.SqlMapClientDaoSupport;
import org.g4studio.core.properties.PropertiesFactory;
import org.g4studio.core.properties.PropertiesFile;
import org.g4studio.core.properties.PropertiesHelper;
import org.g4studio.core.util.G4Utils;

public class ADao extends SqlMapClientDaoSupport {
	/**
	 * 调用存储过程<br>
	 * 存储过程成功返回标志缺省：appCode=1
	 * 
	 * @param prcName
	 *            存储过程ID号
	 * @param parameterObject
	 *            参数对象(入参、出参)
	 * @throws PrcException
	 *             存储过程调用异常
	 */
	public void callPrc(String prcName, Dto prcDto) throws PrcException {
		PropertiesHelper pHelper = PropertiesFactory
				.getPropertiesHelper(PropertiesFile.G4);
		String callPrcSuccessFlag = pHelper.getValue("callPrcSuccessFlag", "1");
		getSqlMapClientTemplate().insert(prcName, prcDto);
		if (G4Utils.isEmpty(prcDto.getAsString("appCode"))) {
			throw new PrcException(prcName, "存储过程没有返回状态码appCode");
		} else {
			if (!prcDto.getAsString("appCode").equals(callPrcSuccessFlag)) {
				throw new PrcException(prcName, prcDto.getAsString("appCode"),
						prcDto.getAsString("errorMsg"));
			}
		}
	}

	/**
	 * 调用存储过程<br>
	 * 存储过程成功返回标志自定义：appCode=successFlag(自定义的传入变量)
	 * 
	 * @param prcName
	 *            存储过程ID号
	 * @param parameterObject
	 *            参数对象(入参、出参)
	 * @param prcName
	 *            存储过程调用成功返回的成功代码值
	 * @throws PrcException
	 *             存储过程调用异常
	 */
	public void callPrc(String prcName, Dto prcDto, String successFlag)
			throws PrcException {
		getSqlMapClientTemplate().insert(prcName, prcDto);
		if (G4Utils.isEmpty(prcDto.getAsString("appCode"))) {
			throw new PrcException(prcName, "存储过程没有返回状态码appCode");
		} else {
			if (!prcDto.getAsString("appCode").equals(successFlag)) {
				throw new PrcException(prcName, prcDto.getAsString("appCode"),
						prcDto.getAsString("errorMsg"));
			}
		}
	}

	/**
	 * 获取Connection对象<br>
	 * 说明：虽然向Dao消费端暴露了获取Connection对象的方法但不建议直接获取Connection对象进行JDBC操作
	 * 
	 * @return 返回Connection对象
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return getSqlMapClientTemplate().getDataSource().getConnection();
	}

	/**
	 * 获取DataSource对象<br>
	 * 说明：虽然向Dao消费端暴露了获取DataSource对象的方法但不建议直接获取DataSource对象进行JDBC操作
	 * 
	 * @return 返回DataSource对象
	 */
	public DataSource getDataSourceFromSqlMap() throws SQLException {
		return getSqlMapClientTemplate().getDataSource();
	}

	/**
	 * 获取SqlMapClientTemplate对象<br>
	 * 
	 * @return 返回SqlMapClientTemplate对象
	 */
	public SqlMapClientTemplate getSqlMapClientTpl() {
		return getSqlMapClientTemplate();
	}

}
