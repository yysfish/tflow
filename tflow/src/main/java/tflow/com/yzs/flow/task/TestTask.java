package tflow.com.yzs.flow.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tflow.com.yzs.flow.common.BaseService;
import tflow.com.yzs.flow.dao.OrderDAO;

@Component
public class TestTask extends BaseService{

	@Autowired
	private OrderDAO orderDAO;
	
	@Scheduled(cron="*/10 * * * * ?")
	private void run() {
		logger.info("testtask:" + System.currentTimeMillis());
		try {
			orderDAO.move("2018-09-02");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
