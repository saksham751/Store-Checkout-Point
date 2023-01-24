package com.increff.groceryPoint.Scheduler;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.ReportMasterdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
public class ReportScheduler {
    @Autowired
    ReportMasterdto reportDto;

    @Async
    @Scheduled(cron = "0 0 * * * *")
    public void createDailyReport() throws ApiException
    {
        reportDto.generate_pos_day_sales();
    }
}
