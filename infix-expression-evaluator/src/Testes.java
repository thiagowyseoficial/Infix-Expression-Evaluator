public class Testes {
    public static void main(String[] args) {
        System.out.println("=== TESTES DO TRABALHO ===");
        System.out.println();

        executarBlocoDeTestes(true);
        System.out.println();
        executarBlocoDeTestes(false);
    }

    private static void executarBlocoDeTestes(boolean usarEstatica) {
        String nomeEstrutura = usarEstatica ? "ESTATICA" : "DINAMICA";
        FabricaEstruturas fabrica = new FabricaEstruturas(usarEstatica, 300);
        ConversorExpressao conversor = new ConversorExpressao(fabrica);
        CalculadoraPosFixa calculadora = new CalculadoraPosFixa(fabrica);

        System.out.println("Testando implementacao " + nomeEstrutura);

        testarConversao(
            conversor,
            "3+{[5*a]-[b/(3+c)]}",
            "3 5 a * b 3 c + / - +"
        );

        testarCalculo(
            conversor,
            calculadora,
            "3+{[5*a]-[b/(3+c)]}",
            new char[] { 'a', 'b', 'c' },
            new double[] { 5.0, 8.0, 1.0 },
            26.0
        );

        testarConversao(
            conversor,
            "a+b*c",
            "a b c * +"
        );

        testarCalculo(
            conversor,
            calculadora,
            "a+b*c",
            new char[] { 'a', 'b', 'c' },
            new double[] { 2.0, 3.0, 4.0 },
            14.0
        );

        testarErro(conversor, "3+{2*5]", "Delimitadores em ordem incorreta");
        testarErro(conversor, "3+", "A expressao terminou de forma incompleta");
        testarErro(conversor, "3a+2", "Falta operador antes");
        testarErro(conversor, "3+&", "Simbolo invalido");
    }

    private static void testarConversao(
        ConversorExpressao conversor,
        String expressao,
        String posFixaEsperada
    ) {
        try {
            ResultadoPosFixa resultado = conversor.converterParaPosFixa(expressao);
            if (resultado.getExpressaoPosFixa().equals(posFixaEsperada)) {
                System.out.println("[OK] Conversao: " + expressao);
            } else {
                System.out.println("[FALHOU] Conversao: " + expressao);
                System.out.println("Esperado: " + posFixaEsperada);
                System.out.println("Obtido:   " + resultado.getExpressaoPosFixa());
            }
        } catch (ErroSintaxe e) {
            System.out.println("[FALHOU] Conversao com excecao: " + expressao);
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarCalculo(
        ConversorExpressao conversor,
        CalculadoraPosFixa calculadora,
        String expressao,
        char[] variaveis,
        double[] valores,
        double valorEsperado
    ) {
        try {
            ResultadoPosFixa resultado = conversor.converterParaPosFixa(expressao);
            TabelaVariaveis tabelaVariaveis = new TabelaVariaveis(variaveis.length + 5);

            for (int i = 0; i < variaveis.length; i++) {
                tabelaVariaveis.guardar(variaveis[i], valores[i]);
            }

            double valorCalculado = calculadora.calcular(resultado.getFilaPosFixa(), tabelaVariaveis);

            if (Math.abs(valorCalculado - valorEsperado) < 0.0001) {
                System.out.println("[OK] Calculo: " + expressao + " = " + valorCalculado);
            } else {
                System.out.println("[FALHOU] Calculo: " + expressao);
                System.out.println("Esperado: " + valorEsperado);
                System.out.println("Obtido:   " + valorCalculado);
            }
        } catch (ErroSintaxe e) {
            System.out.println("[FALHOU] Calculo com excecao: " + expressao);
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarErro(
        ConversorExpressao conversor,
        String expressao,
        String trechoEsperado
    ) {
        try {
            conversor.converterParaPosFixa(expressao);
            System.out.println("[FALHOU] Erro nao detectado: " + expressao);
        } catch (ErroSintaxe e) {
            if (e.getMessage().contains(trechoEsperado)) {
                System.out.println("[OK] Erro detectado: " + expressao);
            } else {
                System.out.println("[FALHOU] Mensagem inesperada: " + expressao);
                System.out.println("Mensagem: " + e.getMessage());
            }
        }
    }
}
