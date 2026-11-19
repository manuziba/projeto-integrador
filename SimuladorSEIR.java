import java.util.ArrayList;
import java.util.List;
public class SimuladorSEIR {

	public static void main(String[] args) {
		double infectadosIniciais = 5;
		double alfa = 0.27;
		double sigma = 0.71;
		double beta = 0.21;
		int diasDeSimulacao = 300;

		double[][][] tabela = {
			{//cantina, sala conv, patio, corredor baixo, bibliotca, corredor cima, quadra
				{2,0,3,1,2,0,3}, //info 1
				{3,0,3,0,2,6,1}, //info 2
				{1,1,0,0,2,1,2}, //info 3
				{7,0,0,0,0,0,6}, //info 4
				{2,4,2,2,0,0,7}, //agro 1
				{5,1,1,1,0,0,6}, // agro2
				{0,0,7,0,0,1,3}, //agro 3
				{0,0,0,0,1,0,0},//agro 4
				{8,0,6,4,0,0,7}, // adm1
				{3,0,0,2,0,2,3} //adm2
			},
			{
				{0,0,6,1,2,3,1}, //info 1
				{6,0,4,0,6,4,3}, //info 2
				{2,0,4,2,3,7,0}, //info 3
				{7,3,3,0,1,0,0}, //info 4
				{1,0,1,2,0,0,7}, //agro 1
				{3,0,8,3,0,0,10}, // agro2
				{0,0,8,6,0,0,2}, //agro 3
				{0,0,3,0,0,0,2},//agro 4
				{0,0,0,6,1,0,1}, // adm1
				{0,0,4,0,0,0,4} //adm2
			},
			{
				{1,0,5,1,0,5,0}, //info 1
				{4,1,6,0,2,4,1}, //info 2
				{7,0,3,0,3,4,3}, //info 3
				{0,4,3,0,0,1,7}, //info 4
				{2,0,3,4,0,0,3}, //agro 1
				{0,1,3,2,0,0,16}, // agro2
				{3,1,9,3,0,0,5}, //agro 3
				{0,0,4,0,0,0,1},//agro 4
				{4,4,5,6,0,0,5}, // adm1
				{5,0,7,0,0,0,4} //adm2
			}
		};
		double[] tamanhos = {33, 36, 34, 31, 33, 30, 28, 20, 35, 30};
		String[] nomes = {"Info 1", "Info 2", "Info 3", "Info 4", "Agro 1","Agro 2", "Agro 3", "Agro 4", "Adm 1", "Adm 2"};

		// matriz de contato final
		MatrizesContato obj = new MatrizesContato(tabela);
		obj.tamanhoTurma = tamanhos;
		double[][] media = obj.mediaTabelas();
		double[] totalLocal = obj.totalPorLocal(media);
		double[] sala = obj.naSala(media);
		double[][] contato = obj.proporcaoContato(media, totalLocal, sala);
		double[][] matriz = obj.proporcaoComTempo(contato);

		// as 10 turmas (os infectados iniciais ficam só na Info 1(por enquanto))
		List<Populacao> turmas = new ArrayList<>();
		for (int a = 0; a < 10; a++) {
			double inf = 0;
			if (a == 0) {
				inf = infectadosIniciais;
			}
			turmas.add(new Populacao(nomes[a], tamanhos[a] - inf, 0, inf, 0, sigma, beta));
		}

		Simulador simulador = new Simulador(diasDeSimulacao, alfa, matriz);
		List<RegistroDiario> historico = simulador.rodar(turmas);

		simulador.imprimirResultados(historico);
	}
}

class Populacao {
    String nome;
    double s;   // suscetíveis
    double e;   // expostos
    double i;   // infectados
    double r;   // recuperados
    double sigma;
    double beta;

    public Populacao(String nome, double s, double e, double i, double r, double sigma, double beta) {
        this.nome = nome;
        this.s = s;
        this.e = e;
        this.i = i;
        this.r = r;
        this.sigma = sigma;
        this.beta = beta;
    }

    public double populacaoTotal() {
        return s+e+i+r;
    }
    
     public void atualizarDia(double novasExposicoes) {
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
    double alfa;
    double[][] matriz;

    public Simulador(int totalDeDias, double alfa, double[][] matriz) {
		
        this.totalDeDias = totalDeDias;
        this.alfa = alfa;
        this.matriz = matriz;
    }

    public void passarUmDia(List<Populacao> turmas) {
		
        double[] novasExposicoes = new double[10];

        // calcula as exposições de todas as turmas
        for (int a = 0; a < 10; a++) {
            double pressao = 0;
            for (int b = 0; b < 10; b++) {
                Populacao outra = turmas.get(b);
                pressao = pressao + matriz[a][b] * outra.i / outra.populacaoTotal();
            }
            novasExposicoes[a] = alfa * turmas.get(a).s * pressao;
        }

        // atualiza cada turma
        for (int a = 0; a < 10; a++) {
            turmas.get(a).atualizarDia(novasExposicoes[a]);
        }
    }


    List<RegistroDiario> rodar(List<Populacao> turmas) {
        List<RegistroDiario> historico = new ArrayList<>();
        historico.add(totalDaEscola(0, turmas));

        for (int dia = 1; dia <= totalDeDias; dia++) {
            passarUmDia(turmas);
            historico.add(totalDaEscola(dia, turmas));
        }

        return historico;
    }

    RegistroDiario totalDaEscola(int dia, List<Populacao> turmas) {
        double s = 0;
        double e = 0;
        double i = 0;
        double r = 0;

        for (int a = 0; a < turmas.size(); a++) {
            s = s + turmas.get(a).s;
            e = e + turmas.get(a).e;
            i = i + turmas.get(a).i;
            r = r + turmas.get(a).r;
        }

        return new RegistroDiario(dia, s, e, i, r);
    }

        public void imprimirResultados(List<RegistroDiario> historico) {
        System.out.printf("%-5s %-9s %-9s %-9s %-9s%n", "Dia", "S", "E", "I", "R");
        for (int k = 0; k < historico.size(); k++) {
            RegistroDiario registro = historico.get(k);
            System.out.printf("%-5d %-9.1f %-9.1f %-9.1f %-9.1f%n",
                    registro.dia, registro.s, registro.e, registro.i, registro.r);
        }
    }
}
