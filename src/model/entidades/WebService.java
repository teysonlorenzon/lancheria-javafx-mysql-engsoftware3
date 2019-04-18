package model.entidades;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import itg.util.Alertas;
import javafx.scene.control.Alert.AlertType;

public class WebService implements Serializable {

	private static final long serialVersionUID = 1L;


	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;

	public WebService() {
	}

	public WebService(String logradouro, String bairro, String cidade, String uf) {
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}




	@Override
	public String toString() {
		return "WebService [logradouro=" + logradouro + ", bairro=" + bairro + ", cidade=" + cidade + ", uf=" + uf
				+ "]";
	}
	
	 public  void buscarCep(String cep) 
	    {
	        String json;        

	        try {
	            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
	            URLConnection urlConnection = url.openConnection();
	            InputStream is = urlConnection.getInputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	            
	           

	         
	            StringBuilder jsonSb = new StringBuilder();

	            br.lines().forEach(l -> jsonSb.append(l.trim()));
	            json = jsonSb.toString();
	            	            	            
	            json = json.replaceAll("[{},:]", "");
	            json = json.replaceAll("\"", "\n");  
	            
	            String array[] = new String[30];
	            array = json.split("\n");
	            	                               
	            
	            logradouro = array[7];            
	            bairro = array[15];
	            cidade = array[19]; 
	            uf = array[23];
	            
	            
	        } catch (Exception e) {
	           // Alertas.showAlert("Erro", null, "Cep inexistente!", AlertType.ERROR);;
	        }
	    }
	

}
