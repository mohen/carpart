package org.carpart.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.carpart.dao.ADao;
import org.carpart.dao.Dao;
import org.carpart.vo.ParkVo;
import org.g4studio.core.metatype.Dto;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.util.G4Utils;

/**
 * 
 * @author BBW
 * 
 */
public class ParkDaoImpl extends ADao implements Dao<ParkVo> {
	private static Log log = LogFactory.getLog(ParkDaoImpl.class);

	@Override
	public void insert(ParkVo vo) {
		this.getSqlMapClientTemplate().insert("ParkVo.insert", vo);
	}

	@Override
	public ParkVo queryForObject(ParkVo vo) {
		return (ParkVo) getSqlMapClientTemplate().queryForObject(
				"CarPart.getById", vo);
	}

	@Override
	public List<ParkVo> queryForList(ParkVo vo) {
		return getSqlMapClientTemplate().queryForList("CarPart.findPage", vo);
	}

	@Override
	public List<ParkVo> queryForPage(ParkVo vo, Dto qDto)
			throws SQLException {
		Connection connection = getConnection();
		String dbNameString = connection.getMetaData().getDatabaseProductName()
				.toLowerCase();
		try {
			connection.close();
		} catch (Exception e) {
			log.error(G4Constants.Exception_Head + "未正常关闭数据库连接");
			e.printStackTrace();
		}
		String start = qDto.getAsString("start");
		String limit = qDto.getAsString("limit");
		int startInt = 0;
		if (G4Utils.isNotEmpty(start)) {
			startInt = Integer.parseInt(start);
			if (dbNameString.indexOf("ora") > -1) {
				qDto.put("start", startInt + 1);
			} else if (dbNameString.indexOf("mysql") > -1) {
				qDto.put("start", startInt);
			} else {
				qDto.put("start", startInt);
			}
		} else {
			qDto.put("start", 0);
			log.warn("缺失分页起始参数,后台已经为你自动赋值，但建议您参照标准范例，如果不是分页查询请使用queryForList()方法");
		}
		if (G4Utils.isNotEmpty(limit)) {
			int limitInt = Integer.parseInt(limit);
			if (dbNameString.indexOf("ora") > -1) {
				qDto.put("end", limitInt + startInt);
			} else if (dbNameString.indexOf("mysql") > -1) {
				qDto.put("end", limitInt);
			} else {
				qDto.put("end", limitInt);
			}
		} else {
			qDto.put("end", 999999);
			log.warn("缺失分终止页参数,后台已经为你自动赋值，但建议您参照标准范例，如果不是分页查询请使用queryForList()方法");
		}

		Integer intStart = qDto.getAsInteger("start");
		Integer end = qDto.getAsInteger("end");
		return getSqlMapClientTemplate().queryForList("CarPart.findPage", qDto,
				intStart.intValue(), end.intValue());
	}

	@Override
	public int update(ParkVo vo) {
		return getSqlMapClientTemplate().update("CarPart.update", vo);
	}

	@Override
	public int delete(ParkVo vo) {
		return getSqlMapClientTemplate().delete("CarPart.delete", vo);
	}

}
