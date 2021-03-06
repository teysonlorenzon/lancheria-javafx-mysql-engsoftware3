package model.mysql;

import db.DB;
import model.mysql.imp.CadastroCategoriasJDBC;
import model.mysql.imp.CadastroClientesJDBC;
import model.mysql.imp.CadastroEntradaJDBC;
import model.mysql.imp.CadastroFornecedoresJDBC;
import model.mysql.imp.CadastroFuncionariosJDBC;
import model.mysql.imp.CadastroLanchesJDBC;
import model.mysql.imp.CadastroProdutosJDBC;
import model.mysql.imp.EstoqueJDBC;
import model.mysql.imp.UsuariosLoginJDBC;
import model.mysql.imp.VendasJDBC;

public class ConexaoTabelas {

	public static UsuariosLoginMYSQL criarUsuariosLoginMYSQL() {
		return new UsuariosLoginJDBC(DB.getConnection());
	}
	
	public static CadastroClientesMYSQL criarCadastroClientesMYSQL()
	{
		return new CadastroClientesJDBC(DB.getConnection());
	}
	
	public static CadastroFucionariosMYSQL criarCadastroFuncionariosMYSQL()
	{
		return new CadastroFuncionariosJDBC(DB.getConnection());
	}
	
	public static CadastroFornecedoresMYSQL criarCadastroFornecedoresMYSQL()
	{
		return new CadastroFornecedoresJDBC(DB.getConnection());
	}
	
	public static CadastroCategoriasMYSQL criarCadastroCategoriasMYSQL()
	{
		return new CadastroCategoriasJDBC(DB.getConnection());
	}
	
	public static CadastroProdutosMYSQL criarCadastroProdutosMYSQL()
	{
		return new CadastroProdutosJDBC(DB.getConnection());
	}
	
	public static CadastroLanchesMYSQL criarCadastroLanchesMYSQL(){
		return new CadastroLanchesJDBC(DB.getConnection());
	}
	
	public static CadastroEntradaMYSQL criarCadastroEntradaMYSQL(){
		return new CadastroEntradaJDBC(DB.getConnection());
	}
	
	public static EstoqueMYSQL criarEstoqueMYSQL(){
		return new EstoqueJDBC(DB.getConnection());
	}
	public static VendasMYSQL criarVendasMYSQL(){
		return new VendasJDBC(DB.getConnection());
	}
	
	
}
