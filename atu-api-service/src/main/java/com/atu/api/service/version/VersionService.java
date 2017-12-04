package com.atu.api.service.version;

import com.atu.api.domain.version.query.VersionControlQuery;
import com.atu.api.service.result.Result;

public interface VersionService {

    /**
     * 根据手机端类型，版本号获得最新版本
     * @param versionControlQuery
     * @return
     */
    public Result getVersionControlByVersionControlQuery(VersionControlQuery versionControlQuery);







}
