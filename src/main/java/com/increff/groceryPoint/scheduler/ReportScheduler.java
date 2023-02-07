package com.increff.groceryPoint.scheduler;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.ReportMasterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
@EnableAsync
public class ReportScheduler {
    @Autowired
    ReportMasterDto reportDto;

    @Async
    @Scheduled(cron = "0 0 0 * * *")
    public void createDailyReport() throws ApiException
    {
        reportDto.get_pos_day_sales();
    }
}
