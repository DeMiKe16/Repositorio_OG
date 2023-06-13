package modelo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import controlador.Controlador;

public class GeneradorPDF {
	public void generar(List<Usuario> seguidores) throws DocumentException, MalformedURLException, IOException {
		Usuario usuario = Controlador.getUnicaInstancia().getUsuarioActual();
		FileOutputStream archivo = new FileOutputStream("archivos/TablaSeguidores.pdf");
        Document documento = new Document();
        PdfWriter.getInstance(documento, archivo);
        documento.open();
        
        PdfPTable tabla = new PdfPTable(3);
        tabla.setSpacingBefore(10f);
        tabla.addCell(new PdfPCell(new Paragraph("Nombre")));
        tabla.addCell(new PdfPCell(new Paragraph("Email")));
        tabla.addCell(new PdfPCell(new Paragraph("Descripción")));
        
        for(Usuario u: seguidores) {
        	tabla.addCell(new PdfPCell(new Paragraph(Controlador.getUnicaInstancia().getLoginUsuario(u))));
            tabla.addCell(new PdfPCell(new Paragraph(Controlador.getUnicaInstancia().getEmailUsuario(u))));
            tabla.addCell(new PdfPCell(new Paragraph(Controlador.getUnicaInstancia().getDescripcionUsuario(u))));
        }
        Image imagen = Image.getInstance("src//imagenes/camarax64.png");
        documento.add(imagen);
        documento.add(new Paragraph("Tabla de seguidores de: " + Controlador.getUnicaInstancia().getLoginUsuario(usuario)));
        documento.add(tabla);
        documento.close();
	}
}
