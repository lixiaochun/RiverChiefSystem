package onemap.controller;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

@Controller
@RequestMapping({ "/QRCodeController" })
public class QRCodeController {
	
	// 二维码颜色
	private static final int BLACK = 0xFF000000;
	// 二维码颜色
	private static final int WHITE = 0xFFFFFFFF;

	@RequestMapping(value = { "/downloadQRCodeImage" }, method = {
			RequestMethod.GET }, produces = "application/octet-stream; charset=utf-8")
	public void downloadQRCode(HttpServletRequest request, HttpServletResponse response) {

		response.setHeader("Content-Type", "image/jped");
		int width = Integer.valueOf(request.getParameter("width"));
		int height = Integer.valueOf(request.getParameter("height"));
		String text = request.getParameter("text");	 
		try {
			BufferedImage image = zxingCodeCreate(text, width, height);
			ImageIO.write(image, "jpeg", response.getOutputStream());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @param text 二维码文本
	 * @param width宽度
	 * @param height高度
	 */
	public static BufferedImage zxingCodeCreate(String text, int width, int height) throws Exception {

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
