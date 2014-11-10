package org.carpart.service;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import org.g4studio.common.service.BaseService;
import org.g4studio.core.metatype.BaseVo;
import org.g4studio.core.metatype.Dto;

/**
 * 
 * @author BBW
 * 
 */
@WebService
public interface IService<T extends BaseVo> extends BaseService {

	/**
	 * 插入一条记录
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto save(Dto pDto);

	/**
	 * 根据ID 查询一条记录
	 * 
	 * @param pDto
	 * @return
	 */
	public T queryById(Dto pDto);

	/**
	 * 根据条件分页查询多条记录
	 * 
	 * @param pDto
	 * @return
	 */
	public List<T> queryByPage(Dto pDto);

	/**
	 * 根据条件查询多条记录
	 * 
	 * @param pDto
	 * @return
	 */
	public List<T> queryByList(Dto pDto);

	/**
	 * 根据条件查询条数
	 * 
	 * @param pDto
	 * @return
	 */
	public int queryCount(Dto pDto);

	/**
	 * 插入一批记录(JDBC批处理演示)
	 * 
	 * @param pDto
	 * @return
	 * @throws SQLException
	 */
	public Dto batchSave(Dto pDto);

	/**
	 * 修改一条记录
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto update(Dto pDto);

	/**
	 * 删除一条记录
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto delete(Dto pDto);

}
