package com.chrusty.StopWatch;

import com.chrusty.StopWatch.RemoteService;

interface AsyncService {

    void registerCallBack(RemoteService cb);
    void unregisterCallBack(RemoteService cb);
    void resetStartTime();
}