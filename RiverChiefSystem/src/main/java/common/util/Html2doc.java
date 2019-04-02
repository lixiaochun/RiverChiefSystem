package common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;

/**
 * @author xsw
 * @ClassName: HtmlToWord
 * @Description: TODO(将html文件导出为word)
 * @date 2016-12-28 上午11:41:19
 */
@Service
public class Html2doc {
    /**
     * (srcPath html文件  fileName保存的doc文件)
     *
     * @param
     * @return void    返回类型
     * @author xsw
     * @2016-12-28上午11:47:27
     */
    public void htmlToWord(String srcPath, String fileName) throws Exception {
        ByteArrayInputStream bais = null;
        FileOutputStream fos = null;
        try {
            if (!"".equals(fileName)) {
                File fileDir = new File(fileName);
                if (fileDir.exists()) {
                    String content = readFile(srcPath);
                    byte b[] = content.getBytes();
                    bais = new ByteArrayInputStream(b);
                    POIFSFileSystem poifs = new POIFSFileSystem();
                    DirectoryEntry directory = poifs.getRoot();
                    DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
                    fos = new FileOutputStream(fileName);
                    poifs.writeFilesystem(fos);
                    bais.close();
                    fos.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) fos.close();
            if (bais != null) bais.close();
        }
    }

    /**
     * 读取html文件到字符串
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public String readFile(String filename) throws Exception {
        StringBuffer buffer = new StringBuffer("");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            buffer = new StringBuffer();
            while (br.ready())
                buffer.append((char) br.read());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) br.close();
        }
        return buffer.toString();
    }
}