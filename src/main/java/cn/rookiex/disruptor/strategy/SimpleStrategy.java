package cn.rookiex.disruptor.strategy;

import cn.rookiex.disruptor.DynamicDisruptor;
import cn.rookiex.disruptor.sentinel.SentinelEvent;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/11/12 14:23
 * @Describe :
 * @version: 1.0
 */
public class SimpleStrategy implements RegulateStrategy {
    @Override
    public void regulate(DynamicDisruptor dynamicDisruptor, SentinelEvent sentinelEvent) {

    }

    @Override
    public int getNeedUpdateCount(SentinelEvent sentinelEvent) {
        int recentConsumeCount = sentinelEvent.getRecentConsumeCount();
        int recentProduceCount = sentinelEvent.getRecentProduceCount();

        //thread info
        int totalThreadCount = sentinelEvent.getTotalThreadCount();
        int runThreadCount = sentinelEvent.getRunThreadCount();

        int updateCount = 0;

        boolean isThreadRunOut = runThreadCount == totalThreadCount;
        if (isThreadRunOut) {
            if (recentProduceCount > recentConsumeCount){
                //保留两位小数
                int needAddThread = (recentProduceCount * 100 / recentConsumeCount * runThreadCount)  / 100 - runThreadCount;
                updateCount += needAddThread;
            }
        }

        return updateCount;
    }
}
