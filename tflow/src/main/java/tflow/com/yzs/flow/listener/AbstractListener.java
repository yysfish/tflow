package tflow.com.yzs.flow.listener;

import org.springframework.context.SmartLifecycle;

import tflow.com.yzs.flow.common.BaseService;
import tflow.com.yzs.flow.util.JedisUtils;

/**
 * 继承SmartLifecycle的所有实现类，是在所有bean加载到ioc容器后，才执行start()
 * @author 005
 *
 */
//@Component
public abstract class AbstractListener<T> extends BaseService implements SmartLifecycle{

	/**
	 * redis消息队列名称
	 */
	private String listName;
	
	private boolean isRunning = false;
	
	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	/**
	 * 所有bean加载到ioc容器后，isRunning()返回false，且isAutoStartup()返回true，就会执行该方法
	 */
	public void start() {
		startListen();
		isRunning = true;
		
	}

	/**
	 * 接口Lifecycle的子类的方法，只有非SmartLifecycle的子类才会执行该方法。<br/>
	 * 1. 该方法只对直接实现接口Lifecycle的类才起作用，对实现SmartLifecycle接口的类无效。<br/>
	 * 2. 方法stop()和方法stop(Runnable callback)的区别只在于，后者是SmartLifecycle子类的专属
	 */
	public void stop() {
		
	}

	/**
	 * 1. 只有该方法返回false时，start方法才会被执行。<br/>
	 * 2. 只有该方法返回true时，stop(Runnable callback)或stop()方法才会被执行
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * 所有SmartLifecycle的实现类的执行顺序，越小越先执行
	 */
	public int getPhase() {
		return 0;
	}

	/**
	 * 根据该方法的返回值决定是否执行start方法。
	 * 返回true时start方法会被自动执行，返回false则不会
	 */
	public boolean isAutoStartup() {
		return true;
	}

	/**
	 * 容器关闭时，isRunning返回true，才会执行该方法
	 */
	public void stop(Runnable callback) {
		// 如果你让isRunning返回true，需要执行stop这个方法，那么就不要忘记调用callback.run()。
        // 否则在你程序退出时，Spring的DefaultLifecycleProcessor会认为你这个TestListener没有stop完成，程序会一直卡着结束不了，等待一定时间（默认超时时间30秒）后才会自动结束。
        // PS：如果你想修改这个默认超时时间，可以按下面思路做，当然下面代码是springmvc配置文件形式的参考，在SpringBoot中自然不是配置xml来完成，这里只是提供一种思路。
        // <bean id="lifecycleProcessor" class="org.springframework.context.support.DefaultLifecycleProcessor">
        //      <!-- timeout value in milliseconds -->
        //      <property name="timeoutPerShutdownPhase" value="10000"/>
        // </bean>
		callback.run();
		isRunning = false;
	}
	
	public void startListen() {
		logger.info("开始拉起线程对redis中的{}队列进行监控", listName);
		Thread thread = new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				while(true) {
					Object object = JedisUtils.rightPop(listName, 10);
					if (object != null) {
						processObj((T) object);
					}
				}
			}
		};
		thread.start();
	}
	
	/**
	 * 
	 * @param t
	 */
	protected abstract void processObj(T t);

	
}
