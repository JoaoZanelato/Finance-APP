package ui;

import com.toedter.calendar.JDateChooser;
import dao.CategoriaDAO;
import dao.Connect;
import dao.CriarUserDAO;
import dao.LoginUserDAO;
import dao.CompraDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Classe principal da aplicação UFinance, responsável por gerenciar as funcionalidades da interface gráfica e da lógica principal.
 */
public class Card extends JFrame {

    // Componentes e variáveis da interface gráfica
    private JPanel TELA;
    private JLabel irParaRegistro;
    private JLabel irParaLogin;
    private JPanel LoginRegistroPanel;
    private JPanel RegistroPanel;
    private JTextField InputRegistro;
    private JPasswordField InputSenhaRegistro;
    private JLabel RegistrarLabel;
    private JLabel VoltarParaRegistroLogin;
    private JLabel loginLabel;
    private JLabel voltarParaRegistroLogin;
    private JTextField InputLogin;
    private JPasswordField InputSenha_Testar;
    private JLabel LabelErroRegistro;
    private JLabel LabelErroLogin;
    private JPanel WelcomePanel;
    private JPanel GerenciarTab;
    private JPanel Header;
    private JLabel UnlogBtn;
    private JLabel GerenciarLabel;
    private JPanel GerenciarPanel;
    private JPanel MainCard;
    private JLabel adicionarLabel;
    private JPanel TabelaPanel;
    private JPanel MasterPane;
    private JList lista;
    private JPanel EntradaCard;
    private JPanel BemvindoCard;
    private JPanel RegistroCard;
    private JPanel LoginPanel;
    private JLabel UserLabel;
    private JLabel PDFLabel;
    private JPanel AdicionarPanel;
    private JTextField descricaoField;
    private JFormattedTextField valorField;
    private JComboBox categoriaComboBox;
    private JRadioButton GastoBtn;
    private JRadioButton ReceitaBtn;
    private JLabel salvarLabel;
    private JLabel cancelarLabel;
    private JLabel adicionarCategoriaLabel;
    private JLabel erroDescricaoLabel;
    private JLabel erroValorLabel;
    private JLabel erroCategoriaLabel;
    private JLabel erroGastoReceitaLabel;
    private JLabel erroDataLabel;
    private JComboBox filtrosComboBox;
    private JLabel filtrarLabel;
    private JPanel datePanel;
    private JComboBox categorias;
    private JPanel HeaderGerencia;
    private JLabel removerLabel;
    private JTable tabela;
    private JLabel saldoLabel;
    private JLabel editarLabel;
    private JPanel EditarPanel;
    private JTextField descricaoEditar;
    private JLabel descricaoErroEditar;
    private JLabel categoriaErroEditar;
    private JLabel adicionarCategoriaEditar;
    private JFormattedTextField valorEditar;
    private JComboBox categoriaEditar;
    private JPanel dataEditarPanel;
    private JRadioButton GastoEditar;
    private JRadioButton ReceitaEditar;
    private JLabel erroBtnsEditar;
    private JLabel erroValorEditar;
    private JLabel salvarEditar;
    private JLabel cancelarEditar;
    private JLabel adicionarCategoriaLabel2;
    private CardLayout cardLayout;
    private final CriarUserDAO criarUser = new CriarUserDAO();
    private final LoginUserDAO loginUser = new LoginUserDAO();
    private CategoriaDAO criarCategoria;
    private static String userLogado;
    private final CompraDAO criarcompra = new CompraDAO();
    private Image backgroundImage;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JDateChooser dateChooser = new JDateChooser();
    private JDateChooser dateChooserEditar = new JDateChooser();
    private ButtonGroup buttonGroupEditar = new ButtonGroup();

    /**
     * Construtor da classe Card.
     * Configura a janela principal e todos os componentes.
     */
    public Card() {
        cfgJanela();
        cfgBtnRegistro();
        cfgBtnVoltarLoginRegistro();
        cfgTabs();
        cfgBtnGerenciar();
        cfgHeader();
        cfgRegister();
        cfgLogin();
        configurarMascaraValor();
        cfgJDateChooser();
    }

    /**
     * Configura os elementos de data (JDateChooser) para o formato correto e insere nas áreas correspondentes.
     */
    private void cfgJDateChooser() {
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooserEditar.setDateFormatString("dd/MM/yyyy");

        dateChooser.setDate(new java.util.Date());

        datePanel.setLayout(new BorderLayout());
        datePanel.add(dateChooser, BorderLayout.CENTER);
        dataEditarPanel.setLayout(new BorderLayout());
        dataEditarPanel.add(dateChooserEditar, BorderLayout.CENTER);
        dataEditarPanel.revalidate();
        dataEditarPanel.repaint();
    }

    /**
     * Exibe o usuário atualmente logado no console.
     */
    public void getUserLogado() {
        System.out.println(userLogado);
    }

    /**
     * Configura as propriedades iniciais da janela principal.
     */
    private void cfgJanela() {
        cardLayout = (CardLayout) MainCard.getLayout();
        MainCard.add(LoginRegistroPanel, "LoginRegistroPanel");
        MainCard.add(WelcomePanel, "WelcomePanel");
        MainCard.add(GerenciarPanel, "GerenciarPanel");
        MainCard.add(RegistroPanel, "RegistroPanel");
        MainCard.add(AdicionarPanel, "AdicionarPanel");
        MainCard.add(EditarPanel, "EditarPanel");
        buttonGroup.add(GastoBtn);
        buttonGroup.add(ReceitaBtn);
        buttonGroupEditar.add(GastoEditar);
        buttonGroupEditar.add(ReceitaEditar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(TELA);
        setTitle("UFinance - Seu app de finanças");
        ImageIcon icon = new ImageIcon("assets/appicon.png");
        setIconImage(icon.getImage());
        InputLogin.requestFocusInWindow();
        setVisible(true);
    }

    /**
     * Configura o processo de registro de novos usuários.
     */
    private void cfgRegister() {
        RegistrarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrarUsuario();
            }
        });

        InputSenhaRegistro.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    registrarUsuario();
                }
            }
        });
    }

    /**
     * Realiza a validação e criação de um novo usuário com base nos dados fornecidos.
     */
    private void registrarUsuario() {
        char[] passwordChars = InputSenhaRegistro.getPassword();
        String password = new String(passwordChars);
        LabelErroRegistro.setText(" ");

        String registro = InputRegistro.getText().trim();
        int tamanho = registro.length();
        String[] teste_espaco = registro.split(" ");

        if (registro.isEmpty()) {
            LabelErroRegistro.setText("PREENCHA O CAMPO REGISTRO");
        } else if (tamanho > 15) {
            LabelErroRegistro.setText("REGISTRO DEVE TER MENOS QUE 15 CARACTERES");
        } else if (teste_espaco.length > 1) {
            LabelErroRegistro.setText("REGISTRO NÃO DEVE CONTER ESPAÇOS");
        } else {
            try {
                if (password.isEmpty()) {
                    LabelErroRegistro.setText("SENHA VAZIA");
                } else {
                    if (criarUser.CriarUser(registro, password)) {
                        userLogado = registro;
                        UnlogBtn.setVisible(true);
                        GerenciarTab.setVisible(true);
                        UserLabel.setText(registro.toUpperCase() + "!");
                        cardLayout.show(MainCard, "WelcomePanel");
                        MainCard.repaint();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                LabelErroRegistro.setText("ERRO AO CRIAR USUÁRIO");
            } finally {
                Arrays.fill(passwordChars, ' ');
            }
        }
    }

    /**
     * Configura o processo de login de usuários.
     */
    private void cfgLogin() {
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                realizarLogin();
            }
        });

        InputSenha_Testar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarLogin();
                }
            }
        });
    }

    /**
     * Realiza o processo de validação e autenticação do usuário com base nos dados fornecidos.
     */
    private void realizarLogin() {
        char[] passwordChars = InputSenha_Testar.getPassword();
        String password = new String(passwordChars);
        LabelErroLogin.setText(" ");

        String login = InputLogin.getText().trim();
        int tamanho = login.length();
        String[] teste_espaco = login.split(" ");

        if (login.isEmpty()) {
            LabelErroLogin.setText("PREENCHA O CAMPO LOGIN");
        } else if (tamanho > 15) {
            LabelErroLogin.setText("LOGIN DEVE TER MENOS QUE 15 CARACTERES");
        } else if (teste_espaco.length > 1) {
            LabelErroLogin.setText("LOGIN NÃO DEVE CONTER ESPAÇOS");
        } else {
            try {
                if (password.isEmpty()) {
                    LabelErroLogin.setText("SENHA VAZIA");
                } else {
                    if (loginUser.validarLogin(login, password)) {
                        InputLogin.setText("");
                        InputSenha_Testar.setText("");
                        JOptionPane.showMessageDialog(null, "USUÁRIO LOGADO", "LOGIN", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(MainCard, "WelcomePanel");
                        userLogado = login;
                        getUserLogado();
                        UnlogBtn.setVisible(true);
                        GerenciarTab.setVisible(true);
                        UserLabel.setText(login.toUpperCase() + "!");
                        MainCard.repaint();
                    } else {
                        LabelErroLogin.setText("SENHA INVÁLIDA");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                LabelErroLogin.setText("ERRO AO LOGAR");
            } finally {
                Arrays.fill(passwordChars, ' ');
            }
        }
    }

    /**
     * Configura os botões e labels relacionados ao processo de registro.
     */
    private void cfgBtnRegistro() {
        irParaRegistro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                InputRegistro.requestFocusInWindow();
                cardLayout.show(MainCard, "RegistroPanel");
            }
        });
    }

    /**
     * Configura os botões de retorno entre as telas de registro e login.
     */
    private void cfgBtnVoltarLoginRegistro() {
        VoltarParaRegistroLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cardLayout.show(MainCard, "LoginRegistroPanel");
            }
        });
    }

    /**
     * Configura as abas da interface (Gerenciar e Relatório).
     */
    private void cfgTabs() {
        GerenciarTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    CfgFiltros();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                cardLayout.show(MainCard, "GerenciarPanel");
                GerenciarTab.setBackground(Color.white);
                GerenciarLabel.setForeground(Color.blue);
                descricaoField.setText("");
                valorField.setText("");
                dateChooser.setDate(new java.util.Date());
                buttonGroup.clearSelection();
                try {
                    aplicarFiltro();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    /**
     * Configura o cabeçalho e botões de logout.
     */
    private void cfgHeader() {
        UnlogBtn.setVisible(false);
        UnlogBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int confirm = JOptionPane.showConfirmDialog(null, "DESEJA ENCERRAR A SESSÃO?", "UNLOG", JOptionPane.YES_NO_OPTION);
                if (confirm == 0) {
                    JOptionPane.showMessageDialog(null, "SESSÃO ENCERRADA!", "UNLOG", JOptionPane.INFORMATION_MESSAGE);
                    GerenciarTab.setVisible(false);
                    UnlogBtn.setVisible(false);
                    userLogado = "";
                    descricaoField.setText("");
                    valorField.setText("");
                    dateChooser.setDate(new java.util.Date());
                    buttonGroup.clearSelection();
                    GerenciarTab.setBackground(Color.blue);
                    GerenciarLabel.setForeground(Color.WHITE);
                    cardLayout.show(MainCard, "LoginRegistroPanel");
                }
            }
        });
    }

    /**
     * Atualiza as categorias disponíveis nos ComboBox com os dados do banco de dados.
     */
    private void atualizarComboBox() {
        String query = "SELECT nome_categoria FROM categorias";
        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            categoriaComboBox.removeAllItems();
            categoriaEditar.removeAllItems();
            while (resultSet.next()) {
                String categoria = resultSet.getString("nome_categoria");
                categoriaComboBox.addItem(categoria);
                categoriaEditar.addItem(categoria);
            }
            System.out.println("Categorias carregadas com sucesso no ComboBox.");
        } catch (SQLException e) {
            System.err.println("Erro ao carregar categorias no ComboBox: " + e.getMessage());
        }
    }

    /**
     * Obtém as categorias associadas ao usuário logado.
     *
     * @return Lista de categorias.
     */
    private ArrayList<String> getCategorias() throws SQLException {
        String query = "SELECT nome_categoria FROM categorias WHERE ID_USER = ?";
        ArrayList<String> categorias = new ArrayList<>();
        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, getUserID());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    categorias.add(resultSet.getString("nome_categoria"));
                    System.out.println("Categoria encontrada: " + resultSet.getString("nome_categoria"));
                }
            }
        }

        // Verificando se há categorias
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada para o usuário: " + userLogado);
        }

        // Retorna a lista de categorias
        return categorias;
    }


    /**
     * Obtém o ID de uma categoria com base no nome fornecido.
     *
     * @param categoria Nome da categoria.
     * @return ID da categoria correspondente.
     * @throws SQLException Caso ocorra um erro ao buscar o ID da categoria.
     */
    private int getCategoriaID(String categoria) throws SQLException {
        String sql = "SELECT ID_CATEGORIA FROM categorias WHERE nome_categoria = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Define o parâmetro do nome da categoria
            pstmt.setString(1, categoria);

            // Executa a consulta e obtém o resultado
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_CATEGORIA"); // Retorna o ID da categoria encontrada
                } else {
                    throw new SQLException("Categoria não encontrada: " + categoria);
                }
            }
        }
    }

    /**
     * Obtém o nome de uma categoria com base no seu ID.
     *
     * @param ID ID da categoria.
     * @return Nome da categoria correspondente.
     * @throws SQLException Caso ocorra um erro ao buscar o nome da categoria.
     */
    private String getCategoriaNome(int ID) throws SQLException {
        String sql = "SELECT nome_categoria FROM categorias WHERE ID_CATEGORIA = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Define o parâmetro do ID da categoria
            pstmt.setInt(1, ID);

            // Executa a consulta e obtém o resultado
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nome_categoria"); // Retorna o nome da categoria encontrada
                } else {
                    throw new SQLException("Categoria não encontrada: " + ID);
                }
            }
        }
    }

    /**
     * Configura a formatação para campos de valores monetários com formato brasileiro (R$ com separador decimal vírgula).
     */
    public void configurarMascaraValor() {
        // Define os símbolos de moeda e separadores
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');
        DecimalFormat formatoMoeda = new DecimalFormat("R$ #,##0.00", simbolos);
        formatoMoeda.setMaximumFractionDigits(2);

        // Aplica a máscara para os campos de valor
        valorField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(formatoMoeda)));
        valorEditar.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(formatoMoeda)));

        // Adiciona eventos de foco para os campos
        valorField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                formatarValorField(); // Formata o valor ao perder o foco
            }

            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (valorField.getText().equals("R$ 0,00")) {
                    valorField.setText(""); // Limpa o campo ao ganhar o foco
                }
            }
        });

        valorEditar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                formatarValorEditar(); // Formata o valor ao perder o foco
            }

            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (valorEditar.getText().equals("R$ 0,00")) {
                    valorEditar.setText(""); // Limpa o campo ao ganhar o foco
                }
            }
        });
    }

    /**
     * Formata o campo de valor ao perder o foco, aplicando a formatação monetária.
     */
    private void formatarValorField() {
        try {
            String valorTexto = valorField.getText().replace("R$", "").trim();
            valorTexto = valorTexto.replaceAll("[^0-9,]", ""); // Remove caracteres inválidos

            if (!valorTexto.isEmpty()) {
                valorTexto = valorTexto.replace(",", "."); // Substitui vírgula por ponto
                double valor = Double.parseDouble(valorTexto);

                // Reaplica a formatação
                DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                simbolos.setDecimalSeparator(',');
                simbolos.setGroupingSeparator('.');
                DecimalFormat formatoMoeda = new DecimalFormat("R$ #,###.##", simbolos);
                valorField.setText(formatoMoeda.format(valor));
            }
        } catch (NumberFormatException ex) {
            valorField.setText(""); // Limpa o campo em caso de erro
        }
    }

    /**
     * Formata o campo de valor de edição ao perder o foco, aplicando a formatação monetária.
     */
    private void formatarValorEditar() {
        try {
            String valorTexto = valorEditar.getText().replace("R$", "").trim();
            valorTexto = valorTexto.replaceAll("[^0-9,]", ""); // Remove caracteres inválidos

            if (!valorTexto.isEmpty()) {
                valorTexto = valorTexto.replace(",", "."); // Substitui vírgula por ponto
                double valor = Double.parseDouble(valorTexto);

                // Reaplica a formatação
                DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                simbolos.setDecimalSeparator(',');
                simbolos.setGroupingSeparator('.');
                DecimalFormat formatoMoeda = new DecimalFormat("R$ #,###.##", simbolos);
                valorEditar.setText(formatoMoeda.format(valor));
            }
        } catch (NumberFormatException ex) {
            valorEditar.setText(""); // Limpa o campo em caso de erro
        }
    }

    /**
     * Obtém o ID do usuário logado com base no login.
     *
     * @return ID do usuário logado.
     * @throws SQLException Caso ocorra um erro ao buscar o ID do usuário.
     */
    private int getUserID() {
        String sql = "SELECT ID_USER FROM users WHERE login = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Define o parâmetro do login do usuário
            pstmt.setString(1, userLogado);

            // Executa a consulta e obtém o resultado
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_USER"); // Retorna o ID do usuário encontrado
                } else {
                    throw new SQLException("Usuário não encontrado.");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao obter ID do usuário: " + ex.getMessage());
            ex.printStackTrace();
            return -1; // Valor inválido indicando erro
        }
    }

    /**
     * Configura os filtros disponíveis para o usuário na interface.
     * Adiciona filtros padrão e categorias do banco de dados na ComboBox de filtros.
     *
     * @throws SQLException Caso ocorra um erro ao acessar o banco de dados.
     */
    public void CfgFiltros() throws SQLException {
        // Adiciona os filtros padrão na ComboBox
        System.out.println("Usuário logado: " + userLogado);

        filtrosComboBox.addItem("Todos");
        filtrosComboBox.addItem("Gastos Crescentes");
        filtrosComboBox.addItem("Gastos Decrescentes");
        filtrosComboBox.addItem("Datas Crescentes");
        filtrosComboBox.addItem("Datas Decrescentes");
        filtrosComboBox.addItem("Receitas Crescentes");
        filtrosComboBox.addItem("Receitas Decrescentes");
        System.out.println("Filtros padrão adicionados à ComboBox.");

        // Obtém as categorias do banco de dados
        ArrayList<String> categorias = getCategorias();
        System.out.println("Categorias obtidas do banco: " + categorias);

        // Adiciona as categorias na ComboBox a partir do índice 7
        for (String categoria : categorias) {
            filtrosComboBox.addItem(categoria);
            System.out.println("Categoria adicionada à ComboBox: " + categoria);
        }
    }

    /**
     * Retorna a consulta SQL correspondente ao filtro selecionado pelo índice na ComboBox.
     *
     * @param index índice do filtro selecionado.
     * @return Consulta SQL correspondente ao filtro.
     * @throws SQLException Caso o índice do filtro seja inválido.
     */
    private String casosTabela(int index) throws SQLException {
        if (index >= 7) {
            // Filtro por categoria a partir do índice 7
            return "SELECT * FROM gastos WHERE ID_USER = ? AND ID_CATEGORIA = ?";
        }

        return switch (index) {
            case 0 -> "SELECT * FROM gastos WHERE ID_USER = ?"; // TODOS
            case 1 -> "SELECT * FROM gastos WHERE ID_USER = ? AND EH_GASTO = 1 ORDER BY valor ASC"; // GASTOS CRESCENTES
            case 2 ->
                    "SELECT * FROM gastos WHERE ID_USER = ? AND EH_GASTO = 1 ORDER BY valor DESC"; // GASTOS DECRESCENTES
            case 3 -> "SELECT * FROM gastos WHERE ID_USER = ? ORDER BY data ASC"; // DATAS CRESCENTES
            case 4 -> "SELECT * FROM gastos WHERE ID_USER = ? ORDER BY data DESC"; // DATAS DECRESCENTES
            case 5 ->
                    "SELECT * FROM gastos WHERE ID_USER = ? AND EH_GASTO = 0 ORDER BY valor ASC"; // RECEITAS CRESCENTES
            case 6 ->
                    "SELECT * FROM gastos WHERE ID_USER = ? AND EH_GASTO = 0 ORDER BY valor DESC"; // RECEITAS DECRESCENTES
            default -> throw new SQLException("Índice de filtro inválido");
        };
    }

    /**
     * Aplica o filtro selecionado pelo usuário e atualiza os dados exibidos na tabela.
     *
     * @throws SQLException Caso ocorra um erro ao acessar o banco de dados.
     */
    public void aplicarFiltro() throws SQLException {
        int index = filtrosComboBox.getSelectedIndex(); // Índice do filtro selecionado
        System.out.println("Filtro selecionado: " + index);

        String sql = casosTabela(index); // Obter consulta SQL correspondente
        System.out.println("Consulta SQL gerada: " + sql);

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, getUserID()); // Define o ID do usuário

            if (index >= 7) {
                // Caso o filtro seja por categoria, define o ID da categoria como parâmetro
                String categoriaSelecionada = filtrosComboBox.getSelectedItem().toString();
                int categoriaID = getCategoriaID(categoriaSelecionada);
                System.out.println("Categoria selecionada: " + categoriaSelecionada + ", ID da categoria: " + categoriaID);
                pstmt.setInt(2, categoriaID);
            }

            // Executa a consulta e atualiza a tabela
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Executando consulta SQL e obtendo ResultSet.");
                UpdateTabela(rs);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao aplicar filtro: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao aplicar filtro.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza a tabela da interface com os dados retornados da consulta SQL.
     *
     * @param rs ResultSet contendo os dados da consulta.
     */
    public void UpdateTabela(ResultSet rs) {
        System.out.println("Método UpdateTabela chamado.");

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        if (model.getColumnCount() == 0) {
            // Configura as colunas da tabela
            model.setColumnIdentifiers(new Object[]{"ID", "VALOR", "DATA", "CATEGORIA", "GASTO/RECEITA", "DESCRIÇÃO"});
            System.out.println("Colunas configuradas.");
        }

        // Limpa os dados existentes na tabela
        model.setRowCount(0);
        System.out.println("Tabela limpa.");

        double saldo = 0; // Variável para calcular o saldo total

        try {
            if (!rs.isBeforeFirst()) {
                // Caso o ResultSet esteja vazio
                System.out.println("O ResultSet está vazio.");
                model.addRow(new Object[]{"", "", "Nenhum dado encontrado", "", "", ""});
                model.fireTableDataChanged();
                tabela.revalidate();
                tabela.repaint();
                return;
            }

            // Preenche a tabela com os dados do ResultSet
            System.out.println("Preenchendo a tabela com os dados do ResultSet.");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formatação da data

            while (rs.next()) {
                int idGasto = rs.getInt("ID_GASTO"); // ID do gasto

                double valor = rs.getDouble("valor");
                String valorFormatado = String.format("R$ %.2f", valor);

                java.sql.Date dataSql = rs.getDate("data");
                String data = (dataSql != null) ? sdf.format(dataSql) : "";

                int categoriaId = rs.getInt("ID_CATEGORIA");
                String categoria;
                try {
                    categoria = getCategoriaNome(categoriaId);
                } catch (SQLException ex) {
                    categoria = "Sem Categoria";
                    System.err.println("Erro ao buscar nome da categoria: " + ex.getMessage());
                }

                String receitaOuGasto = rs.getBoolean("EH_GASTO") ? "GASTO" : "RECEITA";
                String descricao = rs.getString("descricao");

                saldo += receitaOuGasto.equals("RECEITA") ? valor : -valor;

                // Adiciona a linha à tabela
                model.addRow(new Object[]{idGasto, valorFormatado, data, categoria, receitaOuGasto, descricao});
                tabela.setDefaultEditor(Object.class, null);
            }

            // Atualização visual da tabela
            System.out.println("Notificando que os dados foram alterados.");
            model.fireTableDataChanged();
            tabela.revalidate();
            tabela.repaint();

            // Oculta a coluna de ID
            tabela.getColumnModel().getColumn(0).setMinWidth(0);
            tabela.getColumnModel().getColumn(0).setMaxWidth(0);
            tabela.getColumnModel().getColumn(0).setWidth(0);

            // Atualiza o saldo na label
            String saldoFormatado = String.format("R$ %.2f", saldo);
            saldoLabel.setForeground(saldo >= 0 ? Color.GREEN : Color.RED);
            saldoLabel.setText("Saldo: " + saldoFormatado);

        } catch (SQLException e) {
            System.err.println("Erro ao preencher tabela: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados na tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Configura os botões relacionados ao gerenciamento de categorias e movimentações financeiras.
     */
    private void cfgBtnGerenciar() {

        // Listener para aplicar filtros ao clicar no label "filtrarLabel".
        filtrarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    aplicarFiltro(); // Chama o método para aplicar o filtro selecionado
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Listener para adicionar nova categoria ao clicar no label "adicionarCategoriaLabel".
        adicionarCategoriaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String categoria = JOptionPane.showInputDialog(null, "INSIRA A CATEGORIA A SER CRIADA", "CRIAR CATEGORIA", JOptionPane.PLAIN_MESSAGE);
                if (categoria == null || categoria.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "CATEGORIA NÃO FOI CRIADA! CAMPO VAZIO", "ERRO", JOptionPane.ERROR_MESSAGE);
                } else {
                    criarCategoria = new CategoriaDAO();
                    criarCategoria.criarCategoria(categoria, getUserID());
                    atualizarComboBox(); // Atualiza as categorias no ComboBox
                    try {
                        CfgFiltros(); // Atualiza os filtros disponíveis
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        // Listener para adicionar nova categoria ao clicar no label "adicionarCategoriaEditar".
        adicionarCategoriaEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String categoria = JOptionPane.showInputDialog(null, "INSIRA A CATEGORIA A SER CRIADA", "CRIAR CATEGORIA", JOptionPane.PLAIN_MESSAGE);
                if (categoria == null || categoria.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "CATEGORIA NÃO FOI CRIADA! CAMPO VAZIO", "ERRO", JOptionPane.ERROR_MESSAGE);
                } else {
                    criarCategoria = new CategoriaDAO();
                    criarCategoria.criarCategoria(categoria, getUserID());
                    atualizarComboBox(); // Atualiza as categorias no ComboBox
                    try {
                        CfgFiltros(); // Atualiza os filtros disponíveis
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        // Listener para salvar um gasto ou receita ao clicar no label "salvarLabel".
        salvarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Limpa as mensagens de erro antes de realizar validações
                erroDescricaoLabel.setText(" ");
                erroValorLabel.setText(" ");
                erroDataLabel.setText(" ");
                erroGastoReceitaLabel.setText(" ");

                // Variáveis para armazenar os dados
                String descricao_add = "";
                double valor_add = 0.0;
                String categoria = (String) categoriaComboBox.getSelectedItem();
                String receita_gasto = "";
                boolean btn_add = false;
                boolean descricao_teste = false;
                boolean valor_teste = false;
                boolean data_teste = false;

                // Obtém o botão de rádio selecionado
                ButtonModel selectedModel = buttonGroup.getSelection();
                if (selectedModel != null) {
                    for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
                        AbstractButton button = buttons.nextElement();
                        if (button.getModel() == selectedModel) {
                            receita_gasto = button.getText();
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "SELECIONE RECEITA OU GASTO", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Validação da descrição
                    if (descricaoField.getText().isEmpty()) {
                        erroDescricaoLabel.setText("DESCRIÇÃO NÃO PODE ESTAR VAZIA");
                    } else if (descricaoField.getText().trim().length() > 200) {
                        erroDescricaoLabel.setText("DESCRIÇÃO NÃO PODE TER MAIS DE 200 CARACTERES");
                    } else {
                        descricao_add = descricaoField.getText().trim();
                        descricao_teste = true;
                    }

                    // Validação do valor
                    String valorTexto = valorField.getText().trim();
                    if (valorTexto.isEmpty()) {
                        erroValorLabel.setText("VALOR ESTÁ VAZIO");
                    } else {
                        try {
                            // Remove formatação de moeda (R$ e pontuação)
                            valorTexto = valorTexto.replace("R$", "").trim().replace(".", "").replace(",", ".");
                            valor_add = Double.parseDouble(valorTexto);
                            valor_teste = true;
                        } catch (NumberFormatException ex) {
                            erroValorLabel.setText("VALOR INVÁLIDO");
                        }
                    }

                    // Validação da data com JDateChooser
                    java.util.Date dataSelecionada = dateChooser.getDate();
                    if (dataSelecionada == null) {
                        erroDataLabel.setText("DATA NÃO SELECIONADA");
                    } else {
                        java.sql.Date sqlDate = new java.sql.Date(dataSelecionada.getTime());
                        data_teste = true;

                        // Verifica se todas as validações passaram
                        if (valor_teste && descricao_teste && data_teste) {
                            if (receita_gasto.equalsIgnoreCase("Gasto")) {
                                btn_add = true;
                            }

                            // Chama o método da classe model.Compra para adicionar o gasto ou receita
                            criarcompra.adicionarGasto(sqlDate, valor_add, getCategoriaID(categoria), getUserID(), btn_add, descricao_add);

                            // Limpa os campos e seleções após adicionar
                            descricaoField.setText("");
                            valorField.setText("");
                            dateChooser.setDate(new java.util.Date());
                            buttonGroup.clearSelection();

                            // Exibe mensagem de sucesso
                            String tipo = btn_add ? "GASTO" : "RECEITA";
                            JOptionPane.showMessageDialog(null, tipo + " ADICIONADO COM SUCESSO", tipo, JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                } catch (RuntimeException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao adicionar gasto. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener para abrir o painel de adição de novas movimentações financeiras
        adicionarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                descricaoField.setText("");
                valorField.setText("");
                dateChooser.setDate(new java.util.Date());
                buttonGroup.clearSelection();
                atualizarComboBox();
                cardLayout.show(MainCard, "AdicionarPanel");
            }
        });

        // Listener para cancelar a adição de movimentações financeiras
        cancelarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                descricaoField.setText("");
                valorField.setText("");
                dateChooser.setDate(new java.util.Date());
                buttonGroup.clearSelection();
                cardLayout.show(MainCard, "GerenciarPanel");
                try {
                    aplicarFiltro();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Listener para cancelar a edição de movimentações financeiras
        cancelarEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                descricaoEditar.setText("");
                valorEditar.setText("");
                dateChooserEditar.setDate(new java.util.Date());
                buttonGroupEditar.clearSelection();
                cardLayout.show(MainCard, "GerenciarPanel");
                try {
                    aplicarFiltro();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Listener para remover movimentações financeiras selecionadas na tabela
        removerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Obtém a linha selecionada na tabela
                int rowSelecionada = tabela.getSelectedRow();

                if (rowSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione uma linha para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (tabela.getSelectedRows().length > 1) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione apenas uma linha para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Obtém o ID do gasto ou receita da linha selecionada
                int idGasto = (int) tabela.getValueAt(rowSelecionada, 0);

                int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja remover estes dados?", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    try (Connection conn = Connect.getConnection()) {
                        String query = "DELETE FROM gastos WHERE ID_GASTO = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(query)) {
                            stmt.setInt(1, idGasto);
                            stmt.executeUpdate();

                            // Remove a linha da tabela
                            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                            model.removeRow(rowSelecionada);

                            JOptionPane.showMessageDialog(null, "Dados removidos com sucesso.");
                            aplicarFiltro();
                        }
                    } catch (SQLException ex) {
                        System.err.println("Erro ao remover linha do banco de dados: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Erro ao remover a linha do banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Listener para editar movimentações financeiras selecionadas na tabela
        editarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowSelecionada = tabela.getSelectedRow();
                if (rowSelecionada <= -1) {
                    JOptionPane.showMessageDialog(null, "Selecione uma linha para editar!", "ERRO", JOptionPane.ERROR_MESSAGE);
                } else {
                    cardLayout.show(MainCard, "EditarPanel");
                    try (Connection conn = Connect.getConnection()) {
                        String query = "SELECT * FROM gastos WHERE ID_GASTO = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(query)) {
                            int idGasto = (int) tabela.getValueAt(rowSelecionada, 0);
                            stmt.setInt(1, idGasto);
                            ResultSet rs = stmt.executeQuery();

                            if (rs.next()) {
                                dateChooserEditar.setDate(rs.getDate("data"));
                                valorEditar.setText(String.valueOf(rs.getInt("valor")));
                                atualizarComboBox();
                                categoriaComboBox.setSelectedIndex(rs.getInt("ID_CATEGORIA") - 1);
                                if (rs.getBoolean("EH_GASTO")) {
                                    buttonGroupEditar.setSelected(GastoEditar.getModel(), true);
                                } else {
                                    buttonGroupEditar.setSelected(ReceitaEditar.getModel(), true);
                                }
                                descricaoEditar.setText(rs.getString("descricao"));
                            }
                        }
                    } catch (SQLException ex) {
                        System.err.println("Erro ao acessar dados: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Erro ao acessar dados", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Listener para salvar as alterações feitas em movimentações financeiras
        salvarEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowSelecionada = tabela.getSelectedRow();
                int idGasto = (int) tabela.getValueAt(rowSelecionada, 0);

                categoriaErroEditar.setText(" ");
                erroValorEditar.setText(" ");
                erroBtnsEditar.setText(" ");

                String descricao_add = "";
                double valor_add = 0.0;
                String categoria = (String) categoriaEditar.getSelectedItem();
                String receita_gasto = "";
                boolean btn_add = false;
                boolean descricao_teste = false;
                boolean valor_teste = false;
                boolean data_teste = false;

                ButtonModel selectedModel = buttonGroupEditar.getSelection();
                if (selectedModel != null) {
                    for (Enumeration<AbstractButton> buttons = buttonGroupEditar.getElements(); buttons.hasMoreElements(); ) {
                        AbstractButton button = buttons.nextElement();
                        if (button.getModel() == selectedModel) {
                            receita_gasto = button.getText();
                            break;
                        }
                    }
                } else {
                    erroBtnsEditar.setText("SELECIONE RECEITA OU GASTO");
                    return;
                }

                try {
                    if (descricaoEditar.getText().isEmpty()) {
                        descricaoErroEditar.setText("DESCRIÇÃO NÃO PODE ESTAR VAZIA");
                    } else if (descricaoEditar.getText().trim().length() > 200) {
                        descricaoErroEditar.setText("DESCRIÇÃO NÃO PODE TER MAIS DE 200 CARACTERES");
                    } else {
                        descricao_add = descricaoEditar.getText().trim();
                        descricao_teste = true;
                    }

                    String valorTexto = valorEditar.getText().trim();
                    if (valorTexto.isEmpty()) {
                        erroValorEditar.setText("VALOR ESTÁ VAZIO");
                    } else {
                        try {
                            valorTexto = valorTexto.replace("R$", "").trim().replace(".", "").replace(",", ".");
                            valor_add = Double.parseDouble(valorTexto);
                            if (valor_add <= 0) {
                                erroValorEditar.setText("VALOR DEVE SER MAIOR QUE ZERO");
                            } else {
                                valor_teste = true;
                            }
                        } catch (NumberFormatException ex) {
                            erroValorEditar.setText("VALOR INVÁLIDO");
                        }
                    }

                    java.util.Date dataSelecionada = dateChooserEditar.getDate();
                    java.sql.Date sqlDate = new java.sql.Date(dataSelecionada.getTime());
                    data_teste = true;

                    if (valor_teste && descricao_teste && data_teste) {
                        if (receita_gasto.equalsIgnoreCase("Gasto")) {
                            btn_add = true;
                        }

                        criarcompra.editarGasto(idGasto, sqlDate, valor_add, getCategoriaID(categoria), btn_add, descricao_add);

                        descricaoEditar.setText("");
                        valorEditar.setText("");
                        dateChooser.setDate(new java.util.Date());
                        buttonGroupEditar.clearSelection();

                        String tipo = btn_add ? "GASTO" : "RECEITA";
                        JOptionPane.showMessageDialog(null, tipo + " EDITADO COM SUCESSO", tipo, JOptionPane.INFORMATION_MESSAGE);

                        descricaoEditar.setText("");
                        valorEditar.setText("");
                        dateChooserEditar.setDate(new java.util.Date());
                        buttonGroupEditar.clearSelection();
                        cardLayout.show(MainCard, "GerenciarPanel");
                        try {
                            aplicarFiltro();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(null, "ERRO AO EDITAR GASTO", "ERRO", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
