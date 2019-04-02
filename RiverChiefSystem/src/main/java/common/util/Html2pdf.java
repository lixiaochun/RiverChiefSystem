package common.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Html2pdf {
    /**
     * HTML代码转PDF文档
     *
     * @param content     待转换的HTML代码
     * @param storagePath 保存为PDF文件的路径
     */
    public void parsePdf(String content, String storagePath) {
        FileOutputStream os = null;
        try {
            File file = new File(storagePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new FileOutputStream(file);

            ITextRenderer renderer = new ITextRenderer();
//解决中文支持问题
            ITextFontResolver resolver = renderer.getFontResolver();
            Resource fileRource = new ClassPathResource("simsun.ttf");
            String path = fileRource.getFile().getAbsolutePath();
            resolver.addFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(content);
// 解决图片的相对路径问题,图片路径必须以file开头
// renderer.getSharedContext().setBaseURL("file:/");
            renderer.layout();
            renderer.createPDF(os);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getHtmlCode(List<String> s) {
        StringBuffer html = new StringBuffer();
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
                .append("<head>")
                .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
                .append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>")
                .append("<style type=\"text/css\">img {width: 700px;}</style>")
                .append("</head>")
                .append("<body>");
        for (String str : s) {
            html.append(str.replace("font-family:宋体", "font-family:SimSun"));
        }
        html.append("</body></html>");
        return html.toString();
    }
}
