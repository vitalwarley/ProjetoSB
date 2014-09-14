package nucleo.model.persistencia.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nucleo.model.negocios.Blog;
import nucleo.model.persistencia.dao.DAOBlog;


/**
 * Classe para criacao de Blogs
 * @author Raiane
 */
public class JDBCDAOBlog extends JDBCDAO implements DAOBlog<Blog, Integer> {

	
	/**
	 * Método construtor da classe JDBCDAOBlog
	 * @author Raiane
	 */
	public JDBCDAOBlog() {

	}

	@Override
	
	/**
	 * Método para criação de um blog
	 * @param objeto Blog - parâmetro passado ao método blog
	 * @author raiane
	 */

	public void criar(Blog objeto) {

		abrirConexao();
		String sql = "INSERT INTO blog (titulo,descricao,imagemFundo,autorizacaoNormal,autorizacaoAnonimo,login) VALUES (?,?,?,?,?,?)";

		try {
			PreparedStatement stmt = getConnection().prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.setString(1, objeto.getTitulo());
			stmt.setString(2, objeto.getDescricao());
			stmt.setString(3, objeto.getImagemFundo());
			stmt.setBoolean(4, objeto.isAutorizaComentario());
			stmt.setBoolean(5, objeto.isAutorizaComentarioAnonimo());
			stmt.setString(6, objeto.getUsuario().getLogin());

			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next())
				objeto.setCodigo(rs.getInt(1));
			
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			fecharConexao();
		}
	}

	@Override
	
	/**
	 * Método para consulta de um Blog
	 * @param id Integer - identificador de um Blog
	 * @return Blog b - retorna os dados de um Blog
	 * @author raiane
	 */
	
	public Blog consultar(Integer id) {

		abrirConexao();
		String selectSQL = "SELECT * FROM blog WHERE codigo = ?";
//		String selectSQLAssinantes = "SELECT * FROM assinatura WHERE codBlog = ?";
		Blog b = null;

		try {
			// recuperando dados do blog
			PreparedStatement stmt = getConnection()
					.prepareStatement(selectSQL);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

//			PreparedStatement stmtAssinantes = getConnection().prepareStatement(selectSQLAssinantes);
//			stmtAssinantes.setInt(1, id);
//			ResultSet rsA = stmtAssinantes.executeQuery();
			
			if (rs.next()) {
				b = new Blog();

				b.setCodigo(rs.getInt(1));
				b.setTitulo(rs.getString(2));
				b.setDescricao(rs.getString(3));
				b.setImagemFundo(rs.getString(4));
				b.setAutorizaComentario(rs.getBoolean(5));
				b.setAutorizaComentarioAnonimo(rs.getBoolean(6));
				b.setUsuario(new JDBCDAOUsuario().consultar(rs.getString("login")));
				
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			fecharConexao();
		}
		return b;
	}

	@Override
	
	/**
	 * Método que altera os dados de um Blog
	 * @param objeto Blog - parâmetro passado ao método alterar
	 * @author raiane
	 */
	
	public void alterar(Blog objeto) {

		abrirConexao();
		String sqlUpdate = "UPDATE blog SET titulo=?,descricao=?,imagemFundo=?,autorizacaoNormal=?,autorizacaoAnonimo=? WHERE codigo=?";
		
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(sqlUpdate);

			
			stmt.setString(1, objeto.getTitulo());
			stmt.setString(2, objeto.getDescricao());
			stmt.setString(3, objeto.getImagemFundo());
			stmt.setBoolean(4, objeto.isAutorizaComentario());
			stmt.setBoolean(5, objeto.isAutorizaComentarioAnonimo());
			stmt.setInt(6, objeto.getCodigo());
			
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			fecharConexao();
		}
	}

	@Override
	
	/**
	 * Método para exclusão de um blog
	 * @param objeto Blog - parâmetro passado ao método deletar
	 * @author raiane
	 */
	
	public void deletar(Blog objeto) {

		abrirConexao();
		String sqlDelete = "DELETE FROM blog WHERE codigo = ?";

		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(sqlDelete);

			stmt.setInt(1, objeto.getCodigo());

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			fecharConexao();
		}

	}

	@Override
	
	/**
	 * Método para criação de uma lista de blogs
	 * @return List<Blog> bu - retorna uma lista de blogs
	 * @author raiane
	 */
	
	public List<Blog> getList() {

		abrirConexao();
		String sqlList = "SELECT * FROM blog";

		List<Blog> bu = null;
		Blog b = null;

		try {
			PreparedStatement stmt = getConnection().prepareStatement(sqlList);
			ResultSet rs = stmt.executeQuery(sqlList);
			bu = new ArrayList<Blog>();
			
			while (rs.next()) {
				b = new Blog();
			

				b.setCodigo(rs.getInt(1));
				b.setTitulo(rs.getString(2));
				b.setDescricao(rs.getString(3));
				b.setImagemFundo(rs.getString(4));
				b.setAutorizaComentario(rs.getBoolean(5));
				b.setAutorizaComentarioAnonimo(rs.getBoolean(6));
				b.setUsuario(new JDBCDAOUsuario().consultar(rs.getString("login")));

				bu.add(b);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			fecharConexao();
		}

		return bu;
	}

}
