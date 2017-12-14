package com.hunt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hunt.dao.SysExceptionLogMapper;
import com.hunt.model.entity.SysExceptionLog;
import com.hunt.service.SysExceptionLogService;

@Service
@Transactional
public class SysExceptionLogServiceImpl implements SysExceptionLogService{

	@Autowired
	private SysExceptionLogMapper sysExceptionLogMapper;
	
	@Override
	public void insertExceptionLogSelective(SysExceptionLog log) {
		sysExceptionLogMapper.insertExceptionLog(log);
	}

}
