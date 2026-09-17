import java.util.ArrayList;
import java.util.List;

/**
 * Simulador SIR (Suscetível-Infectado-Recuperado) — versão base, turma única.
 *
 * Estrutura pensada para expansão futura para múltiplas turmas (metapopulação):
 * hoje "Populacao" representa a escola inteira; depois pode virar uma turma,
 * e o Simulador pode passar a gerenciar uma lista de Populacoes conectadas
 * por uma matriz de contato.
 */
public class SimuladorSIR {

    public static void main(String[] args) {
        // Parâmetros iniciais (exemplo — ajustar com valores reais depois)
        double populacaoTotal = 500;
        double infectadosIniciais = 5;
        double alfa = 0.0004;   // taxa de transmissão (já normalizada, ver Populacao.calcularNovasInfeccoes)
        double beta = 0.2;      // taxa de recuperação (1/beta = dias médios de infecção)
        int diasDeSimulacao = 60;

        Populacao escola = new Populacao("Escola", populacaoTotal, infectadosIniciais, 0, alfa, beta);

        Simulador simulador = new Simulador(diasDeSimulacao);
        List<RegistroDiario> historico = simulador.rodar(escola);

        simulador.imprimirResultados(historico);
    }
}

/**
 * Representa uma população (por enquanto, a escola inteira; futuramente, uma turma).
 * Guarda o estado atual (S, I, R) e os parâmetros da doença.
 */
class Populacao {
    String nome;
    double s;
    double i;
    double r;
    double alfa;
    double beta;

    public Populacao(String nome, double s, double i, double r, double alfa, double beta) {
        this.nome = nome;
        this.s = s;
        this.i = i;
        this.r = r;
        this.alfa = alfa;
        this.beta = beta;
    }

    double populacaoTotal() {
        return s + i + r;
    }

    /**
     * Calcula quantas novas infecções acontecem neste dia.
     * Versão de turma única: alfa * S * I
     * (Quando expandir para várias turmas, este método vira a soma
     * ponderada pela matriz de contato — alfa * S_i * Σ contato(i,j) * I_j)
     */
    double calcularNovasInfeccoes() {
        return alfa * s * i;
    }

    /**
     * Calcula quantas pessoas se recuperam neste dia.
     */
    double calcularRecuperacoes() {
        return beta * i;
    }

    /**
     * Avança a população em um dia, aplicando as equações a diferenças.
     */
    void atualizarDia() {
        double novasInfeccoes = calcularNovasInfeccoes();
        double novasRecuperacoes = calcularRecuperacoes();

        s = s - novasInfeccoes;
        i = i + novasInfeccoes - novasRecuperacoes;
        r = r + novasRecuperacoes;

        // Proteção contra valores negativos por arredondamento
        if (s < 0){
		s = 0;
	}
        if (i < 0){
		i = 0;
	}
    }
}

/**
 * Guarda o estado da população em um dia específico, para o histórico.
 */
class RegistroDiario {
    int dia;
    double s;
    double i;
    double r;

    public RegistroDiario(int dia, double s, double i, double r) {
        this.dia = dia;
        this.s = s;
        this.i = i;
        this.r = r;
    }
}

/**
 * Controla o laço de simulação dia a dia.
 */
class Simulador {
    int totalDeDias;

    public Simulador(int totalDeDias) {
        this.totalDeDias = totalDeDias;
    }

    List<RegistroDiario> rodar(Populacao populacao) {
        List<RegistroDiario> historico = new ArrayList<>();
        historico.add(new RegistroDiario(0, populacao.s, populacao.i, populacao.r));

        for (int dia = 1; dia <= totalDeDias; dia++) {
            populacao.atualizarDia();
            historico.add(new RegistroDiario(dia, populacao.s, populacao.i, populacao.r));
        }

        return historico;
    }

    void imprimirResultados(List<RegistroDiario> historico) {
        System.out.printf("%-5s %-10s %-10s %-10s%n", "Dia", "S", "I", "R");
        for (RegistroDiario registro : historico) {
            System.out.printf("%-5d %-10.1f %-10.1f %-10.1f%n",
                    registro.dia, registro.s, registro.i, registro.r);
        }
    }
}
