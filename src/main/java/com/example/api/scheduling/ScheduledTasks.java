package com.example.api.scheduling;

import com.example.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private TransactionService transactionService;

    @Scheduled(cron = "0 0 0 * * ?") // Günlük her gece yarısı
    public void aggregateDailyExpenses() {
        logger.info("Daily aggregate job started.");
        transactionService.aggregateExpenses("DAILY");
        logger.info("Daily aggregate job finished.");
    }

    @Scheduled(cron = "0 0 0 * * MON") // Haftalık her Pazartesi gece yarısı
    public void aggregateWeeklyExpenses() {
        logger.info("Weekly aggregate job started.");
        transactionService.aggregateExpenses("WEEKLY");
        logger.info("Weekly aggregate job finished.");
    }

    @Scheduled(cron = "0 0 0 1 * ?") // Aylık her ayın ilk günü gece yarısı
    public void aggregateMonthlyExpenses() {
        logger.info("Monthly aggregate job started.");
        transactionService.aggregateExpenses("MONTHLY");
        logger.info("Monthly aggregate job finished.");
    }


}
