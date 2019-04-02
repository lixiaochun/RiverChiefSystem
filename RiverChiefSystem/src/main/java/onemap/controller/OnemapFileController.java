package onemap.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import onemap.mapper.PublicsignsMapper;
import usermanage.service.UserService;

@Controller
@RequestMapping({ "/OnemapFileController" })
public class OnemapFileController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PublicsignsMapper publicSignsMapper;

	/**
	 * 下载公示牌图片
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/downloadPublicsignsFile" }, method = {
			RequestMethod.GET }, produces = "application/octet-stream; charset=utf-8")
	public void downloadPublicsignsFile(HttpServletRequest request, HttpServletResponse response) {
	
		String fontStr="宋体";
		
		if (request.getParameter("id") == null) {
			return;
		}
		int id = Integer.valueOf(request.getParameter("id"));
		try {
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition",
					"attachment;filename=" + URLEncoder.encode("publicsigns.png", "UTF-8"));
			Map<Object, Object> data = publicSignsMapper.findPublicsignsById(id);

			String path = request.getSession().getServletContext().getRealPath("/");
			File file = new File(path+"/WEB-INF/classes/onemap/controller/publicsigns.png");
			BufferedImage bufferedImage = ImageIO.read(file);
			ImageIcon imageIcon = new ImageIcon(path+"/WEB-INF/classes/onemap/controller/publicsigns.png");
			Image image = imageIcon.getImage();
			// 获取图片的长宽
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			System.out.println("width:" + width + "   height:" + height);
			BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = newImg.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			
			Font font1=new Font(fontStr,Font.BOLD,32);
			g.setFont(font1);
			g.setColor(Color.RED);
			String title = data.get("name") != null ? (String) data.get("name"):"";
			if (title != "") {
				int titleLength = getStringLength(title,g);
				int startX = width/2-titleLength/2;//标题起始位置
				int startY = 60;
				g.drawString(title, startX, startY);
				
			}
			Font font2=new Font(fontStr,Font.PLAIN,16);
			g.setFont(font2);
			g.setColor(Color.WHITE);
			if (data.get("river_name") != null)
				g.drawString(String.valueOf(data.get("river_name")), 125, 106);
			if (data.get("river_start") != null)
				g.drawString(String.valueOf(data.get("river_start")), 125, 133);
			if (data.get("river_end") != null)
				g.drawString(String.valueOf(data.get("river_end")), 125, 162);
			if (data.get("river_length") != null)
				g.drawString(String.valueOf(data.get("river_length")) + " 公里", 125, 189);
			if (data.get("rivermanager") != null) {
				String rivermanagerAndTelphone= String.valueOf(data.get("rivermanager")) + "  " + String.valueOf(data.get("manager_telphone"));
				g.drawString(rivermanagerAndTelphone, 130, 216);
			}
			if (data.get("qu_riverchief") != null)
				g.drawString(String.valueOf(data.get("qu_riverchief")), 500, 106);
			if (data.get("zhen_riverchief") != null)
				g.drawString(String.valueOf(data.get("zhen_riverchief")), 500, 133);
			if (data.get("zhen_telphone") != null)
				g.drawString(String.valueOf(data.get("zhen_telphone")), 500, 162);
			if (data.get("target") != null)
				g.drawString(String.valueOf(data.get("target")), 550, 189);
			if (data.get("duty") != null)
				drawMultitLineString(String.valueOf(data.get("duty")), g, 160, 258, 40,700);
			if (data.get("rivermanager_duty") != null)
				drawMultitLineString(String.valueOf(data.get("rivermanager_duty")), g, 210, 350,40,700);
			
			BufferedImage qrcodeImage=null;
			if (data.get("qrcode") != null && !String.valueOf(data.get("qrcode")).equals("")) {
				System.out.println("qrcode="+String.valueOf(data.get("qrcode")));
				qrcodeImage=zxingCodeCreate(String.valueOf(data.get("qrcode")), 100, 100);
			}
			g.drawImage(qrcodeImage, null, 600, 320);
			
			OutputStream os = response.getOutputStream();
			ImageIO.write(newImg, "png", os);
			os.close();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 计算字符串占多少像素长度
	public static int getStringLength(String text, Graphics2D g) {
		int length = g.getFontMetrics(g.getFont()).charsWidth(text.toCharArray(), 0, text.length());
		return length;
	}

	// 计算字符串占多少像素高度
	public static int getStringHeight(Graphics2D g) {
		int height = g.getFontMetrics(g.getFont()).getHeight();
		return height;
	}
	
	/**
	 *  填写单行字符串
	 * @param text 填写字符串段落文本
	 * @param g 画笔
	 * @param startX  startY 起始位置
	 */
	public static void drawLineString(String text, Graphics2D g, int startX, int startY) {	
		g.drawString(text, startX, startY);

	}
	
	/**
	 * 填写多行字符串
	 * @param text 填写字符串段落文本
	 * @param g 画笔
	 * @param x1    第一行起始位置
	 * @param start 每一行起始位置
	 * @param end 每一行结束位置
	 * @return
	 */
	public static int drawMultitLineString(String text, Graphics2D g, int x1,int y1,int start,int end) {
		int height = 22;  //每行高度
		int temp = 0;
		int x = x1;
		int y = y1;
		for (int i = 0; i < text.length(); i++) {
			String nextText = text.substring(temp, i);
			if (getStringLength(nextText, g) > end - x) {
				g.drawString(nextText, x, y);
				x = start;
				y += height;
				temp = i;
			}
		}
		g.drawString(text.substring(temp, text.length()), x, y);
		return 0;
	}
	
	/**
	 * 根据文本生成二维码图片
	 * @param text二维码文本
	 * @param width宽度
	 * @param height高度
	 */
	public static BufferedImage zxingCodeCreate(String text, int width, int height) throws Exception {
		// 二维码颜色
		int BLACK = 0xFF000000;
		// 二维码颜色
		int WHITE = 0xFFFFFFFF;
		Map<EncodeHintType, String> his = new HashMap<EncodeHintType, String>();
		// 设置编码字符集
		his.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// 1、生成二维码
		BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);
		// 2、获取二维码宽高
		int codeWidth = encode.getWidth();
		int codeHeight = encode.getHeight();
		// 3、将二维码放入缓冲流
		BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < codeWidth; i++) {
			for (int j = 0; j < codeHeight; j++) {
				// 4、循环将二维码内容定入图片
				image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
			}
		}
		return image;
	}


}
