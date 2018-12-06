package com.evolf.ch8b.service.question;

import com.evolf.ch8b.assist.Consts;
import com.evolf.ch8b.assist.SL_QuestionBank;
import com.evolf.ch8b.vo.QuestionInCacheVo;
import com.evolf.ch8b.vo.QuestionInDBVo;
import com.evolf.ch8b.vo.TaskResultVo;

import java.util.concurrent.*;

/**
 *@author Mark老师   itheone itheone
 *
 *类说明：并发处理题目的服务（已处理的题目放入缓存，防止重复处理题目）
 */
public class ParallerQstService {
	
	//已处理题目的缓存
	private static ConcurrentHashMap<Integer, QuestionInCacheVo> questionCache 
	  = new ConcurrentHashMap<>();
	
	//正在处理题目的缓存（必须考虑缓存失效机制，如不管题目处理成功或失败，必须从处理中移除）FutureTask 为已创建出的任务 还未交给线程（交给线程执行后 future.get()获取返回值）
	private static ConcurrentHashMap<Integer, Future<QuestionInCacheVo>> 
		processingQuestionCache = new ConcurrentHashMap<>();

	//处理题目的线程池
	private static ExecutorService makeQuestionService 
	= Executors.newFixedThreadPool(Consts.CPU_COUNT*2); 
	
	public static TaskResultVo makeQuestion(Integer questionId) {
		//获取缓存中题目
		QuestionInCacheVo qstCacheVo = questionCache.get(questionId);
		if(null==qstCacheVo) {
			System.out.println("......题目["+questionId+"]在缓存中不存在，"
					+ "准备启动任务.");
			//先判断正在有无正在执行 中的线程处理此题目
			return new TaskResultVo(getQstFuture(questionId));
		}else {
			//拿摘要（from DB Cache）
			String questionSha = SL_QuestionBank.getSha(questionId);
			if(questionSha.equals(qstCacheVo.getQuestionSha())) {
				System.out.println("......题目["+questionId+"]在缓存中已存在，且未变化.");
				return new TaskResultVo(qstCacheVo.getQuestionDetail());
			}else {
				System.out.println("......题目["+questionId+"]在缓存中已存在，"
						+ "但是发生了变化，更新缓冲.");
				return new TaskResultVo(getQstFuture(questionId));
			}
		}
	}
	//判断是否有处理中的题目 返回正在处理题目中的线程任务
	private static Future<QuestionInCacheVo> getQstFuture(Integer questionId){
		Future<QuestionInCacheVo> questionFuture 
			= processingQuestionCache.get(questionId);
		try {
			//处理中缓存map中没有
			if(questionFuture==null) {
				QuestionInDBVo qstDbVo = SL_QuestionBank.getQuetion(questionId);
				QuestionTask questionTask = new QuestionTask(qstDbVo,questionId);
				/*不靠谱的，无法避免两个线程处理同一个题目
				questionFuture = makeQuestionService.submit(questionTask);//存在线程1 submit一次，线程2 submit一次，因为2读取时还是null（线程1submit时，线程2已经put）
				processingQuestionCache.putIfAbsent(questionId, questionFuture);
				改成
				processingQuestionCache.putIfAbsent(questionId, questionFuture);//questionFuture 为null ，无法进行put
				questionFuture = makeQuestionService.submit(questionTask);
				也不行
				*/
				FutureTask<QuestionInCacheVo> ft 
					= new FutureTask<QuestionInCacheVo>(questionTask);//ft为一个即将执行处理题目的线程（new出实例，未start状态，放入线程池中才开始,因为泛型才如此定义）
				//加入处理中题目缓存
				questionFuture = processingQuestionCache.putIfAbsent(questionId, ft);//先把ft 加入题目缓存map中占位 （即先尝试直接将此任务线程放进处理中map）
				// （putIfAbsent   如果传入key对应的value已经存在，就返回存在的value，不进行替换。如果不存在，就添加key和value，返回null）
				if(questionFuture==null) {
					//先在map中占位？？？？？？？？？？？ 本线程判断map中为null 将questionFuture置非null，则其他线程同时查出null时
					//当前线程从map中获取为null，此时map中无写入，其他线程读取也是null，可能同时启动生成题目任务
					questionFuture = ft;
					makeQuestionService.execute(ft);//execute 非submit
					System.out.println("成功启动了题目["+questionId+"]的计算任务，请等待完成>>>>>>>>");
				}else {//如果不为null，说明已经有线程将此题目放入缓存
					System.out.println("<<<<<<<<<<<有其他线程刚刚启动了题目["+questionId
							+"]的计算任务，本任务无需开启！");
				}
			}else {
				 System.out.println("题目[]已存在计算任务，无需重新生成.");
			}
		} catch (Exception e) {
			processingQuestionCache.remove(questionId);
			e.printStackTrace();
			throw e;
			
		}
		return questionFuture;
	}
	
	
	//解析题目的任务类
	private static class QuestionTask implements Callable<QuestionInCacheVo>{
		
		private QuestionInDBVo qstDbVo;
		private Integer questionId;
		
		public QuestionTask(QuestionInDBVo qstDbVo, Integer questionId) {
			super();
			this.qstDbVo = qstDbVo;
			this.questionId = questionId;
		}

		@Override
		public QuestionInCacheVo call() throws Exception {
			try {
				String qstDetail = BaseQuestionProcessor.makeQuestion(questionId,
						SL_QuestionBank.getQuetion(questionId).getDetail());
				String questionSha = qstDbVo.getSha();
				QuestionInCacheVo qstCache = new QuestionInCacheVo(qstDetail, questionSha);
				questionCache.put(questionId, qstCache);
				return qstCache;
			} finally {
				//不管生成题目的任务正常与否，这个任务都要从正在处理题目的缓存中移除
				processingQuestionCache.remove(questionId);
			}
		}
		
	}

}
