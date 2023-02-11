package com.taller.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.taller.entidades.Empresa;

public class EmpresaExporterPDF {
	
	private List<Empresa> listaEmpresa;

	public EmpresaExporterPDF(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}
	
	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		
		celda.setPhrase(new Phrase("RUC", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("DESCRIPCION", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("CORREO", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("DIRECCION", fuente));
		tabla.addCell(celda);
		
	}
	
	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for (Empresa e : listaEmpresa) {
			tabla.addCell(String.valueOf(e.getIdEmpresa()));
			tabla.addCell(e.getDescripcion());
			tabla.addCell(e.getEmail());
			tabla.addCell(e.getDireccion());
		}
	}
	
	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		
		documento.open();
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLUE);
		fuente.setSize(18);
		
		Paragraph titulo = new Paragraph("Lista de Empleados", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		PdfPTable tabla = new PdfPTable(4);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] {1f, 2.3f, 2.3f, 6f});
		tabla.setWidthPercentage(110);
		
		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);
		
		documento.add(tabla);
		documento.close();
	}

}
