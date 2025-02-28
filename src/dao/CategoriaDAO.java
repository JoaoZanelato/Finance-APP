package dao;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Esta classe é responsável pelo gerenciamento de categorias no banco de dados.
 */
public class CategoriaDAO {
    /**
     * Método para criar uma nova categoria no banco de dados.
     *
     * @param nome O nome da nova categoria.
     * @param userLogadoID O ID do usuário atualmente logado.
     */
    public void criarCategoria(String nome, int userLogadoID) {
        String sql_insert = "INSERT INTO categorias (nome_categoria, ID_USER) VALUES (?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql_insert)) {

            // Define os parâmetros da consulta
            pstmt.setString(1, nome);
            pstmt.setInt(2, userLogadoID); // Define o ID do usuário logado

            // Executa a consulta
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoria adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // Lida com erro, caso a categoria já exista
            JOptionPane.showMessageDialog(null, "ERRO! A categoria já existe ou houve um problema!", "ERRO", JOptionPane.ERROR_MESSAGE);
            System.err.println("Erro ao adicionar categoria: " + e.getMessage());
        }
    }
}