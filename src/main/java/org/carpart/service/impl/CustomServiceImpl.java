/*
 * Powered By [mohen]
 * Since 2014 - 2014
 */

package org.carpart.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.carpart.service.IService;
import org.carpart.vo.CustomVo;
import org.g4studio.common.service.impl.BaseServiceImpl;
import org.g4studio.core.metatype.Dto;
import org.g4studio.core.util.G4Utils;

public class CustomServiceImpl extends BaseServiceImpl implements IService<CustomVo> {

	private String VO_NAME = "Custom";

	@Override
	public Dto save(Dto pDto) {
		g4Dao.insert(VO_NAME + ".insert", pDto);
		return pDto;
	}

	@Override
	public CustomVo queryById(Dto pDto) {
		return (CustomVo) g4Dao.queryForObject(VO_NAME + ".getById", pDto);
	}

	@Override
	public List queryByPage(Dto pDto) {
		List list = new ArrayList();
		try {
			list = g4Dao.queryForPage(VO_NAME + ".findPage", pDto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Dto batchSave(Dto pDto) {
		return null;
	}

	@Override
	public Dto update(Dto pDto) {
		CustomVo vo = new CustomVo();
		G4Utils.copyPropFromDto2Bean(pDto, vo);
		g4Dao.update(VO_NAME + ".update", vo);
		return pDto;
	}

	@Override
	public Dto delete(Dto pDto) {
		g4Dao.delete(VO_NAME + ".delete", pDto);
		return pDto;
	}

	@Override
	public int queryCount(Dto pDto) {
		return (Integer) g4Dao.queryForObject(VO_NAME + ".findPage.count", pDto);
	}

	@Override
	public List queryByList(Dto pDto) {
		return g4Dao.queryForList(VO_NAME + ".findPage", pDto);
	}

}
