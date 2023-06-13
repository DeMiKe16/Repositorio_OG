package modelo;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import controlador.Controlador;

public class GeneradorExcel {
	public void generar(List<Usuario> seguidores) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Hoja excel");

		String[] headers = new String[] { "Nombre", "Email", "Descripcion" };

		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);

		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		HSSFRow headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; ++i) {
			String header = headers[i];
			HSSFCell cell = headerRow.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);
		}

		for (int i = 0; i < seguidores.size(); ++i) {
			HSSFRow dataRow = sheet.createRow(i + 1);
			String nombre = Controlador.getUnicaInstancia().getLoginUsuario(seguidores.get(i));
			String email = Controlador.getUnicaInstancia().getEmailUsuario(seguidores.get(i));
			String descripcion = Controlador.getUnicaInstancia().getDescripcionUsuario(seguidores.get(i));
			
			dataRow.createCell(0).setCellValue(nombre);
			dataRow.createCell(1).setCellValue(email);
			dataRow.createCell(2).setCellValue(descripcion);
		}
		
		FileOutputStream file = new FileOutputStream("archivos/TablaSeguidores.xls");
        workbook.write(file);
        workbook.close();
        file.close();
	}
}
