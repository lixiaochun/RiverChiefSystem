package basemanage.util;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
   
import org.apache.commons.collections.MapUtils;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;  
import org.apache.poi.xwpf.usermodel.XWPFParagraph;  
import org.apache.poi.xwpf.usermodel.XWPFRun;  
import org.apache.poi.xwpf.usermodel.XWPFTable;  
import org.apache.poi.xwpf.usermodel.XWPFTableCell;  
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;  
   
 
public class WordToPDFUtil {  
	
	/**
	 * word文件转成pdf格式
	 * @param source
	 * @param target
	 */
	public static void WordToPDF(FileInputStream source,FileOutputStream target) {
		try {
	        Map<String, String> params = new HashMap<String, String>();
	        PdfOptions options = PdfOptions.create();
	        wordConverterToPdf(source, target, options, params);
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
   

    /** 
     * 将word文档， 转换成pdf, 中间替换掉变量 
     * @param source 源为word文档， 必须为docx文档 
     * @param target 目标输出 
     * @param params 需要替换的变量 
     * @throws Exception 
     */  
    public static void wordConverterToPdf(InputStream source,  
            OutputStream target, Map<String, String> params) throws Exception {  
        wordConverterToPdf(source, target, null, params);      
    }  
   
    /** 
     * 将word文档， 转换成pdf, 中间替换掉变量 
     * @param source 源为word文档， 必须为docx文档 
     * @param target 目标输出 
     * @param params 需要替换的变量 
     * @param options PdfOptions.create().fontEncoding( "windows-1250" ) 或者其他 
     * @throws Exception 
     */  
    public static void wordConverterToPdf(InputStream source, OutputStream target,   
            PdfOptions options, Map<String, String> params) throws Exception {  
         XWPFDocument doc = new XWPFDocument(source);  
         paragraphReplace(doc.getParagraphs(), params);  
         for (XWPFTable table : doc.getTables()) {  
           for (XWPFTableRow row : table.getRows()) {  
               for (XWPFTableCell cell : row.getTableCells()) {  
                   paragraphReplace(cell.getParagraphs(), params);  
               }  
           }  
       } 
		options.fontProvider(new ITextFontRegistry() {
			public Font getFont(String familyName, String encoding, float size, int style, Color color) {
				try {
					//Resource fileRource = new ClassPathResource("simsun.ttf");
					//String path = fileRource.getFile().getAbsolutePath();
					String path="D://test/simsun.ttf";
					BaseFont bfChinese = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
					Font fontChinese = new Font(bfChinese, size, style, color);
					if (familyName != null)
						fontChinese.setFamily(familyName);
					return fontChinese;
				} catch (Throwable e) {
					e.printStackTrace();
					return ITextFontRegistry.getRegistry().getFont(familyName, encoding, size, style, color);
				}
			}
		
		});

       PdfConverter.getInstance().convert(doc, target, options);  
    }  
       
    /** 替换段落中内容 */  
    private static void paragraphReplace(List<XWPFParagraph> paragraphs, Map<String, String> params) {  
        if (MapUtils.isNotEmpty(params)) {  
            for (XWPFParagraph p : paragraphs){  
                for (XWPFRun r : p.getRuns()){  
                    String content = r.getText(r.getTextPosition());  
                    if(StringUtils.isNotEmpty(content) && params.containsKey(content)) {  
                        r.setText(params.get(content), 0);  
                    }  
                }  
            }  
        }  
    }  


    public static void main(String[] args) {
        String filepath = "D:/test/南平智慧河长APP操作手册.docx";
        String outpath = "D:/test/1234.pdf";
        InputStream source;
		OutputStream target;
		try {
			source = new FileInputStream(filepath);
			target = new FileOutputStream(outpath);
	        Map<String, String> params = new HashMap<String, String>();
	        PdfOptions options = PdfOptions.create();
	        wordConverterToPdf(source, target, options, params);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
 
	}
}  