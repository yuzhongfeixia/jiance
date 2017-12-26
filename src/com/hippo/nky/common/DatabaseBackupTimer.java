package com.hippo.nky.common;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时备份数据库业务处理类
 * 
 * @author cyq
 * 
 */
@Service
@Lazy(false)
public class DatabaseBackupTimer {
	/**
	 * 每日凌晨2点,自动备份数据库
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Scheduled(cron = "0 0 2 * * ?")
	public void run() throws InterruptedException, IOException {
		System.out.println("开始备份数据库-------------------------------------");
		Desktop.getDesktop().open(
				new File(getSysPath() + "/assets/db/oracle_autobackup.bat"));
		// Process ps = null;
		// try {
		// String command = getSysPath() + "/assets/db/oracle_autobackup.bat";
		//
		// ps = Runtime.getRuntime().exec("cmd.exe /C start /b "+command);
		// } catch (IOException e) {
		// }
		// ps.waitFor();
		// int i = ps.exitValue();
		// if (i == 0) {
		// System.out.println("数据库备份完成-------------------------------------") ;
		// } else {
		// System.out.println("备份失败") ;
		// }
	}

	private static String getSysPath() {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").toString();
		String temp = path.replaceFirst("file:/", "").replaceFirst(
				"WEB-INF/classes/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator);
		return resultPath;
	}
}
