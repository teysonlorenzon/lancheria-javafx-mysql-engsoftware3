package model.servicos;

import java.io.InputStream;
import java.util.List;

import db.DB;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class Relatorio {
	public static void chamarRelatorios(String caminho) {

		try {
			InputStream fonte = Relatorio.class.getResourceAsStream(caminho);
			
			JasperReport report = JasperCompileManager.compileReport(fonte);
			JasperPrint print = JasperFillManager.fillReport(report, null, DB.getConnection());
			JasperExportManager.exportReportToPdf(print);
			

		} catch (JRException e) {
			System.err.println(e.getMessage());
		}
	}
}