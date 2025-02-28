package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

/**
 * Classe para gerenciar operações relacionadas a gastos no banco de dados.
 */
public class CompraDAO {


    /**
     * Adiciona um novo gasto ao banco de dados.
     *
     * @param data        Data do gasto (java.sql.Date).
     * @param valor       Valor do gasto.
     * @param idCategoria ID da categoria associada ao gasto.
     * @param idUser      ID do usuário que está adicionando o gasto.
     * @param ehGasto     Indica se é gasto (true) ou receita (false).
     * @param descricao   Descrição do gasto ou receita.
     */
    public void adicionarGasto(Date data, double valor, int idCategoria, int idUser, boolean ehGasto, String descricao) {
        String sql = "INSERT INTO gastos (data, valor, ID_CATEGORIA, ID_USER, EH_GASTO, descricao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Configura os valores dos parâmetros na consulta
            pstmt.setDate(1, data);              // Define a data do gasto
            pstmt.setDouble(2, valor);           // Define o valor do gasto
            pstmt.setInt(3, idCategoria);        // Define o ID da categoria
            pstmt.setInt(4, idUser);             // Define o ID do usuário
            pstmt.setBoolean(5, ehGasto);        // Define se é gasto ou receita
            pstmt.setString(6, descricao);       // Define a descrição

            // Executa a consulta SQL
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println("Gasto adicionado com sucesso! Linhas afetadas: " + linhasAfetadas);

        } catch (SQLException e) {
            // Captura e exibe erros de SQL
            System.err.println("Erro ao adicionar gasto: " + e.getMessage());
        }
    }

    /**
     * Atualiza um gasto existente no banco de dados.
     *
     * @param ID_GASTO    ID do gasto que será atualizado.
     * @param data        Nova data do gasto (java.sql.Date).
     * @param valor       Novo valor do gasto.
     * @param idCategoria Novo ID da categoria associada ao gasto.
     * @param ehGasto     Indica se é gasto (true) ou receita (false).
     * @param descricao   Nova descrição do gasto ou receita.
     */
    public void editarGasto(int ID_GASTO, Date data, double valor, int idCategoria, boolean ehGasto, String descricao) {
        String sql = "UPDATE gastos SET data = ?, valor = ?, ID_CATEGORIA = ?, EH_GASTO = ?, descricao = ? WHERE ID_GASTO = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Configura os valores dos parâmetros na consulta
            pstmt.setDate(1, data);              // Define a nova data do gasto
            pstmt.setDouble(2, valor);           // Define o novo valor do gasto
            pstmt.setInt(3, idCategoria);        // Define o novo ID da categoria
            pstmt.setBoolean(4, ehGasto);        // Define se é gasto ou receita
            pstmt.setString(5, descricao);       // Define a nova descrição
            pstmt.setInt(6, ID_GASTO);           // Define o ID do gasto a ser atualizado

            // Executa a consulta SQL
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println("Gasto atualizado com sucesso! Linhas afetadas: " + linhasAfetadas);

        } catch (SQLException e) {
            // Captura e exibe erros de SQL
            System.err.println("Erro ao editar gasto: " + e.getMessage());
        }
    }
}
