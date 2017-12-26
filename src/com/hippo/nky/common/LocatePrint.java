package com.hippo.nky.common;

import java.applet.Applet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


/*
 * 需要一个打印服务对象。这可通过三种方式实现：在jdk1.4之前的版本，必须要实现java.awt.print.Printable
 * 接口或通过Toolkit.getDefaultToolkit().getPrintJob来获取打印服务对象；在jdk1.4中则还可以通过javax.print.PrintSerivceLookup来查找定位一个打印服务对象。 
 *需要开始一个打印工作。这也有几种实现方法：在jdk1.4之前可以通过java.awt.print.PrintJob（jdk1.1提供的，现在已经很少用了）调用print或printAll方法开始打印工作；
 *也可以通过java.awt.print.PrinterJob的printDialog显示打印对话框，然后通过print方法开始打印；在jdk1.4中则可以通过javax.print.ServiceUI的printDialog显示
 打印对话框，然后调用print方法开始一个打印工作。
 */
/**
 * java定位打印，把打印内容打到指定的地方。
 */
public class LocatePrint extends Applet {
	private static final long serialVersionUID = 1L;
	private java.net.URL url = null;

	@Override
	public void init() {
		try {
			String strUrl = this.getParameter("pdf_url");
			if (strUrl != null) {
				url = new java.net.URL(this.getCodeBase(), strUrl);
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace(); 
		}
	}

	@Override
	public void start() {
		if (url != null) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// 构建打印请求属性集
					PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
					pras.add(OrientationRequested.PORTRAIT);// 纵向打印
					// 设置纸张大小,也可以新建MediaSize类来自定义大小
					System.out.println("开始设置打印纸张大小");
//					MediaSize ms = new MediaSize(70, 70, Size2DSyntax.MM,
//							MediaSizeName.ISO_A4);// 100,110f MediaSizeName.INVOICE 发票
					//MediaSizeName MediaSizeName = new MediaSizeName();
					//MediaSize ms = new MediaSize(70, 70, Size2DSyntax.MM,MediaSizeName.ISO_A4);
					
//					MediaSizeName paper = null;
//					MediaSize ms = MediaSize.getMediaSizeForName(paper);
//					pras.add(ms.getMediaSizeName());
					pras.add(MediaSizeName.INVOICE); 
					System.out.println("完成设置打印纸张大小");
					// 设置打印格式，因为未确定文件类型，这里选择AUTOSENSE
					DocFlavor flavor = DocFlavor.URL.AUTOSENSE;// JPEG;
					// 定位默认的打印服务
					PrintService defaultService = PrintServiceLookup
							.lookupDefaultPrintService();
					DocPrintJob job = defaultService.createPrintJob();// 创建打印作业
					DocAttributeSet das = new HashDocAttributeSet();
					Doc doc = new SimpleDoc(url, flavor, das);// 建立打印文件格式

					try {
						job.print(doc, pras);
					} catch (PrintException e) {
						e.printStackTrace();
					}// 进行文件的打印
				}
			});
			thread.start();
		}
	}

	
	public byte[] autoPrintPdf(byte[] pdf_byte) {  
        ByteArrayOutputStream bos=null;  
        try {  
            PdfReader reader = new PdfReader(pdf_byte);  
            bos = new ByteArrayOutputStream();  
            PdfStamper ps = new PdfStamper(reader, bos);  
            StringBuffer script = new StringBuffer();   
            script.append("this.print({bUI: false,bSilent: true,bShrinkToFit: false});").append("\r\nthis.closeDoc();");  
            ps.addJavaScript(script.toString());  
//          ps.addJavaScript("this.print(true);");  
            ps.setFormFlattening(true);  
            ps.close();           
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        return bos.toByteArray();  
    }  
	
	public static void main(String[] args) throws IOException {
		byte[] buffer = null;  
		LocatePrint LocatePrint = new LocatePrint();
		//java.net.URL url = new java.net.URL("http://192.168.100.136:8080/framework3.3.2/pdf/20131223104515_.pdf");
		File file = new File("c:/20131223141940_.pdf");
		FileInputStream fis = new FileInputStream(file);  
		  ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
          byte[] b = new byte[1000];  
          int n;  
          while ((n = fis.read(b)) != -1) {  
              bos.write(b, 0, n);  
          }  
          fis.close();  
          bos.close();  
          buffer = bos.toByteArray();  
		LocatePrint.autoPrintPdf(buffer);
	}
}
