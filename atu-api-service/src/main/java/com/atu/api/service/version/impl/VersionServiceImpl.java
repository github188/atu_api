package com.atu.api.service.version.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.atu.api.dao.VersionControlDao;
import com.atu.api.domain.version.VersionControl;
import com.atu.api.domain.version.query.VersionControlQuery;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;
import com.atu.api.service.version.VersionService;

@Service(value="versionService")
public class VersionServiceImpl implements VersionService {

    private final static Logger logger = Logger.getLogger(VersionServiceImpl.class);

    private VersionControlDao versionControlDao;

    @Override
    public Result getVersionControlByVersionControlQuery(VersionControlQuery versionControlQuery) {
        Result result = new Result();
		try{
            logger.info("获取版本号");
			List<VersionControl> versionControlList = versionControlDao.selectByCondition(versionControlQuery);
			if(versionControlList == null || versionControlList.size() ==0){
				result.setResultCode("10001");
				result.setResultMessage("没有找到相关版本");
				return result;
			}
			
			result.setResult(versionControlList.get(0));
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			logger.error("", e);
			EcUtils.setExceptionResult(result);
		}
        return result;
    }

    public void setVersionControlDao(VersionControlDao versionControlDao) {
        this.versionControlDao = versionControlDao;
    }
}
