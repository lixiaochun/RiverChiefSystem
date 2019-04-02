package common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageUtil {

	private static final long serialVersionUID = -1257947018545327308L;
	private static final String SESSION_KEY_OF_RAND_CODE = "randCode"; // todo 要统一常量
	/**
	 * 
	 */
	private static final int count = 200;

	/**
	 * 定义图形大小
	 */
	private static final int width = 105;
	/**
	 * 定义图形大小
	 */
	private static final int height = 35;
	// private Font mFont = new Font("Arial Black", Font.PLAIN, 15); //设置字体
	/**
	 * 干扰线的长度=1.414*lineWidth
	 */
	private static final int lineWidth = 2;

	public static Map<Object, Object> getImage(HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			// response.setContentType("image/png");

			// 在内存中创建图象
			final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			final Graphics2D graphics = (Graphics2D) image.getGraphics();

			// 设定背景颜色
			graphics.setColor(Color.WHITE); // ---1
			graphics.fillRect(0, 0, width, height);
			// 设定边框颜色
			// graphics.setColor(getRandColor(100, 200)); // ---2
			graphics.drawRect(0, 0, width - 1, height - 1);

			final Random random = new Random();
			// 随机产生干扰线，使图象中的认证码不易被其它程序探测到
			for (int i = 0; i < count; i++) {
				graphics.setColor(getRandColor(150, 200)); // ---3

				final int x = random.nextInt(width - lineWidth - 1) + 1; // 保证画在边框之内
				final int y = random.nextInt(height - lineWidth - 1) + 1;
				final int xl = random.nextInt(lineWidth);
				final int yl = random.nextInt(lineWidth);
				graphics.drawLine(x, y, x + xl, y + yl);
			}

			// 取随机产生的认证码(4位数字)
			final String resultCode = exctractRandCode();
			for (int i = 0; i < resultCode.length(); i++) {
				// 将认证码显示到图象中,调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				// graphics.setColor(new Color(20 + random.nextInt(130), 20 + random
				// .nextInt(130), 20 + random.nextInt(130)));

				// 设置字体颜色
				graphics.setColor(Color.BLACK);
				// 设置字体样式
				// graphics.setFont(new Font("Arial Black", Font.ITALIC, 18));
				graphics.setFont(new Font("Times New Roman", Font.BOLD, 24));
				Graphics2D g2d_word = (Graphics2D) graphics;
				AffineTransform trans = new AffineTransform();
				trans.rotate((20) * 3.14 / 180, 15 * i + 8, 7);
				/* 缩放文字 */
				float scaleSize = random.nextFloat() + 0.8f;
				if (scaleSize > 1f)
					scaleSize = 1f;
				trans.scale(scaleSize, scaleSize);
				g2d_word.setTransform(trans);

				MyDrawString(String.valueOf(resultCode.charAt(i)), 20 * i + 14, 22, 1.5, graphics);
				// 设置字符，字符间距，上边距
				// graphics.drawString(String.valueOf(resultCode.charAt(i)), (23 * i) + 8, 26);
			}

			// 将认证码存入SESSION
			request.getSession().setAttribute("randCode", resultCode);
			String randCode = (String) request.getSession().getAttribute("randCode");
			// 图象生效
			graphics.dispose();

			// 输出图象到页面
			// ImageIO.write(image, "JPEG", response.getOutputStream());

			Base64.Encoder base64 = Base64.getEncoder();
			// 创建字符流
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			// 写入字符流
			ImageIO.write(image, "jpg", bs);
			// 转码成字符串
			String imgsrc = "data:image/jpeg;base64," + base64.encodeToString(bs.toByteArray());
			result.put("imgsrc", imgsrc);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}
		return result;
	}

	private static void MyDrawString(String str, int x, int y, double rate, Graphics g) {
		String tempStr = new String();
		int orgStringWight = g.getFontMetrics().stringWidth(str);
		int orgStringLength = str.length();
		int tempx = x;
		int tempy = y;
		while (str.length() > 0) {
			tempStr = str.substring(0, 1);
			str = str.substring(1, str.length());
			g.drawString(tempStr, tempx, tempy);
			tempx = (int) (tempx + (double) orgStringWight / (double) orgStringLength * rate);
		}
	}

	public static String exctractRandCode() {
		int random = (int) (Math.random() * 10);
		final String randCodeType = Integer.toString(random);
		int randCodeLength = 4;
		if (randCodeType == null) {
			return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
		} else {
			switch (randCodeType.charAt(0)) {
			case '1':
				return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
			case '2':
				return RandCodeImageEnum.LOWER_CHAR.generateStr(randCodeLength);
			case '3':
				return RandCodeImageEnum.UPPER_CHAR.generateStr(randCodeLength);
			case '4':
				return RandCodeImageEnum.LETTER_CHAR.generateStr(randCodeLength);
			case '5':
				return RandCodeImageEnum.ALL_CHAR.generateStr(randCodeLength);

			default:
				return RandCodeImageEnum.ALL_CHAR.generateStr(randCodeLength);
			}
		}
	}

	/**
	 * 描述：
	 */
	private static Color getRandColor(int fc, int bc) { // 取得给定范围随机颜色
		final Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}

		final int r = fc + random.nextInt(bc - fc);
		final int g = fc + random.nextInt(bc - fc);
		final int b = fc + random.nextInt(bc - fc);

		return new Color(r, g, b);
	}
}

/**
 * 验证码辅助类
 */
enum RandCodeImageEnum {
	/**
	 * 混合字符串
	 */
	ALL_CHAR("123456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ"),
	/**
	 * 字符
	 */
	LETTER_CHAR("abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ"),
	/**
	 * 小写字母
	 */
	LOWER_CHAR("abcdefghjkmnpqrstuvwxyz"),
	/**
	 * 数字
	 */
	NUMBER_CHAR("123456789"),
	/**
	 * 大写字符
	 */
	UPPER_CHAR("ABCDEFGHJKMNPQRSTUVWXYZ");
	/**
	 * 待生成的字符串
	 */
	private String charStr;

	/**
	 * @param charStr
	 */
	private RandCodeImageEnum(final String charStr) {
		this.charStr = charStr;
	}

	/**
	 * 生产随机验证码
	 * 
	 * @param codeLength
	 *            验证码的长度
	 * @return 验证码
	 */
	public String generateStr(final int codeLength) {
		final StringBuffer sb = new StringBuffer();
		final Random random = new Random();
		final String sourseStr = getCharStr();

		for (int i = 0; i < codeLength; i++) {
			sb.append(sourseStr.charAt(random.nextInt(sourseStr.length())));
		}

		return sb.toString();
	}

	/**
	 * @return the {@link #charStr}
	 */
	public String getCharStr() {
		return charStr;
	}
}