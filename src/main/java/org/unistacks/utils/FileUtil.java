//package org.unistacks.utils;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.log4j.Logger;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * 文件处理工具类
// *
// */
//public class FileUtil {
//
//	private static Logger logger = Logger.getLogger(FileUtil.class);
//
//	/**
//	 * 文件下载
//	 * @param response
//	 * @param inputStream
//	 * @param fileName
//	 * @throws IOException
//	 */
//    public void downloadFile(HttpServletResponse response, InputStream inputStream, String fileName) throws IOException {
//        logger.info("downloading file: " + fileName);
//        response.setContentType("application/force-download");
//        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//        IOUtils.copy(inputStream, response.getOutputStream());
//        response.flushBuffer();
//        inputStream.close();
//    }
//
//}
