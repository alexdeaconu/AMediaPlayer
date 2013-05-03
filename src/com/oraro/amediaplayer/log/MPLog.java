package com.oraro.amediaplayer.log;

import java.util.logging.Logger;

import android.content.Context;
import android.util.Log;

/**
 * Media Player Log. Wrapper of the android log class. If configured, it can
 * write the log to a file on the SDcard.
 * 
 * @author alexandru.deaconu
 * @date May 2, 2013, 9:58:38 PM
 * @version
 */
public class MPLog {
	
	private static final String TAG = "MPLog";

	//debugger Type
	public static final int USE_LOGCAT = 0;
	public static final int USE_MICROLOG = 1;
	
	private static int loggerType = 0;	//default logger is LOGCAT
	
	//debugging priority levels
	public static final int VERBOSE = 0;
	public static final int DEBUG = 1;
	public static final int INFO = 2;
	public static final int WARNING = 3;
	public static final int ERROR = 4;	//highest priority; FATAL and SILENT will not be logged
	
	private static int debugLevel = 1;

	//flags for debugging
	private static boolean v = 0 >= debugLevel;
	private static boolean d = 1 >= debugLevel;
	private static boolean i = 2 >= debugLevel;
	private static boolean w = 3 >= debugLevel;
	private static boolean e = 4 >= debugLevel;
	
	//microlog setup
	private static Logger logger;
	private static final String LOG_FILENAME = "mymedicII_log.txt";
	private static Context staticContext;
	public static final String LOG_DIR_NAME = "/mymedicII/Log";
	
//	/**
//	 * This method sets 'Microlog' as the default logger. It must be used only if
//	 * 'Microlog' is used.
//	 * @param context
//	 *            - e.g. an activity or an application
//	 */
//	public static void setUpMicrolog(Context context) {
//		if(context == null) {
//			throw new InvalidParameterException(TAG + ".setUpLogger # the context is null");
//		}
//		staticContext = context;
//		setMicrologLogger();
//		loggerType =USE_MICROLOG;
//	}
	
	
	/**
	 * This method sets <i>logcat</i> as the default logger. Its purpose is to be used if
	 * <i>logcat</i> is not the default logger
	 */
	public static void setUpLogcat() {
		loggerType = USE_LOGCAT;
	}
	
	
	/**
	 * Sets the debug level. If negative numbers are send as parameters, the debugging level will be 'verbose'
	 * @param level integer that represents the debug level
	 * <p><i>e.g: Debug = MMLog.DEBUG </i></p>
	 * <p><i>e.g: Info = MMLog.INFO </i></p>
	 */
	public static void setDebugLevel(int level) {
		debugLevel = level;
		v = 0 >= debugLevel;
		d = 1 >= debugLevel;
		i = 2 >= debugLevel;
		w = 3 >= debugLevel;
		e = 4 >= debugLevel;

		if(debugLevel>=5) {	//if debug level is grater than 5, debug level is set to 'error'
			e = true;
		}
	}
	
	
	/**
	 * @param tag - e.g the name of a class
	 * @param msg - the debug message
	 */
	public static void v(String tag, String msg) {
		if(v) { //verbose priority is enabled. Microlog does not have verbose level
			Log.v(tag, msg);
		}
	}
	
	
//	/**
//	 * Sets the Microlog
//	 */
//	private static void setMicrologLogger() {
//		if(logger!=null || staticContext ==null) {
//			return;
//		}
//		
//		boolean useFileAppender = true;
//		boolean logFileWasCreated = false; 
//
//		try {
//			// try to create the LOG_FILE, which is the file where the log will be written. 
//			File root = ResourceManager.STORAGE_ROOT;
//			if (root.canWrite()) {
//				File logDir = new File(root.getAbsoluteFile()+LOG_DIR_NAME);
//				if(logDir.exists() ) {
////					Log.d(TAG, ".setMicrologLogger # the log directory exits");
//					logFileWasCreated = true;
//				} else {
//					Log.d(TAG, ".setMicrologLogger # the log directory doesn't exit");
//					if(logDir.mkdirs()) {
////						Log.d(TAG, ".setMicrologLogger # the log directory was made directory from file");
//						File logFile = new File(logDir, LOG_FILENAME);
//						logFileWasCreated = logFile.createNewFile();
//						DatagramAppender da = new DatagramAppender();
//						// if no exception was thrown by the createNewFile() then it means 
//						// that we can use the log file because if exists (now).
//					} 
//				}
//			} else {
//				// we can't write to SDCard, so don't use the FileAppender for microlog
//				useFileAppender = false;
//			}
//		} catch (Exception e) {
//			Log.w(TAG, "Could create log directory: "+e);
//			useFileAppender = false;
//		}
//
//		
//		if (logger==null || logFileWasCreated || !useFileAppender){
//			if (logger != null) {
//				// this means we have a previous logger and we need to close it.
//				try {
//					logger.close();
//				} catch (Exception e) {
//					Log.w(TAG, "Could not close the previous logger, the logger will still be configured with the new options!");
//				}
//			}
//				
//			// configure logger so that is accessible from all around the application
//			PropertyConfigurator.getConfigurator(staticContext).configure();
//			logger = LoggerFactory.getLogger();
//			
////			Log.d(TAG, ".setMicrologLogger # useFileAppende="+useFileAppender);
//			if (logFileWasCreated && useFileAppender){
//				Logger rootLogger = DefaultLoggerRepository.INSTANCE.getRootLogger();
//				rootLogger.setLevel(Level.DEBUG);
//				rootLogger.addAppender(new LogCatAppender());	//need this to log to console
//
//				FileAppender fileAppender = new FileAppender();
//				fileAppender.setFileName(LOG_DIR_NAME+"/"+LOG_FILENAME);
//				fileAppender.setAppend(true);
//				rootLogger.addAppender(fileAppender);			//need this to log to file
//				
//				PatternFormatter formatter = new PatternFormatter();
//				formatter.setPattern( "%d [%P] %c - %m %T" );
//				int numberOfAppenders = rootLogger.getNumberOfAppenders();
//				for( int appenderNo = 0; appenderNo < numberOfAppenders; appenderNo++ )
//				{
//					Appender appender = rootLogger.getAppender( appenderNo );
//					appender.setFormatter( formatter );
//				}
//			}
//		}else 
//		{
//			Log.i (TAG,"Logger is allready configured!");
//		}
//	}


	/** 
	 * Writes a debug entry into the log.
	 * @param tag - e.g the name of a class
	 * @param msg - the debug message
	 */
	public static void d(String tag, String msg) {
		if(d) { //debug priority is enabled
			
			switch (loggerType) {
			case USE_LOGCAT:
				Log.d(tag, msg);
				break;
			case USE_MICROLOG:
				if(logger!=null) {
//					logger.debug(tag + msg);
				}
				break;
			default:
				break;
			}
			
		}
	}
	
	
	/**
	 *  Writes a info entry into the log.
	 * @param tag - e.g the name of a class
	 * @param msg - the debug message
	 */
	public static void i(String tag, String msg) {
		if(i) { //info priority is enabled
			
			switch (loggerType) {
			case USE_LOGCAT:
				Log.i(tag, msg);
				break;
			case USE_MICROLOG:
				if (logger != null) {
					logger.info(tag + msg);
				}
				break;
			default:
				break;
			}
			
		}
	}
	
	
	/**
	 *  Writes a entry into the log.
	 * @param tag - e.g the name of a class
	 * @param msg - the debug message
	 */
	public static void w(String tag, String msg) {
		if(w) { //warning priority is enabled
			
			switch (loggerType) {
			case USE_LOGCAT:
				Log.w(tag, msg);
				break;
			case USE_MICROLOG:
				if(logger!=null) {
//					logger.warn(tag + msg);
				}
				break;
			default:
				break;
			}
			
		}
	}
	
	
	/**
	 *  Writes a entry into the log.
	 * @param tag - e.g the name of a class
	 * @param msg - the debug message
	 */
	public static void w(String tag, String msg, Throwable t) {
		if(w) { //warning priority is enabled
			Log.w(tag, msg, t);
		}
	}
	
	
	/**
	 *  Writes an error entry into the log. 
	 * @param tag - e.g the name of a class
	 * @param msg - the debug message
	 * @param t - if present the stack trace of the Throwable will be printed
	 * @param r - if not null the runnable's '.run' command will be called.
	 */
	public static void w(String tag, String msg, Throwable t, Runnable r) {
		if(w) { //warning priority is enabled
			Log.w(tag, msg, t);
			
			if (r != null) {
				r.run();
			}
		}
	}
	
	
	/**
	 *  Writes an error entry into the log.
	 * @param tag - e.g the name of a class
	 * @param msg - the debug message
	 */
	public static void e(String tag, String msg) {
		if(e) { //error priority is enabled
			
			switch (loggerType) {
			case USE_LOGCAT:
				Log.e(tag, msg);
				break;
			case USE_MICROLOG:
				if(logger!=null) {
//					logger.error(tag + msg);
				}
				break;
			default:
				break;
			}
			
		}
	}
	
	
	/**
	 *  Writes an error entry into the log. 
	 * @param tag - e.g the name of a class
	 * @param msg - the debug message
	 * @param t - if present the stack trace of the Throwable will be printed
	 */
	public static void e(String tag, String msg, Throwable t) {
		if(e) { //error priority is enabled
			Log.e(tag, msg, t);
		}
	}
	
	/**
	 *  Writes an error entry into the log. 
	 * @param tag - e.g the name of a class
	 * @param msg - the debug message
	 * @param t - if present the stack trace of the Throwable will be printed
	 * @param r - if not null the runnable's '.run' command will be called.
	 */
	public static void e(String tag, String msg, Throwable t, Runnable r) {
		if(e) { //error priority is enabled
			Log.e(tag, msg, t);
			
			if (r != null) {
				r.run();
			}
		}
	}
	
}
