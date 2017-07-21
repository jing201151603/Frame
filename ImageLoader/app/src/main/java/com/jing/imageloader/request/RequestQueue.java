package com.jing.imageloader.request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class RequestQueue {

    /**
     * 阻塞式队列
     * 多线程共享
     * 生产效率和消费效率相差太远
     * disPlayImage（）
     * 使用优先级队列
     */
    private BlockingQueue<BitmapRequest> mReqeustQueue = new PriorityBlockingQueue<>();

    /**
     * 转发器的数量
     */
    private int threadCount;

    //线程安全的编号
    private AtomicInteger i = new AtomicInteger(0);

    private RequestDispatcher[] mDispatcher;

    public RequestQueue(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * 添加请求对象
     *
     * @param request
     */
    public void addRequest(BitmapRequest request) {
        //判断请求队列是否包含该请求
        if (!mReqeustQueue.contains(request)) {

            //给请求进行编号
            request.setSerialNo(i.incrementAndGet());
            mReqeustQueue.add(request);
        }
    }

    /**
     * 开始请求
     */
    public void start() {

        stop();

        startDispatcher();

    }

    private void startDispatcher() {
        mDispatcher = new RequestDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            RequestDispatcher dispatcher = new RequestDispatcher(mReqeustQueue);
            mDispatcher[i] = dispatcher;
            mDispatcher[i].start();
        }

    }

    /**
     * 停止请求
     */
    public void stop() {

    }

}
