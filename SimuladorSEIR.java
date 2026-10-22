import java.util.ArrayList;
import java.util.List;
public class SimuladorSEIR {

    public static void main(String[] args) {
        double populacaoTotal = 310;
        double infectadosIniciais = 5;
        double alfa = 0.27;      // taxa de transmissão (R0 = alfa / beta)
        double sigma = 0.71;     // taxa de incubação (1/sigma = dias de incubação)
        double beta = 0.21;      // taxa de recuperação (1/beta = dias médios de infecção)
        int diasDeSimulacao = 120;

        Populacao escola = new Populacao("Escola",populacaoTotal - infectadosIniciais, 0, infectadosIniciais, 0, alfa, sigma, beta);
              

        Simulador simulador = new Simulador(diasDeSimulacao);
        List<RegistroDiario> historico = simulador.rodar(escola);

        simulador.imprimirResultados(historico);
    }
}

class Populacao {
    String nome;
    double s;   // suscetíveis
    double e;   // expostos
    double i;   // infectados
    double r;   // recuperados
    double alfa;
    double sigma;
    double beta;

    public Populacao(String nome, double s, double e, double i, double r, double alfa, double sigma, double beta) {
        this.nome = nome;
        this.s = s;
        this.e = e;
        this.i = i;
        this.r = r;
        this.alfa = alfa;
        this.sigma = sigma;
        this.beta = beta;
    }


    public void atualizarDia() {
		double populacaoTotal = s+e+i+r;
        double novasExposicoes = alfa*s*i/populacaoTotal;
        double novosInfecciosos = sigma*e;
        double novasRecuperacoes = beta*i;

        s = s - novasExposicoes;
        e = e + novasExposicoes - novosInfecciosos;
        i = i + novosInfecciosos - novasRecuperacoes;
        r = r + novasRecuperacoes;

        // Proteção contra valores negativos 
        if (s < 0) {
            s = 0;
        }
        if (e < 0) {
            e = 0;
        }
        if (i < 0) {
            i = 0;
        }
    }
}

//Guarda o estado da população em um dia específico, pro historico
class RegistroDiario {
    int dia;
    double s;
    double e;
    double i;
    double r;

    public RegistroDiario(int dia, double s, double e, double i, double r) {
        this.dia = dia;
        this.s = s;
        this.e = e;
        this.i = i;
        this.r = r;
    }
}


class Simulador {
    int totalDeDias;

    public Simulador(int totalDeDias) {
        this.totalDeDias = totalDeDias;
    }

    List<RegistroDiario> rodar(Populacao populacao) {
        List<RegistroDiario> historico = new ArrayList<>();
        historico.add(new RegistroDiario(0, populacao.s, populacao.e, populacao.i, populacao.r));

        for (int dia = 1; dia <= totalDeDias; dia++) {
            populacao.atualizarDia();
            historico.add(new RegistroDiario(dia, populacao.s, populacao.e, populacao.i, populacao.r));
        }

        return historico;
    }

    public void imprimirResultados(List<RegistroDiario> historico) {
        System.out.printf("%-5s %-9s %-9s %-9s %-9s %-9s%n", "Dia", "S", "E", "I", "R", "Total");
        for (RegistroDiario registro : historico) {
            System.out.printf("%-5d %-9.1f %-9.1f %-9.1f %-9.1f %-9.1f%n",
                    registro.dia, registro.s, registro.e, registro.i, registro.r,
                    registro.s + registro.e + registro.i + registro.r);
        }
    }
}
