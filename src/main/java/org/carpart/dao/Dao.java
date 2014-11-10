package org.carpart.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.g4studio.core.exception.PrcException;
import org.g4studio.core.metatype.BaseVo;
import org.g4studio.core.metatype.Dto;
import org.g4studio.core.orm.xibatis.support.SqlMapClientTemplate;

/**
 * 数据访问接口(原生)<br>
 * 基于iBatis实现,支持自定义的数据操作
 * 
 */
public interface Dao<T extends BaseVo> {

	/**
	 * 插入一条记录
	 * 
	 * @param vo
	 *            要插入的对象( javaBean)
	 */
	public void insert(T vo);

	/**
	 * 查询一条记录
	 * 
	 * @param vo
	 *            查询条件对象( javaBean)
	 */
	public T queryForObject(T vo);

	/**
	 * 查询记录集合
	 * 
	 * @param SQL语句ID号
	 * @param parameterObject
	 *            查询条件对象(map javaBean)
	 */
	public List<T> queryForList(T vo);

	/**
	 * 按分页查询
	 * 
	 * @param SQL语句ID号
	 * @param parameterObject
	 *            查询条件对象(map javaBean)
	 */
	public List<T> queryForPage(T vo,Dto qDto) throws SQLException;

	/**
	 * 更新记录
	 * 
	 * @param SQL语句ID号
	 * @param parameterObject
	 *            更新对象(map javaBean)
	 */
	public int update(T vo);

	/**
	 * 删除记录
	 * 
	 * @param SQL语句ID号
	 * @param parameterObject
	 *            更新对象(map javaBean)
	 */
	public int delete(T vo);

	/**
	 * 调用存储过程<br>
	 * 存储过程成功返回标志缺省：appCode=1
	 * 
	 * @param prcName 存储过程ID号
	 * @param parameterObject 参数对象(入参、出参)
	 * @throws PrcException 存储过程调用异常
	 */
	public void callPrc(String prcName, Dto prcDto) throws PrcException;
	
	/**
	 * 调用存储过程<br>
	 * 存储过程成功返回标志自定义：appCode=successFlag(自定义的传入变量)
	 * 
	 * @param prcName 存储过程ID号
	 * @param parameterObject 参数对象(入参、出参)
	 * @param prcName 存储过程调用成功返回的成功代码值
	 * @throws PrcException 存储过程调用异常
	 */
	public void callPrc(String prcName, Dto prcDto, String successFlag) throws PrcException;
	
	/**
	 * 获取Connection对象<br>
	 * 说明：虽然向Dao消费端暴露了获取Connection对象的方法但不建议直接获取Connection对象进行JDBC操作
	 * 
	 * @return 返回Connection对象
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;
	
	/**
	 * 获取DataSource对象<br>
	 * 说明：虽然向Dao消费端暴露了获取DataSource对象的方法但不建议直接获取DataSource对象进行JDBC操作
	 * 
	 * @return 返回DataSource对象
	 * @throws SQLException
	 */
	public DataSource getDataSourceFromSqlMap() throws SQLException;
	
	/**
	 * 获取SqlMapClientTemplate对象<br>
	 * 
	 * @return 返回SqlMapClientTemplate对象
	 */
	public SqlMapClientTemplate getSqlMapClientTpl();
}
