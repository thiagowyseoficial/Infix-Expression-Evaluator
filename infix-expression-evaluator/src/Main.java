import java.util.Locale;
import java.util.Scanner;
//ALUNOS : THIAGO WYSE DOS SANTOS E LUCAS FERNANDES DOS SANTOS
public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner leitor = new Scanner(System.in);

        System.out.println("=== EXPRESSOES NUMERICAS COM PILHAS E FILAS ===");
        System.out.println("Escolha a implementacao das estruturas:");
        System.out.println("1 - Estatica");
        System.out.println("2 - Dinamica");
        System.out.print("Opcao: ");

        int opcao = lerOpcaoImplementacao(leitor);
        boolean usarEstatica = opcao == 1;
        FabricaEstruturas fabrica = new FabricaEstruturas(usarEstatica, 300);

        ConversorExpressao conversor = new ConversorExpressao(fabrica);
        CalculadoraPosFixa calculadora = new CalculadoraPosFixa(fabrica);

        System.out.println();
        System.out.println("Digite a expressao infixa:");
        String expressao = leitor.nextLine();

        try {
            ResultadoPosFixa resultado = conversor.converterParaPosFixa(expressao);

            System.out.println();
            System.out.println("Expressao pos-fixa (notacao polonesa invertida):");
            System.out.println(resultado.getExpressaoPosFixa());

            TabelaVariaveis tabelaVariaveis = new TabelaVariaveis(resultado.getVariaveis().length + 5);

            for (char variavel : resultado.getVariaveis()) {
                System.out.print("Informe o valor de " + variavel + ": ");
                double valor = lerReal(leitor);
                tabelaVariaveis.guardar(variavel, valor);
            }

            double resultadoFinal = calculadora.calcular(resultado.getFilaPosFixa(), tabelaVariaveis);

            System.out.println();
            System.out.println("Resultado final: " + resultadoFinal);
        } catch (ErroSintaxe | IllegalStateException e) {
            System.out.println();
            System.out.println("Erro: " + e.getMessage());
        }

        leitor.close();
    }

    private static int lerInteiro(Scanner leitor) {
        while (true) {
            String linha = leitor.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.print("Digite um numero inteiro valido: ");
            }
        }
    }

    private static int lerOpcaoImplementacao(Scanner leitor) {
        while (true) {
            int opcao = lerInteiro(leitor);
            if (opcao == 1 || opcao == 2) {
                return opcao;
            }
            System.out.print("Opcao invalida. Digite 1 para Estatica ou 2 para Dinamica: ");
        }
    }

    private static double lerReal(Scanner leitor) {
        while (true) {
            String linha = leitor.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.print("Digite um numero real valido: ");
            }
        }
    }
}
