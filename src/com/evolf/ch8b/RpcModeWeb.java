package com.evolf.ch8b;

import com.evolf.ch8b.assist.Consts;
import com.evolf.ch8b.assist.CreatePendingDocs;
import com.evolf.ch8b.assist.SL_QuestionBank;
import com.evolf.ch8b.service.ProduceDocService;
import com.evolf.ch8b.vo.SrcDocVo;

import java.util.List;
import java.util.concurrent.*;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：rpc服务端，采用生产者消费者模式，生产者消费者还会级联
 */
public class RpcModeWeb {
	
	//负责生成文档（下载图片，本地写入IO=CPU*2）
	private static ExecutorService docMakeService 
		= Executors.newFixedThreadPool(Consts.CPU_COUNT*2); 
	
	//负责上传文档(IO = CPU*2)
	private static ExecutorService docUploadService 
		= Executors.newFixedThreadPool(Consts.CPU_COUNT*2);
	//ch6  先完成的先上传
	private static CompletionService<String> docCs
	    = new ExecutorCompletionService<>(docMakeService); 
	//先上传完的 先回显
	private static CompletionService<String> docUploadCs
    = new ExecutorCompletionService<>(docUploadService);
	
	public static void main(String[] args) throws InterruptedException, 
	ExecutionException {
        System.out.println("题库开始初始化...........");
        SL_QuestionBank.initBank();
        System.out.println("题库初始化完成。");
        
        //创建60个待处理文档
        List<SrcDocVo> docList = CreatePendingDocs.makePendingDoc(60);
        long startTotal = System.currentTimeMillis();

        //生成文档 交给线程池 线程池static全局
        for(SrcDocVo doc:docList){
        	docCs.submit(new MakeDocTask(doc));
        }

        //上传文档  交给线程池
        for(SrcDocVo doc:docList){
        	Future<String> future = docCs.take();
        	docUploadCs.submit(new UploadDocTask(future.get()));//future.get()获取结果
        }

        //在实际的业务过程中可以不要，主要为了取得时间（模拟消耗时间）
        for(SrcDocVo doc:docList){
        	docUploadCs.take().get();
        }
        
        System.out.println("------------共耗时："
        		+(System.currentTimeMillis()-startTotal)+"ms-------------");
	}
	
	//生成文档的任务 需要返回值传给上传文档任务，所以用Callable
	private static class MakeDocTask implements Callable<String>{
		//待处理文档模板类（包含文档名、题目id列表；待填充具体题目然后生成文档文件）
		private SrcDocVo pendingDocVo;
		
		public MakeDocTask(SrcDocVo pendingDocVo) {
			super();
			this.pendingDocVo = pendingDocVo;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();
            //String localName = ProduceDocService.makeDoc(pendingDocVo);//优化前
			String localName = ProduceDocService.makeDocAsyn(pendingDocVo);//优化后
            System.out.println("文档"+localName+"生成耗时："
            		+(System.currentTimeMillis()-start)+"ms");
			return localName;
		}
	}
	
	//上传文档的任务 （上传后的地址需要回显，所以callable）
	private static class UploadDocTask implements Callable<String>{
		
		private String filePath;
		
		public UploadDocTask(String filePath) {
			super();
			this.filePath = filePath;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();
			String remoteUrl = ProduceDocService.upLoadDoc(filePath);
            System.out.println("已上传至["+remoteUrl+"]耗时："
            		+(System.currentTimeMillis()-start)+"ms");
			return remoteUrl;
		}
	}
	
	

}
