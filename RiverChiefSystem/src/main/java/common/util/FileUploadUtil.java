
package common.util;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import sun.misc.BASE64Decoder;

public class FileUploadUtil {

	Constant constant = new Constant();

	public Map<Object, Object> fileuploaduilfuc(HttpServletRequest request, String targetfolder) {

		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload uploadservlet = new ServletFileUpload(factory);
			uploadservlet.setHeaderEncoding("UTF-8");
			if (!ServletFileUpload.isMultipartContent(request)) {
				result.put("result", 4);
			}

			@SuppressWarnings("unchecked")
			List<FileItem> list = uploadservlet.parseRequest(request);
			if (list.size() != 0) {
				Map<Object, Object> info = canshu(list);
				String savePath = constant.getProperty("path") + targetfolder;
				mkfiledir(savePath);
				List<Map<Object, Object>> fileinfo = uploadfile(list, savePath, targetfolder);
				result.put("info", info);
				result.put("fileinfo", fileinfo);
				result.put("result", 1);
			} else {
				result.put("result", 4);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> canshu(List<FileItem> list) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		for (FileItem item : list) {
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = null;
				try {
					value = item.getString("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result.put(name, value);
			}
		}
		return result;
	}

	public List<Map<Object, Object>> uploadfile(List<FileItem> list, String savePath, String targetfolder) {

		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> resultsmall = new HashMap<Object, Object>();
		List<Map<Object, Object>> resultList = new ArrayList<Map<Object, Object>>();
		for (FileItem item2 : list) {
			if (!item2.isFormField()) {
				String filename = item2.getName();
				InputStream in;
				try {
					in = item2.getInputStream();
					Date now = new Date();
					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
					String name = dateFormat1.format(now) + (int) (Math.random() * 100);

					String fileurl = savePath + "/" + filename.split("\\.")[0] + dateFormat1.format(now) + "." + filename.split("\\.")[1];
					String url = targetfolder + "/" + filename.split("\\.")[0] + dateFormat1.format(now) + "." + filename.split("\\.")[1];
					if (filename.split("\\.")[1].equals("bmp") || filename.split("\\.")[1].equals("emf") || filename.split("\\.")[1].equals("gif") || filename.split("\\.")[1].equals("jpeg")
							|| filename.split("\\.")[1].equals("jpg") || filename.split("\\.")[1].equals("pbm") || filename.split("\\.")[1].equals("pcx") || filename.split("\\.")[1].equals("pgm")
							|| filename.split("\\.")[1].equals("pic") || filename.split("\\.")[1].equals("pict") || filename.split("\\.")[1].equals("png") || filename.split("\\.")[1].equals("ppm")
							|| filename.split("\\.")[1].equals("psd") || filename.split("\\.")[1].equals("psp") || filename.split("\\.")[1].equals("tga") || filename.split("\\.")[1].equals("tiff")
							|| filename.split("\\.")[1].equals("wmf")) {
						String smallfileurl = savePath + "/" + name + "." + filename.split("\\.")[1];
						String smallurl = targetfolder + "/" + name + "." + filename.split("\\.")[1];
						BufferedImage image = ImageIO.read(item2.getInputStream());
						resultsmall = zoomImage(image, 60, 60, smallfileurl, smallurl, now);// 缩小图片
						result.putAll(resultsmall);
						result.put("type", "image");
					} else {
						result.put("type", "file");
					}
					FileOutputStream out = new FileOutputStream(fileurl);
					byte buffer[] = new byte[1024];
					int len = 0;

					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					item2.delete();
					result.put("tail", filename.split("\\.")[1]);
					result.put("filename", name);
					result.put("url", url);
					result.put("fileurl", fileurl);
					result.put("date", now);
					resultList.add(result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}

	/*
	 * 图片缩放,w，h为缩放的目标宽度和高度 src为源文件目录，dest为缩放后保存目录
	 */
	public static Map<Object, Object> zoomImage(BufferedImage bufferImage, int w, int h, String savePath, String targetfolder, Date now) throws Exception {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {

			Image image = (Image) bufferImage;
			BufferedImage bufferImage2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			bufferImage2.getGraphics().drawImage(image, 0, 0, w, h, null);
			FileOutputStream out = new FileOutputStream(savePath); // 输出到文件流
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(bufferImage2); // JPEG编码
			out.close();

			result.put("smallurl", targetfolder);
			result.put("smallfileurl", savePath);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	// Image转BufferedImage
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}
		// 加载所有像素
		image = new ImageIcon(image).getImage();
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			int transparency = Transparency.OPAQUE;
			// 创建buffer图像
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			e.printStackTrace();
		}
		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}
		// 复制
		Graphics g = bimage.createGraphics();
		// 赋值
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}

	public void mkfiledir(String savePath) {

		File file = new File(savePath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}

	public Map<Object, Object> uploadfileBase64(HttpServletRequest request, String beforeimgurl, String target) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> resultsmall = new HashMap<Object, Object>();
		String savePath = constant.getProperty("path") + target;
		// String savePath =
		// request.getSession().getServletContext().getRealPath(target);
		if (beforeimgurl == null || beforeimgurl.equals("")) {
			result.put("result", 0);
		} else {
			OutputStream out = null;
			try {
				BASE64Decoder base64 = new BASE64Decoder();
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String filename = dateFormat1.format(date) + (int) (Math.random() * 100);
				String imgFilePath = savePath + "/" + filename + ".jpg";
				mkfiledir(savePath);
				String url = target + "/" + filename + ".jpg";
				String test = savePath + "/" + filename + ".txt";
				OutputStream file = new FileOutputStream(test);
				file.write(beforeimgurl.getBytes());
				file.flush();
				file.close();

				byte[] b = base64.decodeBuffer(beforeimgurl);

				// ByteArrayInputStream bais = new ByteArrayInputStream(b);
				// RenderedImage bi1 = ImageIO.read(bais);
				// File file = new File(imgFilePath);
				//
				// ImageIO.write(bi1, "jpg", file);
				//
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}

				// ByteArrayInputStream in = new ByteArrayInputStream(b); //将b作为输入流；
				// BufferedImage image = ImageIO.read(in);
				// resultsmall = zoomImage(image, 60, 60, savePath, target,date);

				out = new FileOutputStream(imgFilePath);
				out.write(b);
				out.flush();
				out.close();

				result.put("result", 1);
				result.put("fileurl", imgFilePath);
				result.put("url", url);
				result.put("smallurl", url);
				result.put("smallfileurl", imgFilePath);
				result.put("type", "image");
				result.put("filename", filename);
				result.putAll(resultsmall);

			} catch (Exception e) {
				e.printStackTrace();
				result.put("result", 0);
			}
		}
		return result;
	}

}
