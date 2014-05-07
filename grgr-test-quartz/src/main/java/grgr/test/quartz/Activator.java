package grgr.test.quartz;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class Activator implements BundleActivator {

    Scheduler scheduler;

    @Override
    public void start(BundleContext context) throws Exception {
        Properties props = new Properties();
        props.setProperty(StdSchedulerFactory.PROP_SCHED_JMX_EXPORT, "true");
        props.setProperty(StdSchedulerFactory.PROP_JOB_STORE_CLASS, "org.quartz.simpl.RAMJobStore");
        props.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_PREFIX + ".threadCount", "3");
        this.scheduler = new StdSchedulerFactory(props).getScheduler();
        this.scheduler.start();

        JobDataMap map = new JobDataMap();
        map.put("CamelQuartzTriggerType", "cron");
        map.put("CamelQuartzTriggerCronExpression", "0/2 * * * * ?");

        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
            .withDescription("my job 1")
            .withIdentity("job1", "group1")
            .setJobData(map)
            .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger1", "group1")
            .startNow()
            .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
            .usingJobData(map)
            .build();

        JobDataMap map2 = new JobDataMap();
        map2.put("CamelQuartzTriggerType", "simple");
        map2.put("CamelQuartzTriggerSimpleRepeatInterval", "2000");
        map2.put("CamelQuartzTriggerSimpleRepeatCounter", "20");

        JobDetail simpleJobDetail = JobBuilder.newJob(MyJob.class)
            .withDescription("my job 2")
            .withIdentity("job2", "group1")
            .setJobData(map2)
            .build();

        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger2", "group1")
            .startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(20))
            .usingJobData(map2)
            .build();

        this.scheduler.scheduleJob(jobDetail, cronTrigger);
        this.scheduler.scheduleJob(simpleJobDetail, simpleTrigger);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        this.scheduler.shutdown();
    }

    /**
     *
     */
    public static class MyJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("HEJ: " + context.getJobRunTime());
        }
    }

}
