package nucleo.model.persistencia.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nucleo.model.negocios.Postagem;
import nucleo.model.persistencia.dao.DAOPostagem;

public class JDBCDAOPostagem extends JDBCDAO implements
		DAOPostagem<Postagem, Integer> {

	public JDBCDAOPostagem() {

	}

	@Override
	public void criar(Postagem objeto) {
		abrirConexao();
		String sql = "INSERT INTO postagem (titulo,conteudo,codBlog) VALUES (?,?,?)";

		try {

			PreparedStatement stmt = getConnection().prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.setString(1, objeto.getTitulo());
			stmt.setString(2, objeto.getConteudo());
			stmt.setInt(3, objeto.getBlog().getCodigo());

			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next())
				objeto.setCodigo(rs.getInt(1));

			stmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			fecharConexao();
		}

	}

	@Override
	public Postagem consultar(Integer id) {
		abrirConexao();
		String PostagemSQL = "Select * from postagem where codigo = ?";

		Postagem p = null;

		try {

			PreparedStatement stmt = getConnection().prepareStatement(
					PostagemSQL);

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				p = new Postagem();

				p.setCodigo(rs.getInt(1));
				p.setTitulo(rs.getString(2));
				p.setConteudo(rs.getString(3));
				p.setBlog(new JDBCDAOBlog().consultar(rs.getInt(4)));

			}

			stmt.close();
			rs.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			fecharConexao();
		}

		return p;
	}

	@Override
	public void alterar(Postagem objeto) {
		abrirConexao();
		String sqlUpdate = "UPDATE postagem SET titulo=?,conteudo=?,codBlog=? WHERE codigo=?";

		try {
			abrirConexao();

			PreparedStatement stmt = getConnection()
					.prepareStatement(sqlUpdate);

			stmt.setString(1, objeto.getTitulo());
			stmt.setString(2, objeto.getConteudo());
			stmt.setInt(3, objeto.getBlog().getCodigo());
			stmt.setInt(4, objeto.getCodigo());

			stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			fecharConexao();
		}
	}

	@Override
	public void deletar(Postagem objeto) {
		abrirConexao();

		String sqlDelete = "DELETE FROM postagem WHERE codigo = ?";

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
	public List<Postagem> getList() {
		abrirConexao();

		String sqlList = "SELECT * FROM postagem";

		List<Postagem> po = null;
		Postagem p = null;

		try {
			PreparedStatement stmt = getConnection().prepareStatement(sqlList);
			ResultSet rs = stmt.executeQuery(sqlList);

			po = new ArrayList<Postagem>();

			while (rs.next()) {
				p = new Postagem();

				p.setCodigo(rs.getInt(1));
				p.setTitulo(rs.getString(2));
				p.setConteudo(rs.getString(3));
				p.setBlog(new JDBCDAOBlog().consultar(rs.getInt(4)));

				po.add(p);

			}

			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			fecharConexao();
		}

		return po;
	}
}
