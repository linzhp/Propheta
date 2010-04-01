package data.file.pdf;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.JFreeChart;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;

import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFUtility {

	static Document document = null;
    Font chineseFont_title =null;
	Font chineseFont_content = null;
	BufferedOutputStream out ;
	PdfWriter writer;
        public PDFUtility(String filePath){
        	
        	//A4页面，上下左右页边距为50
        	document = new Document(PageSize.A4, 50, 50, 50, 50);
        	
        	try {
				writer = PdfWriter.getInstance(document, new FileOutputStream(new File(filePath)));
			} catch (FileNotFoundException e1) {				
				e1.printStackTrace();
			} catch (DocumentException e1) {				
				e1.printStackTrace();
			}
    		//预定义所有的中文字体
    		try {
    			this.chineseFont_title= new Font(BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",   
    					 BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 18, Font.BOLD);
    			this.chineseFont_content= new Font(BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",   
    					 BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 11);
    		} catch (DocumentException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
      //打开PDF准备写
    	public void openPDF(){   		
    		try {
    			
    	            //加入PDF附加信息
    	            document.addAuthor("Estimate");   
    	            document.addSubject("Demonstration");   
    	            document.open();  
    		 } catch (Exception de) {   
    	            System.err.println(de.getMessage());   
    	        }      		
    	}
    	//写完后关闭PDF
    	public void closePDF(){
    		
    		document.close();
    		
    	}   	
    	/*
    	 * 添加新页面
    	*/
    	public void addChapter(String title){
    		
    		Paragraph title2 = new Paragraph(title, chineseFont_title);
    		title2.setSpacingAfter(40);
    		title2.setAlignment(1);
    		
    		Chapter chapter = new Chapter(title2,0);
    		chapter.setNumberDepth(0);
    		
    		try {
    			document.add(chapter);
    			//document.add(new Chunk(title));
    		} catch (DocumentException e) {
    			e.printStackTrace();
    		}
    	}
    	/*
    	 * 添加新的段落
    	 * 参数为文字的位置，0左1居中2右
    	*/
    	public void addParagra(int align,String content){
    		
    		Paragraph para = new Paragraph(content, chineseFont_content);
    		para.setAlignment(align);
    		para.setFirstLineIndent(20);
    		try {
    			document.add(para);
    		} catch (DocumentException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	//添加图，4个参数：图，宽，高，缩放百分比
    	public void addChart(JFreeChart chart,int width,int height,double percentage){    		
    		int width_scale = (int) (width*percentage);
    		int height_scale = (int) (height*percentage);   		
            PdfContentByte cb = writer.getDirectContent();
            try {
				Image img = Image.getInstance(cb,chart.createBufferedImage(width,height),1);
				img.scaleAbsolute(width_scale,height_scale);
				img.setAlignment(Image.MIDDLE);
				document.add(img);
			} catch (BadElementException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
            



    		
    	}
}
