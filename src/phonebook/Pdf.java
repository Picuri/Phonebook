
package phonebook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;

public class Pdf {
    
    public void pdfGenerator(String fileName, ObservableList<Person> DATA) {
    Document document = new Document();
    //logó
    try {
        PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));
        document.open();
        Image image = Image.getInstance(getClass().getResource("/logo.jpg"));
        image.scaleToFit(200,86);
        image.setAbsolutePosition(200, 750);
        document.add(image);
        // tartalom hozzáadása
     //   document.add(new Paragraph("\n\n\n\n\n\n\n"+text, FontFactory.getFont("betutipus",BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
     document.add(new Paragraph("\n\n\n\n\n\n"));
     //tálázat
     
     float[] columnWidths = {3,3,4,4};
     PdfPTable table = new PdfPTable(columnWidths);
     table.setWidthPercentage(100);
     PdfPCell cell = new PdfPCell(new Phrase ("Kontaktlista"));
     cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
     cell.setColspan(4);
     table.addCell(cell);
     
     table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
     table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
     table.addCell("Vezetéknév");
     table.addCell("Keresztnév");
     table.addCell("E-mail");
     table.addCell("Telefonszám");
     table.setHeaderRows(1);
     
     table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
     table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
     
     for (int i = 1;i<=DATA.size();i++) {
         Person actualPerson = DATA.get(i-1);
         
         table.addCell(actualPerson.getLastName());
         table.addCell(actualPerson.getFirstName());
         table.addCell(actualPerson.getEmail());
         table.addCell(actualPerson.getMobile());
     }
     
     document.add(table);
    //aláírás
       Chunk signature = new Chunk("\n\n Generálva a Phonebook alkalmazással");
       Paragraph base = new Paragraph(signature);
       document.add(base);
       
        
    }catch (Exception e) {
    e.printStackTrace();
    }
   document.close();
   }
}
