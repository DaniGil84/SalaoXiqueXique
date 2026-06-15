package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/DER_ProjetoIntegrador_Manicure"; // BD Glauber
    //private static final String DB_URL = "jdbc:mysql://localhost:3306/salao_xique_xique"; // BD Dani
    private static final String DB_USUARIO = "root";
    private static final String DB_SENHA = "";

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println(" ");
        System.out.println("===============================");
        System.out.println("BEM VINDO AO SALÃO XIQUE XIQUE!");
        System.out.println("===============================");
        System.out.println(" ");

        int opcao = 0;

        while (opcao != 10) {
            exibirMenu();

            System.out.println("Escolha uma das opções acima: ");

            if (scan.hasNextInt()) {
                opcao = scan.nextInt();
                scan.nextLine();
            } else {
                System.out.println("Por favor digite uma opção válida!");
                scan.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarUsuario(scan);
                    break;
                case 2:
                    listarTodosUsuarios();
                    break;
                case 3:
                    consultaUsuarioPorNome(scan);
                    break;
                case 4:
                    servicosManicure(scan);
                    break;
                case 5:
                    listarAtendimentos();
                    break;
                case 6:
                    listarServico();
                    break;
                case 10:
                    System.out.println("Encerrando o sistema .... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scan.close();
    }

    public static void exibirMenu() {
        System.out.println("============= Salão Xique Xique ===========");
        System.out.println("1 - Cadastro de usuário novo");
        System.out.println("2 - Listar todos os usuário");
        System.out.println("3 - Buscar usuário por nome");
        System.out.println("4 - Atribuir serviço a cliente");
        System.out.println("5 - Listar Atendimentos");
        System.out.println("6 - Listar serviços e valores");
        System.out.println("10 - Sair");
        System.out.println("===========================");
    }

    public static void cadastrarUsuario(Scanner scan) {

        System.out.println("---Cadastro de USUÁRIO---");

        System.out.println("Digite o nome: ");
        String nome = scan.nextLine();
        //if (nome.contains("numeros")) {              - precisa fazer essa validação
            //System.out.println("nome é inválido");

        System.out.println("Digite o CPF: ");
        String CPF = scan.nextLine();
        //if (CPF.contains("letras")) {              - precisa fazer essa validação
            //System.out.println("CPF é inválido");

        System.out.println("Digite a cidade: ");
        String endereco = scan.nextLine();

        if (endereco.length() > 70) {
            System.out.println("Cidade é inválida");
        }

        System.out.println("Digite o E-mail: ");
        String email = scan.nextLine();
        if (!email.contains("@")) {
            System.out.println("E-mail é inválido");
        }

        System.out.println("Digite o Telefone: ");
        String telefone = scan.nextLine();
        //if (Telefone.contains("letras")) {              - precisa fazer essa validação
            //System.out.println("Telefone é inválido");


        String sql = "INSERT INTO usuarios(nome,cpf,endereco,email,telefone) VALUES(?,?,?,?,?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome); //deveria aceitar somente letras
            stmt.setString(2, CPF);  // Não deveria aceitar letras
            stmt.setString(3, endereco); //
            stmt.setString(4, email); //
            stmt.setString(5, telefone);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário salvo com SUCESSO!");
            }
        } catch (Exception e) {
            System.out.println("FALHA ao Salvar!");
            throw new RuntimeException(e);
        }
    }

    public static void listarTodosUsuarios() {

        System.out.println("Lista de usuários");

        String sql = "SELECT * FROM usuarios";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrouDados = false;

            while (rs.next()) {
                encontrouDados = true;
                System.out.println("Nome: "
                        + rs.getString("nome")
                        + " CPF: "
                        + rs.getString("cpf")
                        + " Email: "
                        + rs.getString("email")
                );
            }

            if (encontrouDados) {
                System.out.println("Dados encontrados!");
            } else {
                System.out.println("Nenhum dado encontrado!");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarServico() {

        System.out.println("Lista de Serviçoes e Valores");

        String sql = "SELECT * FROM servicos";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrouDados = false;

            while (rs.next()) {
                encontrouDados = true;
                System.out.println("- "
                        + rs.getString("nome")
                        + " - "
                        + "R$ "
                        + rs.getString("valor")
                 );
            }

            if (encontrouDados) {
                System.out.println("Dados encontrados!");
            } else {
                System.out.println("Nenhum dado encontrado!");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void consultaUsuarioPorNome(Scanner scan) {

        System.out.println("Digite o nome do usuário: ");
        String nome = scan.nextLine();

        String sql = "SELECT * FROM usuarios WHERE nome LIKE ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            boolean encontrouDados = false;

            while (rs.next()) {
                encontrouDados = true;
                System.out.println("Nome: "
                        + rs.getString("nome")
                        + " CPF: "
                        + rs.getString("cpf")
                        + " Email: "
                        + rs.getString("email")
                );
            }

            if (encontrouDados) {
                System.out.println("Dados encontrados!");
            } else {
                System.out.println("Nenhum dado encontrado!");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void servicosManicure(Scanner scan) {

        System.out.println("---Atribuir serviço a um cliente---");

        System.out.println("Digite o nome (ou parte do nome) do cliente: ");
        String nomeBusca = scan.nextLine();

        int usuarioId = buscarIdUsuarioPorNome(nomeBusca, scan);
        if (usuarioId == -1) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        int serv = 0;

        while (serv != 9) {
            System.out.println("===== Serviços =====");
            System.out.println("1 - Manicure R$ 25,00");
            System.out.println("2 - Pedicure R$ 30,00");
            System.out.println("3 - Manicure e Pedicure R$ 50,00");
            System.out.println("4 - Esmaltação em gel mão R$ 60,00");
            System.out.println("5 - Esmaltação em gel pé R$ 60,00");
            System.out.println("6 - Alongamento em gel R$ 190,00");
            System.out.println("7 - Alongamento em fibra R$ 250,00");
            System.out.println("9 - Voltar");
            System.out.println("====================");
            System.out.println("Escolha uma opção: ");

            if (scan.hasNextInt()) {
                serv = scan.nextInt();
                scan.nextLine();
            } else {
                System.out.println("Por favor digite um serviço válido!");
                scan.nextLine();
                continue;
            }

            if (serv >= 1 && serv <= 7) {
                salvarAtendimento(usuarioId, serv);
                System.out.println("Serviço atribuído com sucesso!");
            } else if (serv == 9) {
                System.out.println("Voltando ao menu principal...");
            } else {
                System.out.println("Opção inválida!");
            }
        }
    }

    public static int buscarIdUsuarioPorNome(String nome, Scanner scan) {

        String sql = "SELECT id, nome FROM usuarios WHERE nome LIKE ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            List<Integer> ids = new ArrayList<>();
            List<String> nomes = new ArrayList<>();

            while (rs.next()) {
                ids.add(rs.getInt("id"));
                nomes.add(rs.getString("nome"));
            }

            if (ids.isEmpty()) {
                return -1;
            }

            if (ids.size() == 1) {
                return ids.get(0);
            }

            System.out.println("Vários clientes encontrados:");
            for (int i = 0; i < ids.size(); i++) {
                System.out.println((i + 1) + " - " + nomes.get(i));
            }
            System.out.println("Digite o número do cliente desejado: ");
            int escolha = scan.nextInt();
            scan.nextLine();

            if (escolha >= 1 && escolha <= ids.size()) {
                return ids.get(escolha - 1);
            }

            return -1;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void salvarAtendimento(int usuarioId, int servicoId) {

        String sql = "INSERT INTO atendimentos(usuario_id, servico_id) VALUES(?,?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, servicoId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarAtendimentos() {

        System.out.println("---Lista de Atendimentos---");
        System.out.println();

        String sql = "SELECT u.nome AS cliente, s.nome AS servico, s.valor, a.data_atendimento " +
                "FROM atendimentos a " +
                "JOIN usuarios u ON a.usuario_id = u.id " +
                "JOIN servicos s ON a.servico_id = s.id " +
                "ORDER BY u.nome, a.data_atendimento";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrouDados = false;
            java.math.BigDecimal totalGeral = java.math.BigDecimal.ZERO;
            java.math.BigDecimal subtotalCliente = java.math.BigDecimal.ZERO;
            String clienteAtual = null;

            while (rs.next()) {
                encontrouDados = true;

                String cliente = rs.getString("cliente");
                java.math.BigDecimal valor = rs.getBigDecimal("valor");

                if (clienteAtual != null && !clienteAtual.equals(cliente)) {
                    System.out.println("   Subtotal | " + clienteAtual + ": R$ " + subtotalCliente);
                    System.out.println();
                    subtotalCliente = java.math.BigDecimal.ZERO;
                }

                clienteAtual = cliente;
                subtotalCliente = subtotalCliente.add(valor);
                totalGeral = totalGeral.add(valor);

                System.out.println("Cliente: " + cliente
                        + " | Serviço: " + rs.getString("servico")
                        + " | Valor: R$ " + valor);
            }

            if (!encontrouDados) {
                System.out.println("Nenhum atendimento encontrado!");
            } else {
                System.out.println("   Subtotal | " + clienteAtual + ": R$ " + subtotalCliente);
                System.out.println("--------------------------------");
                System.out.println("VALOR TOTAL: R$ " + totalGeral);
                System.out.println("--------------------------------");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static Connection conectar() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USUARIO, DB_SENHA);
    }

}